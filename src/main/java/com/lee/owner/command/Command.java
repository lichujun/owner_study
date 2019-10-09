package com.lee.owner.command;

public interface Command {

    void releaseResource();

    void setLocal(String key, Object value);

    Object getLocal(String key);
}
