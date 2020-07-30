package com.example.springboot.datafill.executor;

import com.example.springboot.datafill.annonation.DataFill;
import com.example.springboot.datafill.annonation.DataFillEnable;
import com.example.springboot.datafill.excaption.UnsupportedDataFillException;
import com.example.springboot.datafill.handler.DataFillHandler;
import com.example.springboot.datafill.metadata.DataFillMetadata;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/**
 * <h2>数据填充核心执行器</h2>
 * 该类将对象中的注解字段与对应的handler扫描出来，并为每个字段进行异步填充操作。
 * {@link com.szz.fill.datafill.executor.DataFillExecutor#tl} 此变量装载了每个字段的操作任务
 * {@link DataFillExecutor#execute0(Object, Map)} 此方法为核心方法也是填充的入口
 *
 * @author szz
 */
@Component
public class DataFillExecutor {

    private static ConcurrentHashMap<String, DataFillHandler> handlerKeys = new ConcurrentHashMap();

    private static ThreadLocal<List<Runnable>> tl = ThreadLocal.withInitial(() -> new ArrayList());

    public static final ThreadPoolExecutor pool = new ThreadPoolExecutor(10, 10, 1,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue(),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.CallerRunsPolicy());


    /**
     * 对外暴露的执行模板,其意义在于配合ThreadLocal收集的异步任务调用,
     * {@link DataFillExecutor#executeAfter()} 统一执行
     *
     * @param target
     */
    public static void execute(Object target) {
        execute0(target, new ConcurrentHashMap());
    }


    public static void execute0(Object target, Map contextArgs) {
        sinkExecute(target, contextArgs, true);
        executeAfter();
    }


    /**
     * 执行器的入口,扫描对象中的所有带{@link DataFill} 注解的字段并进行
     * 解析封装等操作
     *
     * @param target      填充对象
     * @param contextArgs 上下文的依赖参数
     */
    private static void sinkExecute(Object target, final Map contextArgs, boolean flag) {
        if (target instanceof Collection) {
            for (Object o1 : ((Collection) target)) {
                try {
                    sinkExecute(o1, contextArgs, false);
                } catch (UnsupportedDataFillException exception) {
                    return;
                } catch (Exception exception){
                    continue;
                }
            }
        } else if (target instanceof Map) {
            for (Object value : ((Map) target).values()) {
                try {
                    sinkExecute(value, contextArgs, false);
                } catch (UnsupportedDataFillException exception) {
                    return;
                } catch (Exception exception){
                    continue;
                }
            }
        } else {
            if (null == target.getClass().getAnnotation(DataFillEnable.class)) {
                throw new UnsupportedDataFillException("当前对象不支持填充");
            }
            Class<?> aClass = target.getClass();
            Field[] declaredFields = aClass.getDeclaredFields();

            for (Field declaredField : declaredFields) {
                DataFill dataFill = declaredField.getAnnotation(DataFill.class);
                if (null != dataFill) {

                    DataFillMetadata metadata = new DataFillMetadata();
                    metadata.setFillField(declaredField);
                    metadata.setFillObj(target);
                    metadata.setSelectionKey(findParam(dataFill, target, contextArgs));

                    if (flag) {
                        tl.get().add(() -> {
                            dispatcher(dataFill, metadata);
                            declaredField.setAccessible(true);
                            try {
                                Object sinkObj = declaredField.get(target);
                                if (null != sinkObj) {
                                    try {
                                        sinkExecute(sinkObj, contextArgs, false);
                                    } catch (UnsupportedDataFillException exception) {
                                    }
                                }
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        });
                    } else {
                        dispatcher(dataFill, metadata);
                        declaredField.setAccessible(true);
                        try {
                            Object sinkObj = declaredField.get(target);
                            if (null != sinkObj) {
                                try {
                                    sinkExecute(sinkObj, contextArgs, false);
                                } catch (UnsupportedDataFillException exception) {
                                }
                            }
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }


    /**
     * 当前填充的收尾工作,将收集到的填充任务统一执行
     */
    private static void executeAfter() {
        try {
            List<CompletableFuture<Void>> futureList = tl.get().stream()
                    .map(a -> CompletableFuture.runAsync(a, pool)).collect(Collectors.toList());
            futureList.stream().map(CompletableFuture::join).collect(Collectors.toList());
        } finally {
            tl.remove();
        }
    }


    /**
     * 这里类似享元模式,可以借鉴String的字符串常量池原理。
     * 通过注解信息查找对应字段。
     *
     * @param dataFill    填充注解
     * @param target      目标对象
     * @param contextArgs 上下文参数
     * @return
     */
    private static Object findParam(DataFill dataFill, Object target, Map contextArgs) {
        String key = dataFill.value();
        Object value = null;
        try {
            Field field = target.getClass().getDeclaredField(key);
            field.setAccessible(true);
            value = field.get(target);
            if (value == null) throw new NoSuchFieldException();
            contextArgs.put(key, value);
        } catch (NoSuchFieldException e) {
            value = contextArgs.get(key);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return value;
    }


    /**
     * handler的分发器,不同的注解应对应不同的填充规则。
     * 这里也类似享元模式,这里将收集好的元信息分发给用户自定义的handler。
     *
     * @param dataFill         填充注解
     * @param dataFillMetadata 封装好的元信息
     */
    private static void dispatcher(DataFill dataFill, DataFillMetadata dataFillMetadata) {
        Class<? extends DataFillHandler> handler = dataFill.handler();
        DataFillHandler dataFillHandler;
        if ((dataFillHandler = handlerKeys.get(handler.getName())) != null) {
            try {
                dataFillHandler.fill0(dataFillMetadata);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        } else {
            try {
                DataFillHandler fillHandler = handler.newInstance();
                handlerKeys.put(handler.getName(), fillHandler);
                fillHandler.fill0(dataFillMetadata);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
