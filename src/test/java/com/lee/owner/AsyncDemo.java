package com.lee.owner;

import com.lee.owner.asyn.MessageConsumer;
import com.lee.owner.asyn.MessageProducer;
import com.lee.owner.asyn.impl.MessageConsumerImpl;
import com.lee.owner.asyn.impl.MessageProducerImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Date 2019-08-28 15:26
 * @Author joseph.li
 */
@Slf4j
public class AsyncDemo {

    @Test
    public void test3() throws Exception {
        BlockingQueue<Integer> oneQueue = new LinkedBlockingQueue<>();
        BlockingQueue<Integer> twoQueue = new LinkedBlockingQueue<>();
        BlockingQueue<Integer> threeQueue = new LinkedBlockingQueue<>();


        MessageProducer<Integer> oneProducer = new MessageProducerImpl<>(oneQueue);
        MessageProducer<Integer> twoProducer = new MessageProducerImpl<>(twoQueue);
        MessageProducer<Integer> threeProducer = new MessageProducerImpl<>(threeQueue);

        MessageConsumer<Integer> oneConsumer = new MessageConsumerImpl<>(oneQueue);
        MessageConsumer<Integer> twoConsumer = new MessageConsumerImpl<>(twoQueue);
        MessageConsumer<Integer> threeConsumer = new MessageConsumerImpl<>(threeQueue);

        AtomicInteger one = new AtomicInteger(1);
        AtomicInteger two = new AtomicInteger(1);
        AtomicInteger three = new AtomicInteger(1);


        oneConsumer.consumerMessage(it -> {
            log.info("one consumer:{}", it);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        twoConsumer.consumerMessage(it -> {
            log.info("two consumer:{}", it);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        threeConsumer.consumerMessage(it -> {
            log.info("three consumer:{}", it);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        oneProducer.produceMessage(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return one.getAndIncrement();
        });
        /*twoProducer.produceMessage(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return two.getAndIncrement();
        });
        threeProducer.produceMessage(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return three.getAndIncrement();
        });*/

        Thread.sleep(10000);
    }
}
