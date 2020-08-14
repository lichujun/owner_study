package com.lee.owner.leetcode;

import org.apache.commons.lang3.time.StopWatch;

/**
 * @author joseph.li
 * @date 2020/8/12 6:15 下午
 */
public class LeetCodeSolution {

    public static void main(String[] args) {
        //System.out.println(new LeetCodeSolution().lengthOfLongestSubstring1(" "));
        int n = 10000;
        int[] nums1 = new int[n];
        int[] nums2 = new int[n];


        for (int i = 0; i < n; i++) {
            nums1[i] = i;
            nums2[i] = 2 * i;
        }
        MedianSortedArraysSolution leetCodeSolution = new MedianSortedArraysSolution();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        leetCodeSolution.findMedianSortedArrays(nums1, nums2);
        System.out.println("time:" + stopWatch.getNanoTime() / 1000);

    }


}
