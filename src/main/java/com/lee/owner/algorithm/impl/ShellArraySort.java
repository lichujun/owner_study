package com.lee.owner.algorithm.impl;

import com.lee.owner.algorithm.AbstractArraySort;
import org.springframework.stereotype.Component;

/**
 * 希尔排序
 * @Date 2019-08-26 13:53
 * @Author joseph.li
 */
@Component
public class ShellArraySort extends AbstractArraySort {

    @Override
    protected <T extends Comparable<T>> T[] doRealSort(T[] arr) {
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < arr.length; i++) {
                if (arr[i].compareTo(arr[i - gap]) < 0) {
                    int j = i;
                    T current = arr[i];
                    for (; j - gap >= 0 && current.compareTo(arr[j - gap]) < 0; j -= gap) {
                        arr[j] = arr[j - gap];
                    }
                    if (j < i) {
                        arr[j] = current;
                    }
                }
            }
        }
        return arr;
    }
}
