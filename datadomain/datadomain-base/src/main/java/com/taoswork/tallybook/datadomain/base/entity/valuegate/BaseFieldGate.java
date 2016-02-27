package com.taoswork.tallybook.datadomain.base.entity.valuegate;

/**
 * Created by Gao Yuan on 2015/11/1.
 */
public abstract class BaseFieldGate<T> implements IFieldGate {
    @Override
    public final Object store(Object val, Object oldVal) {
        return this.doStore((T) val, (T) oldVal);
    }

    protected abstract T doStore(T val, T oldVal);
}
