package com.lee.owner.asyn.impl;

import com.lee.owner.asyn.MessageProcessAssistant;
import com.lee.owner.asyn.MessageProducer;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
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

    private final MessageProcessAssistant messageProcessAssistant;


    public MessageProducerImpl(BlockingQueue<T> queue) {
        this(queue, 4);
    }

    public MessageProducerImpl(BlockingQueue<T> queue, Integer produceThreadSize) {
        this.queue = queue;
        this.produceThreadSize = produceThreadSize;
        ioThreadPool = new ThreadPoolExecutor(produceThreadSize, produceThreadSize,
                THREAD_KEEP_ALIVE_TIME, TimeUnit.SECONDS, new SynchronousQueue<>(),
                new DefaultThreadFactory("message-producer"), new ThreadPoolExecutor.AbortPolicy());
        this.messageProcessAssistant = new MessageProcessAssistantImpl();
    }

    @Override
    public void produceMessage(Supplier<T> supplier) {
        if (!messageProcessAssistant.canProcess()) {
            log.info("is producing");
            return;
        }
        for (int i = 0; i < produceThreadSize; i++) {
            ioThreadPool.execute(() -> {
                while (true) {
                    T message;
                    try {
                        message = supplier.get();
                    } catch (Throwable e) {
                        log.error("produce error", e);
                        messageProcessAssistant.processWait();
                        continue;
                    }
                    if (message == null) {
                        messageProcessAssistant.processWait();
                        continue;
                    }
                    putMessage(message);
                }
            });
        }
    }

    @Override
    public void produceBatchMessage(Supplier<Collection<T>> supplier) {
        if (!messageProcessAssistant.canProcess()) {
            log.info("is producing");
            return;
        }
        for (int i = 0; i < produceThreadSize; i++) {
            ioThreadPool.execute(() -> {
                while (true) {
                    Collection<T> collection;
                    try {
                        collection = supplier.get();
                    } catch (Throwable e) {
                        log.error("produce error", e);
                        messageProcessAssistant.processWait();
                        return;
                    }
                    if (CollectionUtils.isEmpty(collection)) {
                        messageProcessAssistant.processWait();
                        continue;
                    }
                    for (T t : collection) {
                        putMessage(t);
                    }
                }
            });
        }
    }

    private void putMessage(T t) {
        try {
            if (t == null) {
                return;
            }
            queue.put(t);
            messageProcessAssistant.initProcessCount();
        } catch (InterruptedException e) {
            log.error("put message error", e);
        }
    }
}
