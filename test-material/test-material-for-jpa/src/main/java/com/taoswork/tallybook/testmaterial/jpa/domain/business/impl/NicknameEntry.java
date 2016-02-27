package com.taoswork.tallybook.testmaterial.jpa.domain.business.impl;

import com.taoswork.tallybook.datadomain.base.presentation.typedcollection.entry.IPrimitiveEntry;

/**
 * Created by Gao Yuan on 2015/11/6.
 */
public class NicknameEntry implements IPrimitiveEntry<String> {
    private String nickname;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String getValue() {
        return nickname;
    }

    @Override
    public void setValue(String val) {
        nickname = val;
    }
}
