package com.taoswork.tallybook.general.extension.collections;

import com.taoswork.tallybook.general.extension.utils.TPredicate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/4/28.
 */
public class MapUtility {
    public static <K, V> void putIfAbsent(final Map<K, V> from, final Map<K, V> to){
        for(Map.Entry<K,V> entry : from.entrySet()){
            to.putIfAbsent(entry.getKey(), entry.getValue());
        }
    }
}
