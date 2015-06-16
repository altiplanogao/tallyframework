package com.taoswork.tallybook.dynamic.dataservice.entity.metadata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/5/22.
 */
public class ClassMetadata extends FriendlyMetadata implements Serializable {
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

    public Map<String, GroupMetadata> getReadonlyGroupMetadataMap(){
        return Collections.unmodifiableMap(groupMetadataMap);
    }

    public Map<String, FieldMetadata> getReadonlyFieldMetadataMap(){
        return Collections.unmodifiableMap(fieldMetadataMap);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClassMetadata)) return false;

        ClassMetadata that = (ClassMetadata) o;

        if (entityClz != null ? !entityClz.equals(that.entityClz) : that.entityClz != null) return false;
        if (tabMetadataMap != null ? !tabMetadataMap.equals(that.tabMetadataMap) : that.tabMetadataMap != null)
            return false;
        if (groupMetadataMap != null ? !groupMetadataMap.equals(that.groupMetadataMap) : that.groupMetadataMap != null)
            return false;
        return !(fieldMetadataMap != null ? !fieldMetadataMap.equals(that.fieldMetadataMap) : that.fieldMetadataMap != null);

    }

    @Override
    public int hashCode() {
        int result = entityClz != null ? entityClz.hashCode() : 0;
        result = 31 * result + (tabMetadataMap != null ? tabMetadataMap.size() : 0);
        result = 31 * result + (groupMetadataMap != null ? groupMetadataMap.size() : 0);
        result = 31 * result + (fieldMetadataMap != null ? fieldMetadataMap.size() : 0);
        return result;
    }
}
