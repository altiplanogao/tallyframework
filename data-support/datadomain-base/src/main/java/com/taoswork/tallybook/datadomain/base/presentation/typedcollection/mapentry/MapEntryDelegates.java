package com.taoswork.tallybook.datadomain.base.presentation.typedcollection.mapentry;

/**
 * Created by Gao Yuan on 2016/2/21.
 */
public abstract class MapEntryDelegates {
    public static Class<? extends IMapEntryDelegate> getDefaultPrimitiveEntry(Class keyType, Class valType){
        if(String.class.equals(keyType) && String.class.equals(valType)){
            return StringStringEntry.class;
        }
        return null;
    }

}
