package com.lee.owner.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author joseph.li
 * @date 2019-10-15 10:42
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GenericsUtils {

    /**
     * 通过对象获取父类范型的class对象
     * @param obj obj对象
     * @return 父类范型的class对象
     */
    public static Class<?> getSuperClassGenericType(Object obj) {
        return getSuperClassGenericType(obj, 0);
    }

    /**
     * 通过对象获取父类范型的class对象
     * @param obj obj对象
     * @param index 第几个范型
     * @return 父类范型的class对象
     */
    public static Class<?> getSuperClassGenericType(Object obj, int index) throws IndexOutOfBoundsException {
        Class<?> clazz = obj.getClass();
        Type genType = clazz.getGenericSuperclass();
        return getSuperClassGenericType(genType, index);
    }

    /**
     * 通过父类的Type对象获取范型的class对象
     * @param genType 父类的Type对象
     * @param index 第几个范型
     * @return 父类范型的class对象
     */
    private static Class<?> getSuperClassGenericType(Type genType, int index) {
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            return Object.class;
        }

        Type clazzType = params[index];

        if (!(clazzType instanceof Class)) {
            return Object.class;
        }
        return (Class<?>) clazzType;
    }
}
