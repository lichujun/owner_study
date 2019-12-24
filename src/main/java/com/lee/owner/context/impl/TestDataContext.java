package com.lee.owner.context.impl;

import com.lee.owner.context.AbstractDataContext;
import com.lee.owner.context.DataContext;

/**
 * @author joseph.li
 * @date 2019-12-24 18:59
 */
public class TestDataContext extends AbstractDataContext<Integer, String> {

    @Override
    protected String doGetData(Integer integer) {
        System.out.println("获取数据");
        return String.valueOf(integer + 1);
    }

    public static void main(String[] args) {
        DataContext<Integer, String> dataContext = new TestDataContext();
        System.out.println(dataContext.getData(12));
        System.out.println(dataContext.getData(12));
        dataContext.releaseResource();
    }
}
