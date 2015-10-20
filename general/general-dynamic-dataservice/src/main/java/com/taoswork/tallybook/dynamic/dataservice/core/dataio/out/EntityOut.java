package com.taoswork.tallybook.dynamic.dataservice.core.dataio.out;

import com.taoswork.tallybook.dynamic.datameta.metadata.property.Property;

import java.util.HashMap;
import java.util.Map;

public class EntityOut {
    private final Map<String, Property> entity = new HashMap();

    public void setProperty(String propertyName, Property property){
        entity.put(propertyName, property);
    }

    public Property getProperty(String propertyName){
        return entity.get(propertyName);
    }
}
