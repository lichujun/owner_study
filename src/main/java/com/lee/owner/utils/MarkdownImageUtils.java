package com.lee.owner.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author joseph.li
 * @date 2020/3/31 6:04 下午
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MarkdownImageUtils {

    private static final String IMAGE_START = "data:image/%s;base64,";

    private static String fileToBase64(File file) throws Exception {
        try (InputStream in = new FileInputStream(file)) {
            byte[] data = new byte[in.available()];
            if (in.read(data) > 0) {
                String fileName = file.getName();
                String start = String.format(IMAGE_START, fileName.substring(fileName.lastIndexOf(".") + 1));
                String realName = fileName.substring(0, (fileName.lastIndexOf(".")));
                return "[" + realName + "]" + ":" + start + Base64.getEncoder().encodeToString(data);
            } else {
                throw new RuntimeException();
            }
        }
    }

    private static void pathAllFileToBase64(String path) throws Exception {
        Set<File> imageFiles = getFile(path).stream()
                .filter(MarkdownImageUtils::isImage)
                .collect(Collectors.toSet());
        for (File imageFile : imageFiles) {
            System.out.println(fileToBase64(imageFile));
        }
    }

    private static Set<File> getFile(String path) {
        File fileOrigin = new File(path);

        if (fileOrigin.isFile()) {
            return Collections.singleton(fileOrigin);
        }

        File[] fileArr = fileOrigin.listFiles();

        if (fileArr == null) {
            return Collections.emptySet();
        }

        Set<File> fileSet = new HashSet<>();

        for (File file : fileArr) {
            if (file.isFile()) {
                fileSet.add(file);
            } else if (file.isDirectory()) {
                fileSet.addAll(getFile(file.getPath()));
            }
         }

        return fileSet;
    }

    private static boolean isImage(File file) {
        BufferedImage bi;
        try {
            bi = ImageIO.read(file);
        } catch (IOException e) {
            throw new RuntimeException();
        }
        return bi != null;
    }

    public static void main(String[] args) throws Exception {
        pathAllFileToBase64("/Users/joseph.li/Downloads/mysql-explain");
    }
}
