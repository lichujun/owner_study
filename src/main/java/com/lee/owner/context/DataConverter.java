package com.lee.owner.context;

public interface DataConverter<S, T> {

    T transForData(S source);

    S transBackData(T target);

    T getTransForData();

    S getTransBackData();
}
