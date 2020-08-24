package com.example.springboot.EnumZidingyi;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.SneakyThrows;

/**
 * @progrm:TestSpringBoot
 * @Description:
 * @Author: leichengxu
 * @Date:2020-08-19 09:42
 */
public class test implements ConstraintValidator<EnumExact,String > {
  private Class<? extends  Enum> enumClass=null;

  @Override
  public void initialize(EnumExact constraintAnnotation) {
    enumClass=constraintAnnotation.enumClass();
  }

  @SneakyThrows
  @Override
  public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
    if (s==null||enumClass==null){
      return true;
    }
    Method method = enumClass.getDeclaredMethod("values");
    Object invoke = method.invoke(null, null);
    Enum[] enums =(Enum[]) invoke;
    Optional<Enum> first = Arrays.stream(enums).filter(c -> Objects.equals(s, c.toString()))
        .findFirst();
    if (!first.isPresent()){
      return false;
    }
    return true;
  }
}
