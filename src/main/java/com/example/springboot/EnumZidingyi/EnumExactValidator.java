package com.example.springboot.EnumZidingyi;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
/*
传入单个值String类型时的自定义判断传入值是否为枚举类中的
*/
public class EnumExactValidator implements ConstraintValidator<EnumExact, String> {

  private Class<? extends Enum> enClass = null;

  @Override
  public void initialize(EnumExact constraintAnnotation) {
    enClass = constraintAnnotation.enumClass();
  }

  @Override
  public boolean isValid(String enumStr, ConstraintValidatorContext constraintValidatorContext) {
    //不验证为null的情况
    if (enumStr == null || enClass == null) {
      return true;
    }

    try {
      //通过反射拿到具体枚举类的所有值values
      Method method = enClass.getDeclaredMethod("values");
      //获取到枚举中所有的值
      Enum[] elements = (Enum[]) method.invoke(null, null);

      //判断传入的值在不在枚举中
      Optional<Enum> element = Arrays.stream(elements)
          .filter(el -> Objects.equals(el.toString(), enumStr)).findFirst();
      if (!element.isPresent()) {
        return false;
      }
      return true;
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }

    return false;
  }


}
