package com.lee.owner.asyn.impl;

import com.lee.owner.asyn.MessageProducer;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 * @author joseph.li
 * @date 2020/6/23 3:06 下午
 */
@Slf4j
public class MessageProducerImpl<T> implements MessageProducer<T> {

    private static final int THREAD_KEEP_ALIVE_TIME = 60;

    private BlockingQueue<T> queue;

    private final Integer produceThreadSize;

    private final ExecutorService ioThreadPool;

    public MessageProducerImpl(BlockingQueue<T> queue) {
        this(queue, 4);
    }

    public MessageProducerImpl(BlockingQueue<T> queue, Integer produceThreadSize) {
        this.queue = queue;
        this.produceThreadSize = produceThreadSize;
        ioThreadPool = new ThreadPoolExecutor(produceThreadSize, produceThreadSize,
                THREAD_KEEP_ALIVE_TIME, TimeUnit.SECONDS, new SynchronousQueue<>(),
                new DefaultThreadFactory("message-producer"), new ThreadPoolExecutor.AbortPolicy());
    }

    @Override
    public void produceMessage(Supplier<T> supplier) {
        for (int i = 0; i < produceThreadSize; i++) {
            ioThreadPool.execute(() -> {
                while (true) {
                    putMessage(supplier.get());
                }
            });
        }
    }

    @Override
    public void produceBatchMessage(Supplier<Collection<T>> supplier) {
        for (int i = 0; i < produceThreadSize; i++) {
            ioThreadPool.execute(() -> {
                while (true) {
                    for (T t : supplier.get()) {
                        putMessage(t);
                    }
                }
            });

        }
    }

    private void putMessage(T t) {
        try {
            queue.put(t);
        } catch (InterruptedException e) {
            log.error("put message error", e);
        }
    }
}
