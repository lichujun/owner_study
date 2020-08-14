package com.lee.owner.leetcode;

/**
 * @author joseph.li
 * @date 2020/8/14 10:06 上午
 */
public class LongestPalindromeSolution {

    private int maxLeft = 0;

    private int maxRight = 0;

    public String longestPalindrome(String s) {
        if (s.length() == 0) {
            return "";
        }
        int length = s.length();
        int middle = length >> 1;
        positionLongestPalindrome(s, middle);
        for (int i = middle; i >= 0; i--) {
            positionLongestPalindrome(s, middle - i);
            if (middle + i < length) {
                positionLongestPalindrome(s, middle + i);
            }
        }
        return s.substring(maxLeft + 1, maxRight);
    }

    public void positionLongestPalindrome(String s, int position) {
        positionLongestPalindrome(s, position - 1, position + 1);
        positionLongestPalindrome(s, position - 1, position);
    }

    public void positionLongestPalindrome(String s, int left, int right) {
        if (maxRight - maxLeft - 1 > Math.min((left + 1) << 1, (s.length() - left + 1) << 1)) {
            return;
        }
        int length = s.length();
        while (left >= 0 && right < length) {
            if (s.charAt(left) != s.charAt(right)) {
                break;
            }
            left--;
            right++;
        }
        if (right - left > maxRight - maxLeft) {
            maxLeft = left;
            maxRight = right;
        }
    }
}
