package com.lee.owner.chain.impl;

import com.lee.owner.chain.BaseChain;
import com.lee.owner.chain.DemoReq;

/**
 * @author joseph.li
 * @date 2020/4/23 5:15 下午
 */
public class SecondChain extends BaseChain {

    @Override
    protected DemoReq doExecute(DemoReq demoReq) {
        System.out.println(demoReq.getName());
        demoReq.setName("second");
        return demoReq;
    }
}
