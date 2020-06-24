package com.lee.owner.asyn.impl;

import com.lee.owner.asyn.MessageProcessAssistant;
import com.lee.owner.asyn.MessageProducer;
import com.lee.owner.utils.JackSonUtils;
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

    public MessageProducerImpl(BlockingQueue<T> queue, String threadPoolName) {
        this(queue, 4, threadPoolName);
    }

    public MessageProducerImpl(BlockingQueue<T> queue, Integer produceThreadSize, String threadPoolName) {
        this.queue = queue;
        this.produceThreadSize = produceThreadSize;
        ioThreadPool = new ThreadPoolExecutor(produceThreadSize, produceThreadSize,
                THREAD_KEEP_ALIVE_TIME, TimeUnit.SECONDS, new SynchronousQueue<>(),
                new DefaultThreadFactory(threadPoolName), new ThreadPoolExecutor.AbortPolicy());
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
                    try {
                        doProduceMessage(supplier);
                    } catch (Throwable e) {
                        log.error("produce occur unknown error", e);
                    }
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
                    try {
                        doProduceBatchMessage(supplier);
                    } catch (Throwable e) {
                        log.error("produce occur unknown error", e);
                    }
                }
            });
        }
    }

    private void doProduceMessage(Supplier<T> supplier) {
        T message;
        try {
            message = supplier.get();
        } catch (Throwable e) {
            log.error("produce error", e);
            messageProcessAssistant.processWait();
            return;
        }
        if (message == null) {
            messageProcessAssistant.processWait();
            return;
        }
        putMessage(message);
    }

    private void doProduceBatchMessage(Supplier<Collection<T>> supplier) {
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
            return;
        }
        for (T t : collection) {
            putMessage(t);
        }
    }


    private void putMessage(T t) {
        try {
            if (t == null) {
                return;
            }
            queue.put(t);
            log.info("produce message:{}", JackSonUtils.writeValueAsString(t));
            messageProcessAssistant.initProcessCount();
        } catch (InterruptedException e) {
            log.error("put message error", e);
        }
    }
}
