package com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.field.valuegate;

import com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.field.handler.FieldTypedHandlerBase;

public abstract class FieldValueGateBase<T>
    extends FieldTypedHandlerBase<T>
    implements IFieldValueGate {

    @Override
    public Object deposit(Object val) {
        return this.doDeposit((T)val);
    }

    protected abstract T doDeposit(T val);
}
