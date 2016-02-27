package com.taoswork.tallybook.dataservice.core.entityprotect.field.handler;

import com.taoswork.tallybook.datadomain.base.presentation.FieldType;

public interface ITypedFieldHandler {

    FieldType supportedFieldType();

    Class supportedFieldClass();
}
