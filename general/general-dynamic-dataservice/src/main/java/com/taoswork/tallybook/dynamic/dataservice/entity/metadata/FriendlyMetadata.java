package com.taoswork.tallybook.dynamic.dataservice.entity.metadata;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
public abstract class FriendlyMetadata {

    public String name;
    public String friendlyName;
    public int order = 9999;

    public FriendlyMetadata(){
        this("","");
    }

    public FriendlyMetadata(String name, String friendlyName){
        this.name = name;
        this.friendlyName = friendlyName;
    }

    public String getName() {
        return name;
    }

    public FriendlyMetadata setName(String name) {
        this.name = name;
        return this;
    }

    public String getFriendlyName() {
        return friendlyName;
    }

    public FriendlyMetadata setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
        return this;
    }

    public int getOrder() {
        return order;
    }

    public FriendlyMetadata setOrder(int order) {
        this.order = order;
        return this;
    }

    public void copyFrom(FriendlyMetadata metadata){
        Class sourceType = metadata.getClass();
        Class targetType = this.getClass();
        if(sourceType.isAssignableFrom(targetType)){
            this.setName(metadata.getName());
            this.setFriendlyName(metadata.getFriendlyName());
            this.setOrder(metadata.getOrder());
        }
    }
}
