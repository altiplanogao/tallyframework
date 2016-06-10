package com.taoswork.tallybook.datadomain.base.entity.handyprotect.valuegate;

import com.taoswork.tallybook.datadomain.base.entity.valuegate.BaseFieldGate;

import java.util.Date;

/**
 * Created by Gao Yuan on 2015/11/1.
 */
public class CreateDateGate extends BaseFieldGate<Date> {
    @Override
    protected Date doStore(Date val, Date oldVal) {
        if (oldVal != null)
            return oldVal;
        return new Date();
    }
}
