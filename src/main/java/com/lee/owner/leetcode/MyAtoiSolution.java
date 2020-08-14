package com.lee.owner.leetcode;

/**
 * @author joseph.li
 * @date 2020/8/14 4:23 下午
 */
public class MyAtoiSolution {

    private static final int MAX = Integer.MAX_VALUE / 10;
    private static final int MIN = Integer.MIN_VALUE / 10;

    private static final char CODE_0 = '0';
    private static final char CODE_9 = '9';
    private static final char NEGATIVE = '-';
    private static final char POSITIVE = '+';
    private static final char BLANK = ' ';

    public int myAtoi(String str) {
        int length = str.length();
        boolean beginAtoi = true;
        char curCh;
        int res = 0;
        boolean negative = false;
        for (int i = 0; i < length; i++) {
            curCh = str.charAt(i);
            if (beginAtoi) {
                if (NEGATIVE == curCh) {
                    negative = true;
                    beginAtoi = false;
                    continue;
                }
                if (POSITIVE == curCh) {
                    beginAtoi = false;
                    continue;
                }
                if (BLANK == curCh) {
                    continue;
                }
                if (curCh > CODE_9 || curCh < CODE_0) {
                    return 0;
                }
            } else {
                if (BLANK == curCh) {
                    break;
                }
            }
            if (curCh <= CODE_9 && curCh >= CODE_0) {
                beginAtoi = false;
                if (res < MIN || (res == MIN && curCh > '8')) {
                    return Integer.MIN_VALUE;
                } else if (res > MAX || (res == MAX && curCh > '7')) {
                    return Integer.MAX_VALUE;
                }
                if (negative) {
                    res = 10 * res - (curCh - '0');
                } else {
                    res = 10 * res + (curCh - '0');
                }
            } else {
                return res;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(new MyAtoiSolution().myAtoi("-2147483649"));
    }
}
