package com.lee.owner.chain;

/**
 * @author joseph.li
 * @date 2020/4/23 5:13 下午
 */
public abstract class BaseChain implements DemoChain {

    private DemoChain demoChain;

    @Override
    public DemoReq execute(DemoReq demoReq) {
        demoReq = doExecute(demoReq);
        if (demoChain != null) {
            demoReq = demoChain.execute(demoReq);
        }
        return demoReq;
    }

    protected abstract DemoReq doExecute(DemoReq demoReq);

    @Override
    public DemoChain addNext(DemoChain demoChain) {
        this.demoChain = demoChain;
        return demoChain;
    }
}
