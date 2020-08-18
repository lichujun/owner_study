package com.lee.owner.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author joseph.li
 * @date 2020/8/17 6:11 下午
 */
public class RomanToIntSolution {

    public static void main(String[] args) {
        System.out.println(new RomanToIntSolution().romanToInt("IV"));
    }

    private static final Map<Character, Integer> MAP = new HashMap<>();
    
    static {
        MAP.put('M', 1000);
        MAP.put('D', 500);
        MAP.put('C', 100);
        MAP.put('L', 50);
        MAP.put('X', 10);
        MAP.put('V', 5);
        MAP.put('I', 1);
    }

    private static final int[] VALUES = new int[] {1, 5, 10, 50, 100, 500, 1000};

    int getRank(char ch){
        switch (ch){
            case 'I':
                return 0;
            case 'V':
                return 1;
            case 'X':
                return 2;
            case 'L':
                return 3;
            case 'C':
                return 4;
            case 'D':
                return 5;
            case 'M':
                return 6;
            default:
                return -1;
        }
    }

    public int romanToInt(String s) {
        int curMax = 0;
        int cur;
        int num = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            cur = VALUES[getRank(s.charAt(i))];
            if (cur < curMax) {
                num -= cur;
            } else {
                curMax = cur;
                num += cur;
            }
        }
        return num;
    }


    public int romanToInt1(String s) {
        int num = 0;
        int cur;
        int curMax = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            cur = MAP.get(s.charAt(i));
            if (cur < curMax) {
                num -= cur;
            } else {
                curMax = cur;
                num += cur;
            }
        }
        return num;
    }
    
}
