package com.lee.owner.leetcode;

/**
 * @author joseph.li
 * @date 2020/8/14 10:07 上午
 */
public class MedianSortedArraysSolution {

    public double findMedianSortedArrays1(int[] nums1, int[] nums2) {
        int length1 = nums1.length;
        int length2 = nums2.length;
        int totalLength = length1 + length2;
        int left = 0;
        int right = 0;
        int start1 = 0;
        int start2 = 0;
        for (int i = 0; i <= (totalLength >> 1); i++) {
            left = right;
            if (start1 < length1 && (start2 >= length2 || nums1[start1] < nums2[start2])) {
                right = nums1[start1++];
            } else {
                right = nums2[start2++];
            }
        }
        if ((totalLength & 1) == 0) {
            return (double) (left + right) / 2;
        }
        return right;
    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int length1 = nums1.length;
        int length2 = nums2.length;

        int totalLength = length1 + length2;
        int needExcludeCount = (totalLength - 1) >> 1;
        int start1 = 0;
        int start2 = 0;
        int end1;
        int end2;
        int k;
        boolean finalCompareResult = false;

        while (needExcludeCount > 0) {
            if (start1 >= length1 || start2 >= length2) {
                break;
            }
            k = needExcludeCount == 1 ? 1 : needExcludeCount >> 1;
            end1 = getEndIndex(length1, start1, k);
            end2 = getEndIndex(length2, start2, k);

            if (nums1[end1] > nums2[end2]) {
                needExcludeCount -= end2 - start2 + 1;
                start2 = end2 + 1;
                finalCompareResult = true;
            } else {
                needExcludeCount -= end1 - start1 + 1;
                start1 = end1 + 1;
                finalCompareResult = false;
            }
        }
        boolean lengthCountEven = (totalLength & 1) == 0;
        if (finalCompareResult) {
            return processResult(nums1, nums2, start1, start2, lengthCountEven, needExcludeCount);
        } else {
            return processResult(nums2, nums1, start2, start1, lengthCountEven, needExcludeCount);
        }
    }

    private double processResult(int[] nums1, int[] nums2, int start1, int start2, boolean lengthCountEven, int needExcludeCount) {
        if (lengthCountEven) {
            if (start2 >= nums2.length) {
                return (double) (nums1[start1 + needExcludeCount] + nums1[start1 + 1 + needExcludeCount]) / 2;
            } else if (start1 >= nums1.length) {
                return (double) (nums2[start2 + needExcludeCount] + nums2[start2 + 1 + needExcludeCount]) / 2;
            }
            if (nums1[start1] < nums2[start2]) {
                return doProcessResult(nums1, nums2, start1, start2);
            } else {
                return doProcessResult(nums2, nums1, start2, start1);
            }
        } else {
            if (start1 >= nums1.length) {
                return nums2[start2 + needExcludeCount];
            }
            if (start2 >= nums2.length || nums1[start1] < nums2[start2]) {
                return nums1[start1 + needExcludeCount];
            } else {
                return nums2[start2 + needExcludeCount];
            }
        }
    }

    private double doProcessResult(int[] nums1, int[] nums2, int start1, int start2) {
        if (start1 + 1 < nums1.length) {
            if (start2 >= nums2.length || nums1[start1 + 1] < nums2[start2]) {
                return (double) (nums1[start1] + nums1[start1 + 1]) / 2;
            } else {
                return (double) (nums1[start1] + nums2[start2]) / 2;
            }
        } else {
            return (double) (nums1[start1] + nums2[start2]) / 2;
        }
    }

    private int getEndIndex(int length, int start, int k) {
        int end = k + start - 1;
        if (end >= length) {
            return length - 1;
        } else {
            return end;
        }
    }

}
