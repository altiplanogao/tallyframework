package com.taoswork.tallybook.descriptor.metadata.friendly;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
public abstract class FriendlyOrderedMeta extends FriendlyMeta implements IFriendlyOrdered {

    public int order = 9999;

    public FriendlyOrderedMeta() {
        this("", "");
    }

    public FriendlyOrderedMeta(String name, String friendlyName) {
        super(name, friendlyName);
    }

    @Override
    public int getOrder() {
        return order;
    }

    public FriendlyOrderedMeta setOrder(int order) {
        this.order = order;
        return this;
    }

    public void copyFrom(FriendlyOrderedMeta metadata) {
        super.copyFrom(metadata);
        Class sourceType = metadata.getClass();
        Class targetType = this.getClass();
        if (sourceType.isAssignableFrom(targetType)) {
            this.setOrder(metadata.getOrder());
        }
    }
}
