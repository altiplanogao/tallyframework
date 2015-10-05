package com.taoswork.tallybook.dynamic.dataservice.core.field.valuegate.gates;

import com.taoswork.tallybook.dynamic.dataservice.core.field.valuegate.FieldValueGateBase;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HtmlValueGate extends FieldValueGateBase<String> {
    private final static Logger LOGGER = LoggerFactory.getLogger(HtmlValueGate.class);

    @Override
    public FieldType supportedFieldType() {
        return FieldType.HTML;
    }

    @Override
    public Class supportedFieldClass() {
        return String.class;
    }

    @Override
    protected String doDeposit(String val) {
        LOGGER.error("Xss attack should be handled here");
        return val;
    }
}
