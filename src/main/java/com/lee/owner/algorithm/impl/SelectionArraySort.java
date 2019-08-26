package com.lee.owner.algorithm.impl;

import com.lee.owner.algorithm.AbstractArraySort;
import com.lee.owner.algorithm.ArraySorts;
import org.springframework.stereotype.Component;

/**
 * 选择排序
 * @Date 2019-08-25 09:22
 * @Author joseph.li
 */
@Component
public class SelectionArraySort extends AbstractArraySort {

    @Override
    public <T extends Comparable<T>> T[] doRealSort(T[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length - i; j++) {
                if (arr[i].compareTo(arr[j]) > 0) {
                    ArraySorts.swap(arr, i, j);
                }
            }
        }
        return arr;
    }
}
