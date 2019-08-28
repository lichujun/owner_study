package com.lee.owner.algorithm.impl;

import com.lee.owner.algorithm.AbstractArraySort;
import org.springframework.stereotype.Component;

/**
 * 插入排序
 * @Date 2019-08-26 13:32
 * @Author joseph.li
 */
@Component
public class InsertionArraySort extends AbstractArraySort {

    @Override
    protected <T extends Comparable<T>> T[] doRealSort(T[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int index = i;
            T current = arr[i];
            for (; index > 0 && current.compareTo(arr[index - 1]) < 0; index--) {
                arr[index] = arr[index - 1];
            }
            if (index < i) {
                arr[index] = current;
            }
        }
        return arr;
    }
}
