package com.lee.owner.asyn.impl;

import com.lee.owner.asyn.IOService;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author joseph.li
 * @date 2020/6/22 5:03 下午
 */
@Slf4j
@Component
public class IOServiceImpl implements IOService {

    private static final int MAX_SIZE = 20;

    private static final int THREAD_SIZE = 4;

    private BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(MAX_SIZE);

    private AtomicInteger a = new AtomicInteger(1);

    private ExecutorService ioThreadPool = new ThreadPoolExecutor(THREAD_SIZE, THREAD_SIZE, 60,
            TimeUnit.SECONDS, new SynchronousQueue<>(), new DefaultThreadFactory("io"));

    @PostConstruct
    public void init() {
        for (int i = 0; i < THREAD_SIZE; i++) {
            ioThreadPool.execute(() -> {
                while (true) {
                    int currentA = a.getAndIncrement();
                    try {
                        queue.put(currentA);
                        log.info("current:{}", currentA);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    public Integer getOneMsg() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            log.error("error:", e);
            return null;
        }
    }
}
