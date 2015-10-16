package com.taoswork.tallybook.dynamic.datameta.metadata.friendly;

import java.io.Serializable;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
public abstract class FriendlyMetadata implements IFriendly, Cloneable, Serializable {

    public String name;
    public String friendlyName;

    public FriendlyMetadata() {
        this("", "");
    }

    public FriendlyMetadata(String name, String friendlyName) {
        this.name = name;
        this.friendlyName = friendlyName;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public FriendlyMetadata setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String getFriendlyName() {
        return friendlyName;
    }

    @Override
    public FriendlyMetadata setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
        return this;
    }

    public void copyFrom(FriendlyMetadata metadata) {
        Class sourceType = metadata.getClass();
        Class targetType = this.getClass();
        if (sourceType.isAssignableFrom(targetType)) {
            this.setName(metadata.getName());
            this.setFriendlyName(metadata.getFriendlyName());
        }
    }
}
