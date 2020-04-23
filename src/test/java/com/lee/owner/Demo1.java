package com.lee.owner;

import com.lee.owner.chain.DemoChain;
import com.lee.owner.chain.DemoReq;
import com.lee.owner.chain.impl.FirstChain;
import com.lee.owner.chain.impl.SecondChain;
import com.lee.owner.chain.impl.ThirdChain;
import org.junit.Test;

/**
 * @author joseph.li
 * @date 2020/4/23 5:16 下午
 */
public class Demo1 {

    @Test
    public void test() {
        DemoReq demoReq = new DemoReq();
        demoReq.setName("init");
        DemoChain demoChain = new FirstChain();
        demoChain.addNext(new SecondChain())
                .addNext(new ThirdChain());
        demoChain.execute(demoReq);
    }
}
