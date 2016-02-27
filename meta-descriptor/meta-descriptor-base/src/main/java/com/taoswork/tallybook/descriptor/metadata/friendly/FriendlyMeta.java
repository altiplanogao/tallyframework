package com.taoswork.tallybook.descriptor.metadata.friendly;

import java.io.Serializable;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
public abstract class FriendlyMeta implements IFriendly, Cloneable, Serializable {

    public String name;
    public String friendlyName;

    public FriendlyMeta() {
        this("", "");
    }

    public FriendlyMeta(String name, String friendlyName) {
        this.name = name;
        this.friendlyName = friendlyName;
    }

    @Override
    public String getName() {
        return name;
    }

    public FriendlyMeta setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String getFriendlyName() {
        return friendlyName;
    }

    public FriendlyMeta setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
        return this;
    }

    public void copyFrom(FriendlyMeta metadata) {
        Class sourceType = metadata.getClass();
        Class targetType = this.getClass();
        if (sourceType.isAssignableFrom(targetType)) {
            this.setName(metadata.getName());
            this.setFriendlyName(metadata.getFriendlyName());
        }
    }
}
