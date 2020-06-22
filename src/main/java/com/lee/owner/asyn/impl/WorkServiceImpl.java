package com.lee.owner.asyn.impl;

import com.lee.owner.asyn.IOService;
import com.lee.owner.asyn.WorkService;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author joseph.li
 * @date 2020/6/22 5:55 下午
 */
@Slf4j
@Component
public class WorkServiceImpl implements WorkService {

    private static final int THREAD_SIZE = 4;

    @Resource
    private IOService ioServiceImpl;

    private ExecutorService workThreadPool = new ThreadPoolExecutor(THREAD_SIZE, THREAD_SIZE, 60,
            TimeUnit.SECONDS, new SynchronousQueue<>(), new DefaultThreadFactory("work"));

    @PostConstruct
    public void init() {
        //processMsg();
    }

    @Override
    public void processMsg() {
        for (int i = 0; i < THREAD_SIZE; i++) {
            workThreadPool.execute(() -> {
                while (true) {
                    log.info("consumer:{}", ioServiceImpl.getOneMsg());
                }
            });
        }
    }
}
