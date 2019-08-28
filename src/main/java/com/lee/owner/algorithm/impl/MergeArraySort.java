package com.lee.owner.algorithm.impl;

import com.lee.owner.algorithm.AbstractArraySort;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * 归并排序
 * @Date 2019-08-27 19:16
 * @Author joseph.li
 */
@Component
public class MergeArraySort extends AbstractArraySort {

    @Override
    protected <T extends Comparable<T>> T[] doRealSort(T[] arr) {
        return mergeSort(arr);
    }

    private <T extends Comparable<T>> T[] mergeSort(T[] arr) {
        int size = arr.length;
        if (size < 2) {
            return arr;
        }
        int middle = size / 2;
        T[] leftArr = Arrays.copyOfRange(arr, 0, middle);
        T[] rightArr = Arrays.copyOfRange(arr, middle, size);
        return merge(mergeSort(leftArr), mergeSort(rightArr));
    }

    private <T extends Comparable<T>> T[] merge(T[] leftArr, T[] rightArr) {
        int leftSize = leftArr.length;
        int rightSize = rightArr.length;
        Class clazz;
        if (leftSize == 0) {
            if (rightSize == 0) {
                return rightArr;
            } else {
                clazz = rightArr[0].getClass();
            }
        }  else {
            clazz = leftArr[0].getClass();
        }
        int size = leftArr.length + rightArr.length;
        @SuppressWarnings("unchecked")
        T[] resultArr = (T[]) Array.newInstance(clazz, size);

        int leftIndex = 0;
        int rightIndex = 0;

        for (int i = 0; i < size; i++) {
            if (rightIndex == rightArr.length) {
                resultArr[i] = leftArr[leftIndex++];
            } else if (leftIndex == leftArr.length) {
                resultArr[i] = rightArr[rightIndex++];
            } else if (leftArr[leftIndex].compareTo(rightArr[rightIndex]) < 0) {
                resultArr[i] = leftArr[leftIndex++];
            } else {
                resultArr[i] = rightArr[rightIndex++];
            }
        }
        return resultArr;
    }
}
