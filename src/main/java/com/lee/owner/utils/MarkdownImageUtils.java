package com.lee.owner.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import java.io.*;
import java.util.Base64;

/**
 * @author joseph.li
 * @date 2020/3/31 6:04 下午
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MarkdownImageUtils {

    private static final String st = "data:image/png;base64,";

    private static String fileToBase64(File file) throws Exception {
        try (InputStream in = new FileInputStream(file)) {
            byte[] data = new byte[in.available()];
            if (in.read(data) > 0) {
                return Base64.getEncoder().encodeToString(data);
            } else {
                return null;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        File file = new File("/Users/joseph.li/Downloads/mysql-explain/10-1.png");
        System.out.println(fileToBase64(file));
    }
}
