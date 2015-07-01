package com.taoswork.tallybook.dynamic.datameta.description.descriptor.base;

import org.apache.commons.lang3.builder.CompareToBuilder;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public interface NamedInfo {

    String getName();

    String getFriendlyName();

    int getOrder();

    public static class InfoOrderedComparator implements Comparator<NamedInfo>, Serializable {
        @Override
        public int compare(NamedInfo o1, NamedInfo o2) {
            return new CompareToBuilder()
                    .append(o1.getOrder(), o2.getOrder())
                    .append(o1.getName(), o2.getName())
                    .toComparison();
        }

    }

}
