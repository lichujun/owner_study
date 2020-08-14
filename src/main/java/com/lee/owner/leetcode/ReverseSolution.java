package com.lee.owner.leetcode;

/**
 * @author joseph.li
 * @date 2020/8/14 3:40 下午
 */
public class ReverseSolution {

    public static void main(String[] args) {
        System.out.println(new ReverseSolution().reverse1(-123));
    }

    private static final int MAX = Integer.MAX_VALUE / 10;
    private static final int MIN = Integer.MIN_VALUE / 10;

    /**
     * my brother write this, not me
     */
    public int reverse(int x) {
        if (x == Integer.MIN_VALUE) {
            return 0;
        }
        boolean negativeNumber = x < 0;
        if (negativeNumber) {
            x = -x;
        }
        String str = String.valueOf(x);
        String reverseStr = new StringBuilder(str).reverse().toString();
        long a = Long.parseLong(reverseStr);
        if (a > Integer.MAX_VALUE || a < Integer.MIN_VALUE) {
            a = 0;
        }
        if (negativeNumber) {
            return (int) -a;
        } else {
            return (int) a;
        }
    }

    public int reverse1(int x) {
        if (x == Integer.MIN_VALUE) {
            return 0;
        }
        int res = 0;
        int pop;
        while (x != 0) {
            if (res > MAX || res < MIN) {
                return 0;
            }
            pop = x % 10;
            res = pop + res * 10;
            x /= 10;
        }
        return res;
    }

}
