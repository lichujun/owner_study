package com.lee.owner.command;

/**
 * @author joseph.li
 * @date 2019-10-08 18:13
 */
public abstract class AbstractConverterContextCommand<S, T> extends AbstractContextCommand<S, T> implements DataConverter<S, T> {

    private static final String TRANS_FOR_DATA = "transForData";

    private static final String TRANS_BACK_DATA = "transBackData";

    abstract protected T doRealTransData(S source);

    abstract protected S doRealTransBackData(T target);

    @Override
    public final T transForData(S source) {
        T target = doRealTransData(source);
        if (target == null) {
            return null;
        }
        setLocal(TRANS_FOR_DATA, target);
        return target;
    }

    @Override
    public final S transBackData(T target) {
        S source = doRealTransBackData(target);
        if (source == null) {
            return null;
        }
        setLocal(TRANS_BACK_DATA, source);
        return source;
    }

    @Override
    @SuppressWarnings("unchecked")
    public final T getTransForData() {
        Object value = getLocal(TRANS_FOR_DATA);
        if (value == null) {
            return null;
        }
        return (T) value;
    }

    @Override
    @SuppressWarnings("unchecked")
    public final S getTransBackData() {
        Object value = getLocal(TRANS_BACK_DATA);
        if (value == null) {
            return null;
        }
        return (S) value;
    }
}
