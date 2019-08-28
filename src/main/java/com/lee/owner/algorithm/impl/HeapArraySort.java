package com.lee.owner.algorithm.impl;

import com.lee.owner.algorithm.AbstractArraySort;
import com.lee.owner.algorithm.ArraySorts;
import org.springframework.stereotype.Component;

/**
 * 堆排序
 * @Date 2019-08-28 14:13
 * @Author joseph.li
 */
@Component
public class HeapArraySort extends AbstractArraySort {

    @Override
    protected <T extends Comparable<T>> T[] doRealSort(T[] arr) {
        buildMaxTree(arr);
        for (int i = arr.length - 1; i > 0; i--) {
            ArraySorts.swap(arr, 0, i);
            adjustHeap(arr, 0, i);
        }
        return arr;
    }

    private <T extends Comparable<T>> void buildMaxTree(T[] arr) {
        int length = arr.length;
        // 调整倒数第二级以上的树节点
        for (int i = length / 2; i >= 0; i--) {
            adjustHeap(arr, i, length);
        }
    }

    /**
     * 调整索引为parent以下的树节点
     * @param arr 数组
     * @param parent 父节点
     * @param length 调整的最大长度
     * @param <T> 数组类型
     */
    private <T extends Comparable<T>> void adjustHeap(T[] arr, int parent, int length) {
        int left = 2 * parent + 1;
        int right = 2 * parent + 2;
        int largestIndex = parent;
        
        if (left >= length) {
            return;
        }
        
        if (arr[left].compareTo(arr[largestIndex]) > 0) {
            largestIndex = left;
        }
        
        if (right < length && arr[right].compareTo(arr[largestIndex]) > 0) {
            largestIndex = right;
        }
        
        if (largestIndex != parent) {
            ArraySorts.swap(arr, parent, largestIndex);
            adjustHeap(arr, largestIndex, length);
        }
        
    } 

}
