package com.example.springboot.EnumZidingyi;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
/*
传入多个个值String类型，并多个值拼接为一个Stirng，时的自定义判断传入值是否为枚举类中的
*/
public class ManyEnumExactValidator implements ConstraintValidator<ManyEnumExact, String> {

    private Class<? extends Enum> enClass = null;

    @Override
    public void initialize(ManyEnumExact constraintAnnotation) {
        enClass = constraintAnnotation.enumClass();
    }

    @Override
    public boolean isValid(String manyEnumStr, ConstraintValidatorContext constraintValidatorContext) {
        //不验证为null的情况
        if (manyEnumStr == null || enClass == null) {
            return true;
        }

        try {
            String[] enumStrs = manyEnumStr.split(",");
            Method method = enClass.getDeclaredMethod("values");
            Enum[] elements = (Enum[]) method.invoke(null, null);

            for (String enumStr : enumStrs) {
                Optional<Enum> element = Arrays.stream(elements)
                        .filter(el -> Objects.equals(el.toString(), enumStr)).findFirst();
                if (!element.isPresent()) {
                    return false;
                }
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
