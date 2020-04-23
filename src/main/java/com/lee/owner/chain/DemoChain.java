package com.lee.owner.chain;

/**
 * @author joseph.li
 * @date 2020/4/23 5:12 下午
 */
public interface DemoChain<T> {

    T execute(T t);

    DemoChain<T> addNext(DemoChain<T> demoChain);
}
