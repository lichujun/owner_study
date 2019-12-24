package com.lee.owner.context.impl;

import com.lee.owner.context.AbstractContext;
import com.lee.owner.context.Context;

/**
 * @author joseph.li
 * @date 2019-12-24 18:59
 */
public class TestContext extends AbstractContext<Integer, String> {

    @Override
    protected String doGetData(Integer integer) {
        System.out.println("获取数据");
        return String.valueOf(integer + 1);
    }

    public static void main(String[] args) {
        Context<Integer, String> context = new TestContext();
        System.out.println(context.getData(12));
        System.out.println(context.getData(12));
        context.releaseResource();
    }
}
