package com.taoswork.tallybook.general.extension.collections;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/4/21.
 */
public class MapBuilder<TK, TV> {
    protected Map<TK, TV> innerMap;
    public MapBuilder(){
        innerMap = new HashMap<TK, TV>();
    }

    public static <TTK, TTV> MapBuilder instance(){
        MapBuilder<TTK, TTV> obj = new MapBuilder<TTK, TTV>();
        return obj;
    }

    public static <TTK, TTV> MapBuilder instance(TTK key, TTV value){
        MapBuilder<TTK, TTV> obj = new MapBuilder<TTK, TTV>();
        obj.put(key, value);
        return obj;
    }

    public MapBuilder put(TK key, TV value){
        innerMap.put(key, value);
        return this;
    }

    public Map<TK, TV> result(){
        return innerMap;
    }
}
