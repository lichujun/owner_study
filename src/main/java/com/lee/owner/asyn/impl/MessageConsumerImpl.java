package com.lee.owner.asyn.impl;

import com.google.common.collect.Lists;
import com.lee.owner.asyn.MessageConsumer;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;

/**
 * @author joseph.li
 * @date 2020/6/23 2:54 下午
 */
@Slf4j
public class MessageConsumerImpl<T> implements MessageConsumer<T> {

    private static final int THREAD_KEEP_ALIVE_TIME = 60;

    private final BlockingQueue<T> queue;

    private final int consumerThreadSize;

    private final ExecutorService workThreadPool;

    public MessageConsumerImpl(BlockingQueue<T> queue) {
        this(queue, 4);
    }

    public MessageConsumerImpl(BlockingQueue<T> queue, Integer consumerThreadSize) {
        this.queue = queue;
        this.consumerThreadSize = consumerThreadSize;
        workThreadPool = new ThreadPoolExecutor(consumerThreadSize, consumerThreadSize,
                THREAD_KEEP_ALIVE_TIME, TimeUnit.SECONDS, new SynchronousQueue<>(),
                new DefaultThreadFactory("message-consumer"), new ThreadPoolExecutor.AbortPolicy());
    }

    @Override
    public void consumerMessage(Consumer<T> consumer) {
        for (int i = 0; i < consumerThreadSize; i++) {
            workThreadPool.execute(() -> {
                while (true){
                    consumer.accept(getMessage());
                }
            });
        }
    }

    @Override
    public void consumerBatchMessage(Consumer<Collection<T>> consumer, int size) {
        if (size <= 0) {
            throw new UnsupportedOperationException("size can not be below 0");
        }
        for (int i = 0; i < consumerThreadSize; i++) {
            workThreadPool.execute(() -> {
                while (true) {
                    List<T> list = Lists.newArrayList();
                    for (int j = 0; j < size; j++) {
                        list.add(getMessage());
                    }
                    consumer.accept(list);
                }
            });
        }
    }

    private T getMessage() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            log.error("take message error", e);
            return null;
        }
    }

}
