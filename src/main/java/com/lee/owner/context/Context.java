package com.lee.owner.context;

/**
 * 数据存储
 * @param <K> Key
 * @param <V> Value
 * @author joseph.li
 */
public interface Context<K, V> {

    /**
     * 释放资源
     */
    void releaseResource();

    /**
     * 获取数据
     * @param k key
     * @return value
     */
    V getData(K k);
}
