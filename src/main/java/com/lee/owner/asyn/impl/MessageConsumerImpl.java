package com.lee.owner.asyn.impl;

import com.google.common.collect.Lists;
import com.lee.owner.asyn.MessageConsumer;
import com.lee.owner.asyn.MessageProcessAssistant;
import com.lee.owner.utils.JackSonUtils;
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

    public MessageConsumerImpl(BlockingQueue<T> queue, String threadPoolName) {
        this(queue, 4, threadPoolName);
    }

    public MessageConsumerImpl(BlockingQueue<T> queue, Integer consumerThreadSize, String threadPoolName) {
        this.queue = queue;
        this.consumerThreadSize = consumerThreadSize;
        messageProcessAssistant = new MessageProcessAssistantImpl();
        workThreadPool = new ThreadPoolExecutor(consumerThreadSize, consumerThreadSize,
                THREAD_KEEP_ALIVE_TIME, TimeUnit.SECONDS, new SynchronousQueue<>(),
                new DefaultThreadFactory(threadPoolName), new ThreadPoolExecutor.AbortPolicy());
    }

    @Override
    public void consumerMessage(Consumer<T> consumer) {
        if (!messageProcessAssistant.canProcess()) {
            log.info("is consuming");
            return;
        }
        for (int i = 0; i < consumerThreadSize; i++) {
            workThreadPool.execute(() -> {
                while (true) {
                    try {
                        doConsumeMessage(consumer);
                    } catch (Throwable e) {
                        log.error("consume occur unknown error", e);
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
                    try {
                        doConsumerBatchMessage(consumer, size);
                    } catch (Throwable e) {
                        log.error("consume occur unknown error", e);
                    }
                }
            });
        }
    }

    private void doConsumeMessage(Consumer<T> consumer) {
        T t = takeMessage();
        try {
            if (t != null) {
                consumer.accept(t);
                log.info("consume success, data: {}", JackSonUtils.writeValueAsString(t));
            }
        } catch (Throwable e) {
            log.error("consume error, data: {}", JackSonUtils.writeValueAsString(t), e);
        }
    }

    private void doConsumerBatchMessage(Consumer<Collection<T>> consumer, int size) {
        List<T> list = null;
        T t;
        for (int j = 0; j < size; j++) {
            t = pollMessage();
            if (t == null) {
                break;
            }
            if (list == null) {
                list = Lists.newArrayList();
            }
            list.add(t);
        }
        if (CollectionUtils.isNotEmpty(list)) {
            try {
                consumer.accept(list);
                log.info("consume success, data: {}", JackSonUtils.writeValueAsString(list));
            } catch (Throwable e) {
                log.error("consume error, data: {}", JackSonUtils.writeValueAsString(list), e);
            }
        } else {
            log.info("poll message empty");
        }
    }

    private T pollMessage() {
        try {
            T t = queue.poll(100, TimeUnit.MILLISECONDS);
            if (t != null) {
                log.info("consume message:{}", JackSonUtils.writeValueAsString(t));
            }
            return t;
        } catch (InterruptedException e) {
            log.error("poll message error", e);
            return null;
        }
    }

    private T takeMessage() {
        try {
            T t = queue.take();
            log.info("consume message:{}", JackSonUtils.writeValueAsString(t));
            return t;
        } catch (InterruptedException e) {
            log.error("take message error", e);
            return null;
        }
    }

}
