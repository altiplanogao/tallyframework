package com.taoswork.tallybook.dataservice.core.entityprotect.field.valuegate.gates;

import com.taoswork.tallybook.datadomain.base.presentation.FieldType;
import com.taoswork.tallybook.dataservice.core.entityprotect.field.valuegate.BaseTypedFieldGate;

/**
 * Just for test purpose
 */
public class EmailGate extends BaseTypedFieldGate<String> {
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
