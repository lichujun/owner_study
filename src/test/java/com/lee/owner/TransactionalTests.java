package com.lee.owner;

import com.lee.owner.repository.ProduceTaskRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;

/**
 * @author joseph.li
 * @date 2020/6/22 4:50 下午
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@EnableAspectJAutoProxy(exposeProxy=true)
@EnableTransactionManagement
public class TransactionalTests {

    @Resource
    private ProduceTaskRepository produceTaskRepository;

    @Test
    public void test() {
        produceTaskRepository.test(1);
    }

}
