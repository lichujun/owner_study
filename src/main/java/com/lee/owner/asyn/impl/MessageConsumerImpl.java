package com.lee.owner.asyn.impl;

import com.google.common.collect.Lists;
import com.lee.owner.asyn.MessageConsumer;
import com.lee.owner.asyn.MessageProcessor;
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

    private final MessageProcessor messageProcessor;

    public MessageConsumerImpl(BlockingQueue<T> queue) {
        this(queue, 4);
    }

    public MessageConsumerImpl(BlockingQueue<T> queue, Integer consumerThreadSize) {
        this.queue = queue;
        this.consumerThreadSize = consumerThreadSize;
        messageProcessor = new MessageProcessorImpl();
        workThreadPool = new ThreadPoolExecutor(consumerThreadSize, consumerThreadSize,
                THREAD_KEEP_ALIVE_TIME, TimeUnit.SECONDS, new SynchronousQueue<>(),
                new DefaultThreadFactory("message-consumer"), new ThreadPoolExecutor.AbortPolicy());
    }

    @Override
    public void consumerMessage(Consumer<T> consumer) {
        if (!messageProcessor.canProcess()) {
            log.info("is consuming");
            return;
        }
        for (int i = 0; i < consumerThreadSize; i++) {
            workThreadPool.execute(() -> {
                while (true){
                    try {
                        consumer.accept(getMessage());
                    } catch (Throwable e) {
                        log.error("consume error", e);
                    }
                }
            });
        }
    }

    @Override
    public void consumerBatchMessage(Consumer<Collection<T>> consumer, int size) {
        if (size <= 0) {
            throw new UnsupportedOperationException("size can not be below 0");
        }
        if (!messageProcessor.canProcess()) {
            log.info("is consuming");
            return;
        }
        for (int i = 0; i < consumerThreadSize; i++) {
            workThreadPool.execute(() -> {
                while (true) {
                    List<T> list = Lists.newArrayList();
                    for (int j = 0; j < size; j++) {
                        list.add(getMessage());
                    }
                    try {
                        consumer.accept(list);
                    } catch (Throwable e) {
                        log.error("consume error", e);
                    }
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
