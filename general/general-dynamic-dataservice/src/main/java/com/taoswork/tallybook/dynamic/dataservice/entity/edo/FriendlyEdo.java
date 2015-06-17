package com.taoswork.tallybook.dynamic.dataservice.entity.edo;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by Gao Yuan on 2015/5/26.
 */
public abstract class FriendlyEdo implements Serializable {

    public static class OrderedComparator implements Comparator<FriendlyEdo> , Serializable{
        @Override
        public int compare(FriendlyEdo o1, FriendlyEdo o2) {
            return new CompareToBuilder()
                    .append(o1.getOrder(), o2.getOrder())
                    .append(o1.getName(), o2.getName())
                    .toComparison();
        }

    }

    public String name;
    public String friendlyName;
    public int order;

    public FriendlyEdo(){
        this("","");
    }

    public FriendlyEdo(String name, String friendlyName){
        this.name = name;
        this.friendlyName = friendlyName;
    }

    public String getName() {
        return name;
    }

    public FriendlyEdo setName(String name) {
        this.name = name;
        return this;
    }

    public String getFriendlyName() {
        return friendlyName;
    }

    public FriendlyEdo setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
        return this;
    }

    public int getOrder() {
        return order;
    }

    public FriendlyEdo setOrder(int order) {
        this.order = order;
        return this;
    }

    protected String toStringChildInfo(){
        return "";
    }

    @Override
    public String toString() {
        String childrenInfo = toStringChildInfo();
        if(!StringUtils.isEmpty(childrenInfo)){
            childrenInfo = childrenInfo + ", ";
        }
        return this.getClass().getSimpleName() + "{" +
                childrenInfo +
                "order=" + order  +
                ", name='" + name + '\'' +
                '}';
    }
}
