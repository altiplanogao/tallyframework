package com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.field.handler;

import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;

public interface ITypedFieldHandler {

    FieldType supportedFieldType();

    Class supportedFieldClass();
}