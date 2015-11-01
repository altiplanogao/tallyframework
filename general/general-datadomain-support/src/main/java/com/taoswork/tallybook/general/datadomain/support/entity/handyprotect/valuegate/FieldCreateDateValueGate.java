package com.taoswork.tallybook.general.datadomain.support.entity.handyprotect.valuegate;

import com.taoswork.tallybook.general.datadomain.support.entity.valuegate.FieldValueGateBase;

import java.util.Date;

/**
 * Created by Gao Yuan on 2015/11/1.
 */
public class FieldCreateDateValueGate extends FieldValueGateBase<Date> {
    @Override
    protected Date doStore(Date val, Date oldVal) {
        if(oldVal != null)
            return oldVal;
        return new Date();
    }
}
