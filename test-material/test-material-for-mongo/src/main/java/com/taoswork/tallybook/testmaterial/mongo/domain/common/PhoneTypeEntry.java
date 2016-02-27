package com.taoswork.tallybook.testmaterial.mongo.domain.common;

import com.taoswork.tallybook.datadomain.base.presentation.typedcollection.entry.IPrimitiveEntry;

/**
 * Created by Gao Yuan on 2015/11/6.
 */
public class PhoneTypeEntry implements IPrimitiveEntry<PhoneType> {
    private PhoneType phoneType;

    public PhoneType getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(PhoneType phoneType) {
        this.phoneType = phoneType;
    }

    @Override
    public PhoneType getValue() {
        return phoneType;
    }

    @Override
    public void setValue(PhoneType val) {
        phoneType = val;
    }
}
