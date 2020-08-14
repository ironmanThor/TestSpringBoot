package com.example.springboot.EnumZidingyi;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.util.CollectionUtils;

/**
 */
public class EnumCollectionExactValidator implements
    ConstraintValidator<EnumTest, Collection> {

    private Class<? extends Enum> enClass;


    @Override
    public void initialize(EnumTest constraintAnnotation) {
        enClass = constraintAnnotation.enumClass();
    }

    @Override
    public boolean isValid(Collection collection, ConstraintValidatorContext constraintValidatorContext) {
        //不验证为null的情况
        if (CollectionUtils.isEmpty(collection)){
            return true;
        }

        try {
            Method method = enClass.getDeclaredMethod("values");
            Enum[] elements = (Enum[]) method.invoke(null, null);

            Iterator iterator = collection.iterator();
            while (iterator.hasNext()){
                Object next = iterator.next();
                boolean flag = false;
                for (Enum element : elements) {
                    if (Objects.equals(next,element.toString())){
                        flag = true;
                    }
                }
                if (!flag) return false;
            }
            return true;
        }catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return false;
    }
}
