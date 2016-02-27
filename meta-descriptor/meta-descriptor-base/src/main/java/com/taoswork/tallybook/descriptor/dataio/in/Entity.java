package com.taoswork.tallybook.descriptor.dataio.in;

import com.taoswork.tallybook.datadomain.base.entity.Persistable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/6/1.
 */
public class Entity implements Serializable{
    private Integer timezoneOffset;
    private Class<? extends Persistable> type;
    private Class<? extends Persistable> ceilingType;
    private final Map<String, String> props = new HashMap<String, String>();
    public final static String ENTITY_PROPERTY_NAME = "props";

    public Integer getTimezoneOffset() {
        return timezoneOffset;
    }

    public void setTimezoneOffset(Integer timezoneOffset) {
        this.timezoneOffset = timezoneOffset;
    }

    public Class<? extends Persistable> getType() {
        return type;
    }

    public Entity setType(Class<? extends Persistable> type) {
        this.type = type;
        return this;
    }

    public Class<? extends Persistable> getCeilingType() {
        return ceilingType;
    }

    public Entity setCeilingType(Class<? extends Persistable> ceilingType) {
        this.ceilingType = ceilingType;
        return this;
    }

    public Map<String, String> getProps() {
        return props;
    }

    public Entity setProperty(String key, String value){
        this.props.put(key, value);
        return this;
    }
}
