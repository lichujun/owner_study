package com.lee.owner.algorithm;

import java.util.List;

/**
 * 排序
 */
public interface Sort {

    /**
     * 对集合进行排序
     * @param collection 集合
     * @return 排序好的集合
     */
    <T extends Comparable> List<T> sort(List<T> collection);
}
