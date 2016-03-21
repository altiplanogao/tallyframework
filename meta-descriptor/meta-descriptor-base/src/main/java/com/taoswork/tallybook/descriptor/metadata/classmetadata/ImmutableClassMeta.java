package com.taoswork.tallybook.descriptor.metadata.classmetadata;

import com.taoswork.tallybook.descriptor.metadata.GroupMeta;
import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
import com.taoswork.tallybook.descriptor.metadata.IFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.TabMeta;
import com.taoswork.tallybook.general.extension.utils.CloneUtility;
import com.taoswork.tallybook.general.solution.reflect.ClassUtility;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/11/10.
 */
public final class ImmutableClassMeta implements IClassMeta, Serializable {

    //Main
    private final Class entityClz;
    private final boolean containsSuper;
    private final boolean containsHierarchy;

    public final String name;
    public final String friendlyName;

    private final String idFieldName;
    private final String nameFieldName;
    private transient Field idField;
    private transient Field nameField;

    //Tab, Group, Field metadata
    private final Map<String, TabMeta> tabMetaMap;
    private final Map<String, GroupMeta> groupMetaMap;
    private final Map<String, IFieldMeta> fieldMetaMap;
    private final Collection<String> collectionFields;
    private final Collection<String> nonCollectionFields;

    //Referencing
    private final Map<String, IClassMeta> referencingClassMeta;
    private final boolean referencingClassMetadataPublished;

    //Validator and Gate
    private final Collection<String> validators;
    private final Collection<String> valueGates;
    private final String valueCopier;

    public ImmutableClassMeta(IClassMeta cm) {
        this.name = cm.getName();
        this.friendlyName = cm.getFriendlyName();

        this.entityClz = cm.getEntityClz();
        this.containsSuper = cm.containsSuper();
        this.containsHierarchy = cm.containsHierarchy();

        this.idFieldName = cm.getIdFieldName();
        this.nameFieldName = cm.getNameFieldName();

        this.tabMetaMap = CloneUtility.makeClone(cm.getReadonlyTabMetaMap());
        this.groupMetaMap = CloneUtility.makeClone(cm.getReadonlyGroupMetaMap());
        this.fieldMetaMap = CloneUtility.makeClone(cm.getReadonlyFieldMetaMap());
        this.collectionFields = CloneUtility.makeClone(cm.getCollectionFields());
        this.nonCollectionFields = CloneUtility.makeClone(cm.getNonCollectionFields());

        Map<String, IClassMeta> referencingClassMetadataLocal = new HashMap<String, IClassMeta>();
        for (Map.Entry<String, IClassMeta> entry : cm.getReadonlyReferencingClassMetaMap().entrySet()) {
            IClassMeta ecm = entry.getValue();
            if (!(ecm instanceof ImmutableClassMeta)) {
                ecm = new ImmutableClassMeta(ecm);
            }
            referencingClassMetadataLocal.put(entry.getKey(), ecm);
        }
        this.referencingClassMeta = Collections.unmodifiableMap(referencingClassMetadataLocal);
        this.referencingClassMetadataPublished = cm.isReferencingClassMetaPublished();

        this.validators = CloneUtility.makeClone(cm.getReadonlyValidators());
        this.valueGates = CloneUtility.makeClone(cm.getReadonlyValueGates());
        this.valueCopier = cm.getValueCopier();
    }

    //Main
    @Override
    public Class<?> getEntityClz() {
        return entityClz;
    }

    @Override
    public boolean containsSuper() {
        return this.containsSuper;
    }

    @Override
    public boolean containsHierarchy() {
        return containsHierarchy;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getFriendlyName() {
        return friendlyName;
    }

    @Override
    public String getIdFieldName() {
        return this.idFieldName;
    }

    @Override
    public Field getIdField() {
        if (idField != null)
            return idField;
        if (StringUtils.isNotEmpty(this.idFieldName)) {
            idField = ClassUtility.getFieldOfName(entityClz, idFieldName, true);
            idField.setAccessible(true);
            return idField;
        } else {
            return null;
        }
    }

    @Override
    public String getNameFieldName() {
        return nameFieldName;
    }

    @Override
    public Field getNameField() {
        if (nameField != null)
            return nameField;
        if (StringUtils.isNotEmpty(this.nameFieldName)) {
            nameField = ClassUtility.getFieldOfName(entityClz, nameFieldName, true);
            nameField.setAccessible(true);
            return nameField;
        } else {
            return null;
        }
    }

    //Tab, Group, Field metadata
    @Override
    public Map<String, TabMeta> getReadonlyTabMetaMap() {
        return this.tabMetaMap;
    }

    @Override
    public Map<String, GroupMeta> getReadonlyGroupMetaMap() {
        return this.groupMetaMap;
    }

    @Override
    public Map<String, IFieldMeta> getReadonlyFieldMetaMap() {
        return this.fieldMetaMap;
    }

    @Override
    public IFieldMeta getFieldMeta(String fieldName) {
        return fieldMetaMap.get(fieldName);
    }

    @Override
    public boolean hasField(String fieldName) {
        return fieldMetaMap.containsKey(fieldName);
    }

    @Override
    public Collection<String> getCollectionFields() {
        return collectionFields;
    }

    @Override
    public Collection<String> getNonCollectionFields() {
        return nonCollectionFields;
    }

    //Referencing
    @Override
    public boolean isReferencingClassMetaPublished() {
        return this.referencingClassMetadataPublished;
    }

    @Override
    public Map<String, IClassMeta> getReadonlyReferencingClassMetaMap() {
        return this.referencingClassMeta;
    }

    @Override
    public IClassMeta getReferencingClassMeta(Class entity) {
        return referencingClassMeta.get(entity.getName());
    }

    //Validator and Gate
    @Override
    public Collection<String> getReadonlyValidators() {
        return validators;
    }

    @Override
    public Collection<String> getReadonlyValueGates() {
        return valueGates;
    }

    @Override
    public String getValueCopier() {
        return valueCopier;
    }

    //Clone
    @Override
    public IClassMeta clone() {
        return SerializationUtils.clone(this);
    }
}
