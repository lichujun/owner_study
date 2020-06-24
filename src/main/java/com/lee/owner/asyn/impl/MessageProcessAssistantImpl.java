package com.lee.owner.asyn.impl;

import com.google.common.collect.Lists;
import com.lee.owner.asyn.MessageProcessAssistant;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

/**
 * @author joseph.li
 * @date 2020/6/23 5:45 下午
 */
@Slf4j
public class MessageProcessAssistantImpl implements MessageProcessAssistant {

    private static final int PROCESS_MAX_TIME = 6;

    private static final List<Integer> PROCESS_WAIT_TIME = Lists.newArrayList(0, 10, 100, 1000, 2000, 3000, 5000);

    private final AtomicInteger processCount;

    private final AtomicInteger processStatus;

    public MessageProcessAssistantImpl() {
        this.processStatus = new AtomicInteger();
        this.processCount = new AtomicInteger();
    }

    @Override
    public boolean canProcess() {
        if (processStatus.get() > 0) {
            return false;
        }
        return processStatus.compareAndSet(0, 1);
    }

    @Override
    public void processWait() {
        int processWaitTimeIndex;
        if (processCount.get() >= PROCESS_MAX_TIME) {
            processWaitTimeIndex = PROCESS_MAX_TIME;
        } else {
            synchronized (processCount) {
                if (processCount.get() >= PROCESS_MAX_TIME) {
                    processWaitTimeIndex = PROCESS_MAX_TIME;
                } else {
                    processWaitTimeIndex = processCount.incrementAndGet();
                }
                processWaitTimeIndex = Math.min(processWaitTimeIndex, PROCESS_MAX_TIME);
            }
        }
        int waitTime = PROCESS_WAIT_TIME.get(processWaitTimeIndex);
        log.info("process fail, wait time:{}", waitTime);
        LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(waitTime));
    }

    @Override
    public void initProcessCount() {
        synchronized (processCount) {
            processCount.set(0);
        }
    }
}
