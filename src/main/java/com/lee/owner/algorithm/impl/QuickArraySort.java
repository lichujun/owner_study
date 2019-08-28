package com.lee.owner.algorithm.impl;

import com.lee.owner.algorithm.AbstractArraySort;
import com.lee.owner.algorithm.ArraySorts;
import org.springframework.stereotype.Component;

/**
 * 快速排序
 * @Date 2019-08-28 10:27
 * @Author joseph.li
 */
@Component
public class QuickArraySort extends AbstractArraySort {

    @Override
    protected <T extends Comparable<T>> T[] doRealSort(T[] arr) {
        quickIndexSort(arr, 0, arr.length - 1);
        return arr;
    }

    private <T extends Comparable<T>> void quickIndexSort(T[] arr, int leftIndex, int rightIndex) {
        if (rightIndex > leftIndex) {
            int partitionIndex = partition(arr, leftIndex, rightIndex);
            quickIndexSort(arr, leftIndex, partitionIndex - 1);
            quickIndexSort(arr, partitionIndex + 1, rightIndex);
        }
    }

    private <T extends Comparable<T>> int partition(T[] arr, int leftIndex, int rightIndex) {
        int j = leftIndex;
        for (int i = leftIndex + 1; i <= rightIndex; i++) {
            if (arr[leftIndex].compareTo(arr[i]) > 0) {
                ArraySorts.swap(arr, ++j, i);
            }
        }
        if (j > leftIndex) {
            ArraySorts.swap(arr, j, leftIndex);
        }
        return j;
    }
}
