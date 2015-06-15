package com.taoswork.tallybook.dynamic.dataservice.entity.metadata;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
public abstract class FriendyMetadata {

    public String name;
    public String friendlyName;
    public int order = 9999;

    public FriendyMetadata(){
        this("","");
    }

    public FriendyMetadata(String name, String friendlyName){
        this.name = name;
        this.friendlyName = friendlyName;
    }

    public String getName() {
        return name;
    }

    public FriendyMetadata setName(String name) {
        this.name = name;
        return this;
    }

    public String getFriendlyName() {
        return friendlyName;
    }

    public FriendyMetadata setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
        return this;
    }

    public int getOrder() {
        return order;
    }

    public FriendyMetadata setOrder(int order) {
        this.order = order;
        return this;
    }


}
