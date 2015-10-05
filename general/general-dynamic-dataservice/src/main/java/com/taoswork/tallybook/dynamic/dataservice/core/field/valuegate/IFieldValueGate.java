package com.taoswork.tallybook.dynamic.dataservice.core.field.valuegate;

import com.taoswork.tallybook.dynamic.dataservice.core.field.handler.IFieldTypedHandler;

public interface IFieldValueGate extends IFieldTypedHandler {
    Object deposit(Object val);
}
