package com.example.springboot.Util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 反射工具类
 *
 */
public class ReflectionUtil {

  /**
   * 根据对象的属性名获取对象属性中的值
   *
   * @param data 对象
   * @param fieldName 字段属性名
   * @param <T> 传入对象类型
   * @param <K> 返回值类型
   */
  public static <T, K> K getFieldValue(T data, String fieldName) {
    if (data != null && fieldName != null) {
      try {
        Field field = getClassFieldContainParentClass(data.getClass(), fieldName);
        if (field != null) {
          field.setAccessible(true);
          return (K) field.get(data);
        }
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  /**
   * <h4>功能：【获取泛型集合实体类中1到多个字段的值，且保证不重复】【2017年6月10日 上午9:57:21】【创建人：xz】</h4>
   * <h4></h4>
   *
   * @param data 泛型集合
   * @param fieldNames 多个字段
   */
  public static <T, K> List<K> getListFieldValue(List<T> data, String... fieldNames) {
    // 存储已经有过的值
    List<K> fieldValues = new ArrayList<>();
    if (data == null) {
      return fieldValues;
    }
    // 取出不重复的值存储
    for (T t : data) {
      if (null != t) {
        Class groupObject = t.getClass();
        try {
          if (fieldNames != null) {
            for (String fieldName : fieldNames) {
              Field field = getClassFieldContainParentClass(groupObject, fieldName);
              if (field != null) {
                field.setAccessible(true);
                Object value = field.get(t);
                if (value != null && !fieldValues.contains(value)) {
                  fieldValues.add((K) value);
                }
              }
            }
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
    return fieldValues;
  }

  /**
   * <h4>功能：【获取类中的字段，包括父类，返回第一个匹配】【2017年6月5日 上午10:54:06】【创建人：xz】</h4>
   * <h4></h4>
   *
   * @param obj 要查找的类
   */
  private static Field getClassFieldContainParentClass(Class obj, String fieldName) {
    try {
      if (obj != null) {
        return obj.getDeclaredField(fieldName);
      }
      return null;
    } catch (Exception e) {
      Class parentObj = obj.getSuperclass();
      return getClassFieldContainParentClass(parentObj, fieldName);
    }
  }

  /**
   * <h4>功能：【获取子类包扩父类所有的字段，但是必须指定类名,且必须指定，否则不返回任何结果】【2017年6月10日
   * 上午11:17:20】【创建人：xz】</h4>
   * <h4></h4>
   */
  private static List<Field> getClassFieldsContainParentClass(Class obj, List<Field> fields) {
    try {
      if (obj != null && fields != null) {
        fields.addAll(Arrays.asList(obj.getDeclaredFields()));
        Class parentObj = obj.getSuperclass();
        getClassFieldsContainParentClass(parentObj, fields);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return fields;
  }
}
