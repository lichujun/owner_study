package com.lee.owner.algorithm;

/**
 * 排序抽象类
 * @Date 2019-08-26 12:56
 * @Author joseph.li
 */
public abstract class AbstractArraySort implements ArraySort {

    @Override
    public <T extends Comparable<T>> T[] sort(T[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        return doRealSort(arr);
    }

    /**
     * 做排序的算法实现
     * @param arr 数组
     * @param <T> 范型
     * @return 排序好的数组
     */
    public abstract  <T extends Comparable<T>> T[] doRealSort(T[] arr);
}
