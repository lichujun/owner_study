package com.lee.owner.leetcode;

import java.util.Objects;

/**
 * @author joseph.li
 * @date 2020/8/14 5:42 下午
 */
public class StringMatchSolution {

    private static final char ANY = '.';
    private static final char REPEAT = '*';

    public static void main(String[] args) {
        System.out.println(new StringMatchSolution().isMatch("aa", "a."));
    }

    public boolean isMatch(String s, String p) {
        if (s == null && p == null) {
            return true;
        }
        if (s == null || p == null) {
            return false;
        }
        int sLength = s.length();
        int pLength = p.length();
        if (sLength == 0 && pLength == 0) {
            return true;
        }
        if (Objects.equals(s, p)) {
            return true;
        }
        for (int i = 0; i < sLength; i++) {
            if (s.charAt(i) != p.charAt(i) && p.charAt(i) != ANY) {
                return false;
            }
        }
        return true;
    }
}
