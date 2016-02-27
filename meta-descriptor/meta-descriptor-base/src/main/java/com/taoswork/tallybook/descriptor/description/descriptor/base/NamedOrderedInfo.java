package com.taoswork.tallybook.descriptor.description.descriptor.base;

import org.apache.commons.lang3.builder.CompareToBuilder;

import java.io.Serializable;
import java.util.*;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public interface NamedOrderedInfo extends NamedInfo {

    int getOrder();

    public static class InfoOrderedComparator implements Comparator<NamedOrderedInfo>, Serializable {
        @Override
        public int compare(NamedOrderedInfo o1, NamedOrderedInfo o2) {
            return new CompareToBuilder()
                    .append(o1.getOrder(), o2.getOrder())
                    .append(o1.getName(), o2.getName())
                    .toComparison();
        }
    }

    public static class NameSorter {

        public static List<String> makeNamesOrdered(Collection<? extends NamedOrderedInfo> infos) {
            Set<OrderedName> orderedNameSet = new TreeSet<OrderedName>(new OrderedName.OrderedComparator());
            for (NamedOrderedInfo info : infos) {
                if (info != null) {
                    orderedNameSet.add(new OrderedName(info.getName(), info.getOrder()));
                }
            }

            List<String> orderedNames = new ArrayList<String>();
            for (OrderedName orderedName : orderedNameSet) {
                orderedNames.add(orderedName.getName());
            }
            return orderedNames;
        }

        public static List<String> makeNamesOrdered(Collection<String> names, Map<String, ? extends NamedOrderedInfo> infoMap) {
            List<NamedOrderedInfo> infos = new ArrayList<NamedOrderedInfo>();
            for (String name : names) {
                NamedOrderedInfo info = infoMap.get(name);
                if (info != null) {
                    infos.add(info);
                }
            }
            return makeNamesOrdered(infos);
        }

        public static <T extends NamedOrderedInfo> List<T> makeObjectOrdered(Collection<T> objects) {
            Map<OrderedName, T> orderedNameSet = new TreeMap<OrderedName, T>(new OrderedName.OrderedComparator());
            for (T info : objects) {
                if (info != null) {
                    orderedNameSet.put(new OrderedName(info.getName(), info.getOrder()), info);
                }
            }

            List<T> orderedObjects = new ArrayList<T>();
            for (Map.Entry<OrderedName, T> entry : orderedNameSet.entrySet()) {
                orderedObjects.add(entry.getValue());
            }
            return orderedObjects;
        }

    }

}
