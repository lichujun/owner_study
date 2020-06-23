package com.lee.owner.asyn;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * @author joseph.li
 * @date 2020/6/23 2:53 下午
 */
public interface MessageProducer<T> {

    void produceMessage(Supplier<T> supplier);

    void produceBatchMessage(Supplier<Collection<T>> supplier);

}
