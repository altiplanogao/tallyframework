package com.taoswork.tallybook.descriptor.description.descriptor.base;

import org.apache.commons.lang3.builder.CompareToBuilder;

import java.util.Comparator;

/**
 * Created by Gao Yuan on 2015/6/24.
 */
public class OrderedName {

    public String name;
    public int order;

    public OrderedName(String name, int order) {
        this.name = name;
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public OrderedName setName(String name) {
        this.name = name;
        return this;
    }

    public int getOrder() {
        return order;
    }

    public OrderedName setOrder(int order) {
        this.order = order;
        return this;
    }

    public static class OrderedComparator implements Comparator<OrderedName> {
        @Override
        public int compare(OrderedName o1, OrderedName o2) {
            return new CompareToBuilder()
                    .append(o1.getOrder(), o2.getOrder())
                    .append(o1.getName(), o2.getName())
                    .toComparison();
        }

    }
}
