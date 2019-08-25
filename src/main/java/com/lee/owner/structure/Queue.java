package com.lee.owner.structure;

/**
 * 队列
 * @param <T> 队列存储的数据类型
 */
public interface Queue<T> {

    void push(T t);

    T pop();
}
