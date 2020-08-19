package com.example.springboot.EnumZidingyi;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = EnumExactValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumExact {
  String message() default "{ManyEnum}";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};

  /**
   * 目标枚举类
   */
  Class<? extends Enum> enumClass() default Enum.class;
}


/*
传入单个值String类型时的自定义判断传入值是否为枚举类中的
*/
