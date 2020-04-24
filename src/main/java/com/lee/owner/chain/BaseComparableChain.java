package com.lee.owner.chain;

/**
 * @author joseph.li
 * @date 2020/4/24 12:41 下午
 */
public abstract class BaseComparableChain<T, V>
        extends BaseChain<T>
        implements Comparable<BaseComparableChain<T, V>> {

    protected abstract V getComparableValue();

    @Override
    public int compareTo(BaseComparableChain<T, V> o) {
        if (getComparableValue() instanceof Comparable) {
            @SuppressWarnings("unchecked")
            Comparable<V> comparable = (Comparable<V>) getComparableValue();
            return comparable.compareTo(o.getComparableValue());
        }
        throw new UnsupportedOperationException();
    }
}
