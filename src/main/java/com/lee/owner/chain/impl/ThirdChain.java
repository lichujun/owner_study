package com.lee.owner.chain.impl;

import com.lee.owner.chain.DemoReq;
import org.springframework.stereotype.Component;

/**
 * @author joseph.li
 * @date 2020/4/23 5:15 下午
 */
@ChainMark(3)
@Component
public class ThirdChain extends AbstractDemoChain {

    @Override
    protected DemoReq doExecute(DemoReq demoReq) {
        System.out.println(demoReq.getName());
        demoReq.setName("third");
        System.out.println(demoReq.getName());
        return demoReq;
    }
}
