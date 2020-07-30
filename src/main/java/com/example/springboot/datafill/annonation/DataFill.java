package com.example.springboot.datafill.annonation;


import com.example.springboot.datafill.handler.DataFillHandler;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 填充注解
 * @author szz
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface DataFill {

    /**
     * 这参数要标记查询条件的字段名
     * 需要是本类可以方法到得字段，或者在嵌套填充中，上一层有得字段都可以访问到
     * @return
     */
    String value() default "";

    /**
     * 对应不同得处理器,也是区分注解作用得关键
     * @return
     */
    Class<? extends DataFillHandler> handler();
}
