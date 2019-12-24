package com.lee.owner.context;

/**
 * 上下文数据
 * @param <K> Key
 * @param <V> Value
 * @author joseph.li
 */
public interface DataContext<K, V> {

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
