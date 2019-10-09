package com.lee.owner.command;

public interface DataConverter<S, T> {

    T transForData(S source);

    S transBackData(T target);

    T getTransForData();

    S getTransBackData();
}
