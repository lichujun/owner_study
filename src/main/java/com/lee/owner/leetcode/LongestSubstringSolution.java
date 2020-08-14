package com.lee.owner.leetcode;

/**
 * @author joseph.li
 * @date 2020/8/14 10:06 上午
 */
public class LongestSubstringSolution {

    public int lengthOfLongestSubstring1(String input) {
        int[] lastIndex = new int[128];
        int length = input.length();
        int curMaxLength = 0;
        int curIndex;
        int repeatIndex;
        int lastRepeatIndex = 0;
        for (int i = 0; i < length; i++) {
            curIndex = input.charAt(i);
            repeatIndex = lastIndex[curIndex];
            lastIndex[curIndex] = i + 1;
            lastRepeatIndex = Math.max(lastRepeatIndex, repeatIndex);
            curMaxLength = Math.max(curMaxLength, i - lastRepeatIndex + 1);
        }
        return curMaxLength;
    }

    public int lengthOfLongestSubstring(String input) {
        int length = input.length();
        int maxLength = 0;
        int lastRepeatIndex = -1;
        int firstRepeatIndex;
        for (int i = 0; i < length && maxLength <= length - lastRepeatIndex; i++) {
            firstRepeatIndex = findRepeatIndex(input, input.charAt(i), lastRepeatIndex + 1, i);
            if (firstRepeatIndex >= 0) {
                lastRepeatIndex = firstRepeatIndex;
            }
            maxLength = Math.max(maxLength, i - lastRepeatIndex);
        }
        return maxLength;
    }

    private int findRepeatIndex(String input, int cur, int start, int end) {
        if (start < 0) {
            start = 0;
        } else if (start >= end) {
            return -1;
        }
        for (int i = start; i < end; i++) {
            if (input.charAt(i) == cur) {
                return i;
            }
        }
        return -1;
    }
}
