package com.lee.owner.leetcode;

/**
 * @author joseph.li
 * @date 2020/8/17 5:30 下午
 */
public class IntToRomanSolution {

    public static void main(String[] args) {
        System.out.println(new IntToRomanSolution().intToRoman1(60));
    }

    private static final int[] VALUES = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    private static final String[] SYMBOLS = {"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};

    public String intToRoman1(int num) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < VALUES.length; i++) {
            while (num >= VALUES[i]) {
                num -= VALUES[i];
                stringBuilder.append(SYMBOLS[i]);
            }
        }
        return stringBuilder.toString();
    }

    private static final int NINE = 9;

    private static final int FOUR = 4;

    private static final int FIVE = 5;

    public String intToRoman(int num) {
        int x = num / 1000;
        StringBuilder stringBuilder = new StringBuilder();
        if (x > 0) {
            for (int i = 0; i < x; i++) {
                stringBuilder.append("M");
            }
            num = num - x * 1000;
        }

        x = num / 100;
        if (x > 0) {
            if (x == NINE) {
                stringBuilder.append("CM");
            } else if (x >= FIVE) {
                stringBuilder.append("D");
                for (int i = 0; i < x - FIVE; i++) {
                    stringBuilder.append("C");
                }
            } else if (x == FOUR) {
                stringBuilder.append("CD");
            } else {
                for (int i = 0; i < x; i++) {
                    stringBuilder.append("C");
                }
            }
            num = num - x * 100;
        }

        x = num / 10;
        if (x > 0) {
            if (x == NINE) {
                stringBuilder.append("XC");
            } else if (x >= FIVE) {
                stringBuilder.append("L");
                for (int i = 0; i < x - FIVE; i++) {
                    stringBuilder.append("X");
                }
            } else if (x == FOUR) {
                stringBuilder.append("XL");
            } else {
                for (int i = 0; i < x; i++) {
                    stringBuilder.append("X");
                }
            }
            num = num - x * 10;
        }

        if (num > 0) {
            if (num == NINE) {
                stringBuilder.append("IX");
            } else if (num >= FIVE) {
                stringBuilder.append("V");
                for (int i = 0; i < num - FIVE; i++) {
                    stringBuilder.append("I");
                }
            } else if (num == FOUR) {
                stringBuilder.append("IV");
            } else {
                for (int i = 0; i < num; i++) {
                    stringBuilder.append("I");
                }
            }
        }
        return stringBuilder.toString();
    }

}
