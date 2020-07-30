package com.example.springboot.datafill.annonation;

import java.lang.annotation.*;

/**
 * 此注解只是一个标记,目的减少不必要的字段扫描
 *
 * Q:此注解不加也一样填充吗?
 * A:不一定，这取决于代理方法是如何调用的。最终还是希望他不填充

 * Q:嵌套的对象要填充需要加这个注解吗?
 * A:必须加，这就是这个注解存在的目的，减少反射盲目的扫描对象参数带来性能浪费
 *
 * @author szz
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface DataFillEnable {
}
