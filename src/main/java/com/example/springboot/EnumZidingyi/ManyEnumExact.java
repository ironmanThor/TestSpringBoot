package com.example.springboot.EnumZidingyi;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 多传入枚举验证
 * @author xuzhen97
 */
@Documented
@Constraint(validatedBy = ManyEnumExactValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ManyEnumExact {
    String message() default "{ManyEnum}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<? extends Enum> enumClass() default Enum.class;
}
/*
传入多个个值String类型，并多个值拼接为一个Stirng，时的自定义判断传入值是否为枚举类中的
*/