package com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.field.valuegate.gates;

import com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.field.valuegate.TypedFieldValueGateBase;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;

/**
 * Just for test purpose
 */
public class EmailValueGate extends TypedFieldValueGateBase<String> {
    @Override
    public FieldType supportedFieldType() {
        return FieldType.EMAIL;
    }

    @Override
    public Class supportedFieldClass() {
        return String.class;
    }

    @Override
    protected String doStore(String val, String oldVal) {
        if (val == null)
            return val;
        return val.replaceAll(" ", "");
    }
}
