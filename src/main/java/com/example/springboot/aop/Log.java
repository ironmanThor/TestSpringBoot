package com.example.springboot.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Administrator
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {
  /**
   * 终端
   */
  String extremity() default "聚合:PC:管理后台";

  /**
   * 操作模块
   */
  String operationUnit() default "UNKNOWN";

  /**
   * 操作类型
   */
  String operationType() default "未知操作类型";

  /**
   * 详情描述,可使用占位符获取参数:{{构造参数名}}
   */
  String describe() default "";
}
