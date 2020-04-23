package com.lee.owner.chain;

/**
 * @author joseph.li
 * @date 2020/4/23 5:13 下午
 */
public abstract class BaseChain<T> implements DemoChain<T> {

    private DemoChain<T> demoChain;

    @Override
    public T execute(T t) {
        t = doExecute(t);
        if (demoChain != null) {
            t = demoChain.execute(t);
        }
        return t;
    }

    protected abstract T doExecute(T t);

    @Override
    public DemoChain<T> addNext(DemoChain<T> demoChain) {
        this.demoChain = demoChain;
        return demoChain;
    }
}
