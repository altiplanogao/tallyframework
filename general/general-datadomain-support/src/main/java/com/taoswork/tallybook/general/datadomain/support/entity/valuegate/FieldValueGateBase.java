package com.taoswork.tallybook.general.datadomain.support.entity.valuegate;

/**
 * Created by Gao Yuan on 2015/11/1.
 */
public abstract class FieldValueGateBase<T> implements IFieldValueGate {
    @Override
    public final Object store(Object val, Object oldVal) {
        return this.doStore((T) val, (T) oldVal);
    }

    protected abstract T doStore(T val, T oldVal);
}
