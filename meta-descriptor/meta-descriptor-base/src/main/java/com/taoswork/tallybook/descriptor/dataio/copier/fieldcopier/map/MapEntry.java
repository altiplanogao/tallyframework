package com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier.map;

import java.util.Map;

/**
 * Created by Gao Yuan on 2016/2/23.
 */
public class MapEntry {
    public Object key;
    public Object val;

    public MapEntry(Map.Entry entry) {
        this.key = entry.getKey();
        this.val = entry.getValue();
    }

    public MapEntry(Object key, Object val) {
        this.key = key;
        this.val = val;
    }
}
