package com.lee.owner.algorithm;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 排序对象工厂
 * @Date 2019-08-25 09:35
 * @Author joseph.li
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ArraySorts {

    public static <T extends Comparable<T>> void swap(T[] arr, int a, int b) {
        T temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }
}
