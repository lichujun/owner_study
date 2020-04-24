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
     * 通过反射,获得定义Class时声明的父类的范型参数的类型.
     * 如public BookManager extends GenricManager<Book>
     *
     * @param clazz The class to introspect
     * @return the first generic declaration, or <code>Object.class</code> if cannot be determined
     */
    public static Class<?> getSuperClassGenericType(Class<?> clazz) {
        return getSuperClassGenericType(clazz, 0);
    }

    /**
     * 通过反射,获得定义Class时声明的父类的范型参数的类型.
     * 如public BookManager extends GenricManager<Book>
     *
     * @param clazz clazz The class to introspect
     * @param index the Index of the generic ddeclaration,start from 0.
     */
    public static Class<?> getSuperClassGenericType(Class<?> clazz, int index) throws IndexOutOfBoundsException {

        Type genType = clazz.getGenericSuperclass();

        return getSuperClassGenericType(genType, index);

    }

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

    /**
     * 通过反射,获得定义Class时获取指定父类的范型参数的类型.
     * 如public BookManager extends GenricManager<Book>
     *
     * @param clazz clazz The class to introspect
     * @param index the Index of the generic ddeclaration,start from 0.
     */
    public static Class<?> getSuperClassGenericType(Class<?> clazz, Class<?> parentClazz, int index) {
        Type genType;
        Type newGenType;
        for (; clazz != Object.class;) {
            genType = clazz.getGenericSuperclass();
            if (genType instanceof ParameterizedType) {
                newGenType = ((ParameterizedType) genType).getRawType();
            } else {
                newGenType = genType;
            }

            if (newGenType == parentClazz) {
                return getSuperClassGenericType(genType, index);
            }
            if (newGenType instanceof Class) {
                clazz = (Class<?>) newGenType;
            } else {
                return Object.class;
            }
        }
        return Object.class;
    }
}
