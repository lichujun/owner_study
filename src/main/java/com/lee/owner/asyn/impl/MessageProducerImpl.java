package com.lee.owner.asyn.impl;

import com.google.common.collect.Lists;
import com.lee.owner.asyn.MessageProducer;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;
import java.util.function.Supplier;

/**
 * @author joseph.li
 * @date 2020/6/23 3:06 下午
 */
@Slf4j
public class MessageProducerImpl<T> implements MessageProducer<T> {

    private static final int THREAD_KEEP_ALIVE_TIME = 60;

    private static final int PRODUCE_NULL_MAX_TIME = 6;

    private static final List<Integer> PRODUCE_NULL_WAIT_TIME = Lists.newArrayList(0, 10, 100, 1000, 2000, 5000, 10000);

    private BlockingQueue<T> queue;

    private final Integer produceThreadSize;

    private final ExecutorService ioThreadPool;

    private final AtomicInteger produceNullCount;

    private final AtomicInteger produceStatus;

    public MessageProducerImpl(BlockingQueue<T> queue) {
        this(queue, 4);
    }

    public MessageProducerImpl(BlockingQueue<T> queue, Integer produceThreadSize) {
        this.queue = queue;
        this.produceThreadSize = produceThreadSize;
        produceNullCount = new AtomicInteger();
        produceStatus = new AtomicInteger();
        ioThreadPool = new ThreadPoolExecutor(produceThreadSize, produceThreadSize,
                THREAD_KEEP_ALIVE_TIME, TimeUnit.SECONDS, new SynchronousQueue<>(),
                new DefaultThreadFactory("message-producer"), new ThreadPoolExecutor.AbortPolicy());
    }

    @Override
    public void produceMessage(Supplier<T> supplier) {
        if (!canProduce()) {
            log.info("is producing");
            return;
        }
        for (int i = 0; i < produceThreadSize; i++) {
            ioThreadPool.execute(() -> {
                while (true) {
                    T message = supplier.get();
                    if (message == null) {
                        produceNullWait();
                        continue;
                    }
                    putMessage(message);
                }
            });
        }
    }

    @Override
    public void produceBatchMessage(Supplier<Collection<T>> supplier) {
        if (!canProduce()) {
            log.info("is producing");
            return;
        }
        for (int i = 0; i < produceThreadSize; i++) {
            ioThreadPool.execute(() -> {
                while (true) {
                    Collection<T> collection = supplier.get();
                    if (CollectionUtils.isEmpty(collection)) {
                        produceNullWait();
                        continue;
                    }
                    for (T t : collection) {
                        putMessage(t);
                    }
                }
            });
        }
    }

    private boolean canProduce() {
        if (produceStatus.get() > 0) {
            return false;
        }
        return produceStatus.compareAndSet(0, 1);
    }

    private void produceNullWait() {
        int produceWaitTimeIndex;
        if (produceNullCount.get() >= PRODUCE_NULL_MAX_TIME) {
            produceWaitTimeIndex = PRODUCE_NULL_MAX_TIME;
        } else {
            synchronized (this) {
                if (produceNullCount.get() >= PRODUCE_NULL_MAX_TIME) {
                    produceWaitTimeIndex = PRODUCE_NULL_MAX_TIME;
                } else {
                    produceWaitTimeIndex = produceNullCount.incrementAndGet();
                }
                produceWaitTimeIndex = Math.min(produceWaitTimeIndex, PRODUCE_NULL_MAX_TIME);
            }
        }
        int waitTime = PRODUCE_NULL_WAIT_TIME.get(produceWaitTimeIndex);
        log.info("produce null, wait time:{}", waitTime);
        LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(waitTime));
    }

    private void putMessage(T t) {
        try {
            if (t == null) {
                return;
            }
            queue.put(t);
        } catch (InterruptedException e) {
            log.error("put message error", e);
        }
    }
}
