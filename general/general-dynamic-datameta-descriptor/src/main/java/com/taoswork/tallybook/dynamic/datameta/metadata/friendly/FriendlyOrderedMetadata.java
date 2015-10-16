package com.taoswork.tallybook.dynamic.datameta.metadata.friendly;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
public abstract class FriendlyOrderedMetadata extends FriendlyMetadata implements IFriendlyOrdered {

    public int order = 9999;

    public FriendlyOrderedMetadata() {
        this("", "");
    }

    public FriendlyOrderedMetadata(String name, String friendlyName) {
        super(name, friendlyName);
    }

    @Override
    public int getOrder() {
        return order;
    }

    @Override
    public FriendlyOrderedMetadata setOrder(int order) {
        this.order = order;
        return this;
    }

    public void copyFrom(FriendlyOrderedMetadata metadata) {
        super.copyFrom(metadata);
        Class sourceType = metadata.getClass();
        Class targetType = this.getClass();
        if (sourceType.isAssignableFrom(targetType)) {
            this.setOrder(metadata.getOrder());
        }
    }
}
