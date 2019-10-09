package com.lee.owner.context;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import java.util.Map;

/**
 * @author joseph.li
 * @date 2019-10-08 18:09
 */
@Slf4j
public abstract class AbstractContext implements Context {

    private final ThreadLocal<Map<String, Object>> buffer = ThreadLocal.withInitial(() -> null);

    @Override
    public final void releaseResource() {
        buffer.remove();
        log.info("remove resource...");
    }

    @Override
    public final void setLocal(String key, Object value) {
        Map<String, Object> localMap = buffer.get();
        if (localMap == null) {
            localMap = Maps.newHashMap();
        }
        localMap.put(key, value);
        buffer.set(localMap);
    }

    @Override
    public final Object getLocal(String key) {
        Map<String, Object> localMap = buffer.get();
        if (localMap == null) {
            return null;
        }
        return localMap.get(key);
    }
}
