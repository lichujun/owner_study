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
    public <T extends Comparable<T>> T[] doRealSort(T[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int preIndex = i - 1;
            T current = arr[i];
            for (; preIndex >= 0 && current.compareTo(arr[preIndex]) < 0; preIndex--) {
                arr[preIndex + 1] = arr[preIndex];
            }
            if (preIndex + 1 < i) {
                arr[preIndex + 1] = current;
            }
        }
        return arr;
    }
}
