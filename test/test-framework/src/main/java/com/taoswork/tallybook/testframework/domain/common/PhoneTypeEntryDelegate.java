package com.taoswork.tallybook.testframework.domain.common;

import com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection.entry.ISimpleEntryDelegate;

/**
 * Created by Gao Yuan on 2015/11/6.
 */
public class PhoneTypeEntryDelegate implements ISimpleEntryDelegate<PhoneType> {
    private PhoneType phoneType;

    @Override
    public PhoneType getEntryValue() {
        return phoneType;
    }

    @Override
    public void setEntryValue(PhoneType val) {
        phoneType = val;
    }
}
