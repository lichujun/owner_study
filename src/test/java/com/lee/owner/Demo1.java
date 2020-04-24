package com.lee.owner;

import com.lee.owner.chain.DemoReq;
import com.lee.owner.chain.impl.AbstractDemoChain;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @author joseph.li
 * @date 2020/4/23 5:16 下午
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class Demo1 {

    @Resource
    private List<AbstractDemoChain> abstractDemoChains;

    @Test
    public void test() {
        DemoReq demoReq = new DemoReq();
        demoReq.setName("init");
        Collections.sort(abstractDemoChains);

        for (int i = 0; i < abstractDemoChains.size() - 1; i++) {
            abstractDemoChains.get(i).addNext(abstractDemoChains.get(i+1));
        }

        abstractDemoChains.get(0).execute(demoReq);
    }
}
