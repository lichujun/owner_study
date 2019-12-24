package com.lee.owner.context;

import com.google.common.collect.Maps;
import com.lee.owner.utils.GenericsUtils;
import lombok.extern.slf4j.Slf4j;
import java.util.Map;

/**
 * context抽象类
 * @author joseph.li
 * @date 2019-10-08 18:09
 */
@Slf4j
public abstract class AbstractContext<K, V> implements Context<K, V> {

    private final ThreadLocal<Map<Object, Object>> buffer = ThreadLocal.withInitial(() -> null);

    @SuppressWarnings("unchecked")
    private final Class<V> vClazz = (Class<V>) GenericsUtils.getSuperClassGenericType(getClass(), 1);

    @Override
    public final void releaseResource() {
        buffer.remove();
    }

    /**
     * 设置key->value
     * @param key key
     * @param value value
     */
    private void setLocal(Object key, Object value) {
        Map<Object, Object> localMap = buffer.get();
        if (localMap == null) {
            localMap = Maps.newHashMap();
        }
        localMap.put(key, value);
        buffer.set(localMap);
    }

    /**
     * 通过key获取value
     * @param key key
     * @return value
     */
    private Object getLocal(Object key) {
        Map<Object, Object> localMap = buffer.get();
        if (localMap == null) {
            return null;
        }
        return localMap.get(key);
    }

    /**
     * 查询数据K，所对应的V
     * @param k key
     * @return value
     */
    protected abstract V doGetData(K k);

    /**
     * 通过key对象获取数据value
     * @param key 数据存储的索引key
     * @return 存储的数据
     */
    @Override
    public V getData(K key) {
        Object data = getLocal(key);
        if (data != null) {
            return vClazz.cast(data);
        }
        V value = doGetData(key);
        if (value != null) {
            setLocal(key, value);
            return value;
        }
        return null;
    }
}
