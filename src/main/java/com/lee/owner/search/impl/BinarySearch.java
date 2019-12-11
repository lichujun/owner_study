package com.lee.owner.search.impl;

import com.lee.owner.search.Search;

/**
 * 二分查找
 * @author joseph.li
 * @date 2019-12-05 19:54
 */
public class BinarySearch implements Search {


    public static void main(String[] args) {
        int[] arr = {1, 4, 4, 7, 8, 10, 23, 76, 123, 444, 543, 1233};
        System.out.println(new BinarySearch().search(arr, 123));
    }

    @Override
    public int search(int[] arr, int target) {
        int leftIndex = 0;
        int rightIndex = arr.length - 1;
        int midIndex;
        while (leftIndex <= rightIndex) {
            midIndex = leftIndex + (rightIndex - leftIndex) / 2;
            if (arr[midIndex] == target) {
                return midIndex;
            }
            if (arr[midIndex] < target) {
                leftIndex = midIndex + 1;
            } else {
                rightIndex = midIndex - 1;
            }
        }
        return -1;
    }
}
