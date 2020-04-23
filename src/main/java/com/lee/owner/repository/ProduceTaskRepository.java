package com.lee.owner.repository;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lee.owner.repository.mapper.ProduceTaskMapper;
import com.lee.owner.repository.model.ProduceTask;
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


    public void test(long count) {
        ((ProduceTaskRepository) AopContext.currentProxy()).test1(count);
    }


    private void test1(long count) {
       test2(count);
    }

    @Transactional(rollbackFor = Exception.class)
    public void test2(long count) {
        ProduceTask produceTask = new ProduceTask();
        produceTask.setCurrentProduceId(count);
        UpdateWrapper<ProduceTask> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", 1);
        produceTaskMapper.update(produceTask, updateWrapper);
        if (count == 3) {
            throw new RuntimeException();
        }
    }
}
