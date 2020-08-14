package com.lee.owner.leetcode;

/**
 * @author joseph.li
 * @date 2020/8/14 5:01 下午
 */
public class NumberPalindromeSolution {

    public static void main(String[] args) {
        System.out.println(new NumberPalindromeSolution().isPalindrome(121));
    }

    public boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        }
        if (x == 0) {
            return true;
        }
        if (x % 10 == 0) {
            return false;
        }
        int reverseNumber = 0;
        int pop;
        while (x > reverseNumber) {
            pop = x % 10;
            reverseNumber = 10 * reverseNumber + pop;
            x /= 10;
        }
        return x == reverseNumber || x == reverseNumber / 10;
    }
}
