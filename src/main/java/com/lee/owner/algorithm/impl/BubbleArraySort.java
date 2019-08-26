package com.lee.owner.algorithm.impl;

import com.lee.owner.algorithm.AbstractArraySort;
import com.lee.owner.algorithm.ArraySorts;
import org.springframework.stereotype.Component;

/**
 * 冒泡排序
 * @Date 2019-08-26 13:10
 * @Author joseph.li
 */
@Component
public class BubbleArraySort extends AbstractArraySort {

    @Override
    public <T extends Comparable<T>> T[] doRealSort(T[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j].compareTo(arr[j + 1]) > 0) {
                    ArraySorts.swap(arr, j, j + 1);
                }
            }
        }
        return arr;
    }
}
