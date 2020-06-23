package com.lee.owner.asyn;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * @author joseph.li
 * @date 2020/6/23 2:53 下午
 */
public interface MessageConsumer<T> {

    void consumerMessage(Consumer<T> consumer);

    void consumerBatchMessage(Consumer<Collection<T>> consumer, int size);
}
