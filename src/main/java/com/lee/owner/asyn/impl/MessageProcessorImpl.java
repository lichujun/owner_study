package com.lee.owner.asyn.impl;

import com.google.common.collect.Lists;
import com.lee.owner.asyn.MessageProcessor;
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
public class MessageProcessorImpl implements MessageProcessor {

    private static final int PROCESS_FAIL_MAX_TIME = 6;

    private static final List<Integer> PROCESS_FAIL_WAIT_TIME = Lists.newArrayList(0, 10, 100, 1000, 2000, 5000, 10000);

    private final AtomicInteger processEmptyCount;

    private final AtomicInteger processStatus;

    public MessageProcessorImpl() {
        this.processStatus = new AtomicInteger();
        this.processEmptyCount = new AtomicInteger();
    }

    @Override
    public boolean canProcess() {
        if (processStatus.get() > 0) {
            return false;
        }
        return processStatus.compareAndSet(0, 1);
    }

    @Override
    public void processEmptyWait() {
        int processWaitTimeIndex;
        if (processEmptyCount.get() >= PROCESS_FAIL_MAX_TIME) {
            processWaitTimeIndex = PROCESS_FAIL_MAX_TIME;
        } else {
            synchronized (processEmptyCount) {
                if (processEmptyCount.get() >= PROCESS_FAIL_MAX_TIME) {
                    processWaitTimeIndex = PROCESS_FAIL_MAX_TIME;
                } else {
                    processWaitTimeIndex = processEmptyCount.incrementAndGet();
                }
                processWaitTimeIndex = Math.min(processWaitTimeIndex, PROCESS_FAIL_MAX_TIME);
            }
        }
        int waitTime = PROCESS_FAIL_WAIT_TIME.get(processWaitTimeIndex);
        log.info("process fail, wait time:{}", waitTime);
        LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(waitTime));
    }

    @Override
    public void initProcessEmptyCount() {
        synchronized (processEmptyCount) {
            processEmptyCount.set(0);
        }
    }

    private AtomicInteger getProcessCount() {
        return processEmptyCount;
    }
}
