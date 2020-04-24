package com.lee.owner.chain.impl;

import com.lee.owner.chain.BaseComparableChain;
import com.lee.owner.chain.DemoReq;

/**
 * @author joseph.li
 * @date 2020/4/24 12:24 下午
 */
public abstract class AbstractDemoChain extends BaseComparableChain<DemoReq, Integer> {

    private final ChainMark chainMark = getClass().getAnnotation(ChainMark.class);

    @Override
    protected Integer getComparableValue() {
        return chainMark.value();
    }
}
