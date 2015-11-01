package com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.field.valuegate;

import com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.field.handler.TypedFieldHandlerBase;

public abstract class TypedFieldValueGateBase<T>
    extends TypedFieldHandlerBase<T>
    implements TypedFieldValueGate {

    @Override
    public Object store(Object val, Object oldVal) {
        return this.doStore((T) val, (T)oldVal);
    }

    protected abstract T doStore(T val, T oldVal);
}
