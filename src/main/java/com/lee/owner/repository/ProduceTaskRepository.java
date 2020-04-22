package com.lee.owner.repository;

import com.lee.owner.repository.mapper.ProduceTaskMapper;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;

/**
 * @author joseph.li
 * @date 2020/4/22 6:07 下午
 */
@Component
public class ProduceTaskRepository {

    @Resource
    private ProduceTaskMapper produceTaskMapper;

    public void test() {
        ((ProduceTaskRepository) AopContext.currentProxy()).test1();
    }

    @Transactional(rollbackFor = Exception.class)
    public void test1() {
        produceTaskMapper.selectById(1);
        throw new RuntimeException();
    }
}
