package com.example.springboot.datafill.handler;


import com.example.springboot.datafill.metadata.DataFillMetadata;
import org.springframework.stereotype.Component;

/**
 * @author szz
 * @see DataFillHandler
 */
public abstract class AbstractDataFillHandler implements DataFillHandler {


    /**
     * 参数验证类提取到抽象层进行实现减少不必要的操作
     *
     * @param metadata
     * @throws Exception
     */
    protected void validation(DataFillMetadata metadata) throws Exception{
        if (metadata == null){
            throw new IllegalArgumentException();
        }else if (metadata.getFillField() == null){
            throw new IllegalArgumentException();
        }else if (metadata.getSelectionKey() == null){
            throw new IllegalArgumentException();
        }
    }


    /**
     * 模板方法 3 步骤:
     * 1. 检测入参
     * 2. 执行填充
     * 3. 异常统一拦截
     *
     * @param metadata 元信息
     * @return
     * @throws Exception
     */
    public final AbstractDataFillHandler fill0(DataFillMetadata metadata) throws Exception {
        try {
            validation(metadata);
            fill(metadata);
        }catch (Exception exception){
            exceptionCaught(exception);
        }
        return this;
    }



    /**
     * 异常统一处理方法,可以让使用者更方便的对抛出异常进行处理
     *
     * @param cause
     * @throws Exception
     */
    protected void exceptionCaught(Throwable cause) throws Exception {
        cause.printStackTrace();
    }

}
