package com.lee.owner.asyn.impl;

import com.google.common.collect.Lists;
import com.lee.owner.asyn.MessageConsumer;
import com.lee.owner.asyn.MessageProcessAssistant;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
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

    private final MessageProcessAssistant messageProcessAssistant;

    public MessageConsumerImpl(BlockingQueue<T> queue) {
        this(queue, 4);
    }

    public MessageConsumerImpl(BlockingQueue<T> queue, Integer consumerThreadSize) {
        this.queue = queue;
        this.consumerThreadSize = consumerThreadSize;
        messageProcessAssistant = new MessageProcessAssistantImpl();
        workThreadPool = new ThreadPoolExecutor(consumerThreadSize, consumerThreadSize,
                THREAD_KEEP_ALIVE_TIME, TimeUnit.SECONDS, new SynchronousQueue<>(),
                new DefaultThreadFactory("message-consumer"), new ThreadPoolExecutor.AbortPolicy());
    }

    @Override
    public void consumerMessage(Consumer<T> consumer) {
        if (!messageProcessAssistant.canProcess()) {
            log.info("is consuming");
            return;
        }
        for (int i = 0; i < consumerThreadSize; i++) {
            workThreadPool.execute(() -> {
                while (true){
                    try {
                        T t = takeMessage();
                        if (t != null) {
                            consumer.accept(t);
                        }
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
        if (!messageProcessAssistant.canProcess()) {
            log.info("is consuming");
            return;
        }
        for (int i = 0; i < consumerThreadSize; i++) {
            workThreadPool.execute(() -> {
                while (true) {
                    List<T> list = Lists.newArrayList();
                    T t;
                    for (int j = 0; j < size; j++) {
                        t = pollMessage();
                        if (t == null) {
                            break;
                        }
                        list.add(t);
                    }
                    if (CollectionUtils.isNotEmpty(list)) {
                        try {
                            consumer.accept(list);
                        } catch (Throwable e) {
                            log.error("consume error", e);
                        }
                    }
                }
            });
        }
    }

    private T pollMessage() {
        try {
            return queue.poll(100, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            log.error("poll message error", e);
            return null;
        }
    }

    private T takeMessage() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            log.error("take message error", e);
            return null;
        }
    }

}
