package com.lee.owner.leetcode;

/**
 * @author joseph.li
 * @date 2020/8/14 10:06 上午
 */
public class LongestPalindromeSolution {

    private int start = 0;

    private int end = 0;

    private int count = 0;

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
        return s.substring(start + 1, end);
    }

    public void positionLongestPalindrome(String s, int position) {
        if ((position << 1) < count || ((s.length() - position) << 1) <= count) {
            return;
        }
        positionLongestPalindrome(s, position - 1, position + 1);
        positionLongestPalindrome(s, position - 1, position);
    }

    public void positionLongestPalindrome(String s, int left, int right) {
        if (end - start - 1 > Math.min((left + 1) << 1, (s.length() - left + 1) << 1)) {
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
        if (right - left > end - start) {
            start = left;
            end = right;
            count = end - start - 1;
        }
    }

    public String longestPalindrome1(String s) {
        if (s == null) {
            return s;
        }
        int length = s.length();
        if (length <= 1) {
            return s;
        }
        char[] chs = s.toCharArray();
        findLongest(chs, 0);
        return s.substring(start, end + 1);
    }

    private void findLongest(char[] chs, int index) {
        int length = chs.length;
        if (index >= length || index < 0 || ((length - index - 1) << 1) <= count) {
            return;
        }
        int curEnd = index;
        int curStart = index;
        while (curEnd + 1 < length && chs[curEnd] == chs[curEnd + 1]) {
            curEnd++;
        }
        index = curEnd;
        while (curStart - 1 >= 0 && curEnd + 1 < length && chs[curStart - 1] == chs[curEnd + 1]) {
            curEnd++;
            curStart--;
        }
        if (curEnd - curStart > count) {
            start = curStart;
            end = curEnd;
            count = end - start;
        }
        findLongest(chs, index + 1);
    }

    public static void main(String[] args) {
        System.out.println(new LongestPalindromeSolution().longestPalindrome1("bb"));
    }
}
