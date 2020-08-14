package com.example.springboot.EnumZidingyi;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumCollectionExactValidator.class)
@Target(value = {ElementType.FIELD,ElementType.METHOD,ElementType.PARAMETER})
public @interface EnumTest {
  String message() default "{EnumTest}";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
  Class<? extends Enum> enumClass() default Enum.class;

}
