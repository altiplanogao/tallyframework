package com.taoswork.tallybook.testframework.domain.business.impl;

import com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection.entry.ISimpleEntryDelegate;

/**
 * Created by Gao Yuan on 2015/11/6.
 */
public class NicknameEntryDelegate implements ISimpleEntryDelegate<String> {
    private String nickname;

    @Override
    public String getEntryValue() {
        return nickname;
    }

    @Override
    public void setEntryValue(String val) {
        nickname = val;
    }
}
