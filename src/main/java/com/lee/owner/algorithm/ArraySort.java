package com.lee.owner.algorithm;


/**
 * 排序
 */
public interface ArraySort {

    /**
     * 对数组进行排序
     * @param arr 数组
     * @return 排序好的数组
     */
    <T extends Comparable<T>> T[] sort(T[] arr);
}
