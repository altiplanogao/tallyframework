package com.taoswork.tallybook.descriptor.description.descriptor.base.impl;

import com.taoswork.tallybook.descriptor.description.descriptor.base.NamedInfo;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public class NamedInfoImpl implements NamedInfoRW {
    public String name;
    public String friendlyName;

    public NamedInfoImpl() {
        this("", "");
    }

    public NamedInfoImpl(String name, String friendlyName) {
        this.name = name;
        this.friendlyName = friendlyName;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public NamedInfoImpl setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String getFriendlyName() {
        return friendlyName;
    }

    @Override
    public NamedInfoImpl setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
        return this;
    }

    @Override
    public void copyNamedInfo(NamedInfo source) {
        this.name = source.getName();
        this.friendlyName = source.getFriendlyName();
    }
}
