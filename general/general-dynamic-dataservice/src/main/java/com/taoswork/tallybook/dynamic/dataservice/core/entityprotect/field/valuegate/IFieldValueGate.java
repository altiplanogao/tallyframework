package com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.field.valuegate;

import com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.field.handler.IFieldTypedHandler;

public interface IFieldValueGate extends IFieldTypedHandler {
    Object deposit(Object val);
}
