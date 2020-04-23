package com.lee.owner.chain;

/**
 * @author joseph.li
 * @date 2020/4/23 5:12 下午
 */
public interface DemoChain {

    DemoReq execute(DemoReq demoReq);

    DemoChain addNext(DemoChain demoChain);
}
