package com.lee.owner.leetcode;

/**
 * @author joseph.li
 * @date 2020/8/14 11:29 上午
 */
public class ZConvertSolution {

    public static void main(String[] args) {
        System.out.println(new ZConvertSolution().convert1("PAYPALISHIRING", 3));
    }

    public String convert(String s, int numRows) {
        int length = s.length();
        if (length <= numRows || numRows <= 1) {
            return s;
        }
        char[] chars = new char[length];
        int j = 0;
        int k;
        int index;
        for (int i = 0; i < numRows; i++) {
            k = 0;
            while (true) {
                index = ((numRows - 1) << 1) * k + i;
                if (index >= length) {
                    break;
                }
                chars[j++] = s.charAt(index);
                if (i < numRows - 1 && i > 0) {
                    index = index + ((numRows - i - 1) << 1);
                    if (index < length) {
                        chars[j++] = s.charAt(index);
                    }
                }
                k++;
            }
        }
        return new String(chars);
    }

    public String convert1(String s, int numRows) {
        int length = s.length();
        if (length <= numRows || numRows <= 1) {
            return s;
        }
        StringBuilder[] builders = new StringBuilder[numRows];
        for (int i = 0; i < numRows; i++) {
            builders[i] = new StringBuilder();
        }
        int flag = -1;
        int i = 0;
        for (char ch : s.toCharArray()) {
            builders[i].append(ch);
            if (i == 0 || i == numRows - 1) {
                flag = - flag;
            }
            i += flag;
        }
        StringBuilder stringBuilder = builders[0];
        for (int i1 = 0; i1 < builders.length; i1++) {
            if (i1 != 0) {
                stringBuilder.append(builders[i1]);
            }
        }
        return stringBuilder.toString();
    }
}
