package com.taoswork.tallybook.dynamic.dataservice.entity.metadata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/5/22.
 */
public class ClassMetadata extends FriendlyMetadata {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassMetadata.class);

    public Class<?> entityClz;
    private final Map<String, TabMetadata> tabMetadataMap = new HashMap<String, TabMetadata>();
    private final Map<String, GroupMetadata> groupMetadataMap = new HashMap<String, GroupMetadata>();
    private final Map<String, FieldMetadata> fieldMetadataMap = new HashMap<String, FieldMetadata>();

    public ClassMetadata(){
        this(null);
    }
    private ClassMetadata(Class<?> entityClz){
        setEntityClz(entityClz);
    }

    public Class<?> getEntityClz() {
        return entityClz;
    }

    public void setEntityClz(Class<?> entityClz) {
        this.entityClz = entityClz;
    }

    public Map<String, TabMetadata> getRWTabMetadataMap(){
        return (tabMetadataMap);
    }

    public Map<String, GroupMetadata> getRWGroupMetadataMap(){
        return (groupMetadataMap);
    }

    public Map<String, FieldMetadata> getRWFieldMetadataMap(){
        return (fieldMetadataMap);
    }

    public Map<String, TabMetadata> getReadonlyTabMetadataMap(){
        return Collections.unmodifiableMap(tabMetadataMap);
    }

    public Map<String, GroupMetadata> getGroupMetadataMap(){
        return Collections.unmodifiableMap(groupMetadataMap);
    }

    public Map<String, FieldMetadata> getFieldMetadataMap(){
        return Collections.unmodifiableMap(fieldMetadataMap);
    }
}
