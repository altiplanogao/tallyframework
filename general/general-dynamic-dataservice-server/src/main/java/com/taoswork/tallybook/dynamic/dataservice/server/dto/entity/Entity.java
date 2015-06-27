package com.taoswork.tallybook.dynamic.dataservice.server.dto.entity;

import com.taoswork.tallybook.general.extension.collections.StringChain;
import com.taoswork.tallybook.general.extension.utils.StringUtility;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/6/1.
 */
public class Entity {
    private String type;
    private String id;
    private String path;
    private final Map<String, Property> properties = new HashMap<String, Property>();

    public void putProperty(Property property){
        properties.put(property.getName(), property);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        StringChain sc = new StringChain();
        sc.setFixes(StringUtility.lastPiece(getType(), ".") + " [", "]");
        sc.setSeparator(",");

        int counter = 0;
        for(Property prop : properties.values()){
            counter ++;
            if(counter >= 4){
                sc.add("..., (size:" + properties.size() + ")");
                break;
            }
            sc.add(prop);
        }
        return sc.toString()                                                                                                                                                                                                                                                                                                            ;
    }

    public Property getProperty(String propertyName){
        return properties.getOrDefault(propertyName, null);
    }
}
