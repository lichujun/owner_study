package com.lee.owner.context;

public interface Context {

    void releaseResource();

    void setLocal(String key, Object value);

    Object getLocal(String key);
}
