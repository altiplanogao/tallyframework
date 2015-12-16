package com.taoswork.tallybook.dynamic.datameta.metadata.classmetadata;

import com.taoswork.tallybook.dynamic.datameta.metadata.GroupMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.IClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.IFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.TabMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.utils.NativeClassHelper;
import com.taoswork.tallybook.general.extension.utils.CloneUtility;
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
public final class ImmutableClassMetadata implements IClassMetadata, Serializable {

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
    private final Map<String, TabMetadata> tabMetadataMap;
    private final Map<String, GroupMetadata> groupMetadataMap;
    private final Map<String, IFieldMetadata> fieldMetadataMap;
    private final Collection<String> collectionFields;
    private final Collection<String> nonCollectionFields;

    //Referencing
    private final Map<String, IClassMetadata> referencingClassMetadata;
    private final boolean referencingClassMetadataPublished;

    //Validator and Gate
    private final Collection<String> validators;
    private final Collection<String> valueGates;
    private final String valueCopier;

    public ImmutableClassMetadata(IClassMetadata classMetadata) {
        this.name = classMetadata.getName();
        this.friendlyName = classMetadata.getFriendlyName();

        this.entityClz = classMetadata.getEntityClz();
        this.containsSuper = classMetadata.containsSuper();
        this.containsHierarchy = classMetadata.containsHierarchy();

        this.idFieldName = classMetadata.getIdFieldName();
        this.nameFieldName = classMetadata.getNameFieldName();

        this.tabMetadataMap = CloneUtility.makeClone(classMetadata.getReadonlyTabMetadataMap());
        this.groupMetadataMap = CloneUtility.makeClone(classMetadata.getReadonlyGroupMetadataMap());
        this.fieldMetadataMap = CloneUtility.makeClone(classMetadata.getReadonlyFieldMetadataMap());
        this.collectionFields = CloneUtility.makeClone(classMetadata.getCollectionFields());
        this.nonCollectionFields = CloneUtility.makeClone(classMetadata.getNonCollectionFields());

        Map<String, IClassMetadata> referencingClassMetadataLocal = new HashMap<String, IClassMetadata>();
        for(Map.Entry<String, IClassMetadata> entry : classMetadata.getReadonlyReferencingClassMetadataMap().entrySet()){
            IClassMetadata cm = entry.getValue();
            if(!(cm instanceof ImmutableClassMetadata)){
                cm = new ImmutableClassMetadata(cm);
            }
            referencingClassMetadataLocal.put(entry.getKey(), cm);
        }
        this.referencingClassMetadata = Collections.unmodifiableMap(referencingClassMetadataLocal);
        this.referencingClassMetadataPublished = classMetadata.isReferencingClassMetadataPublished();

        this.validators = CloneUtility.makeClone(classMetadata.getReadonlyValidators());
        this.valueGates = CloneUtility.makeClone(classMetadata.getReadonlyValueGates());
        this.valueCopier = classMetadata.getValueCopier();
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
            idField = NativeClassHelper.getFieldOfName(entityClz, idFieldName, true);
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
            nameField = NativeClassHelper.getFieldOfName(entityClz, nameFieldName, true);
            nameField.setAccessible(true);
            return nameField;
        } else {
            return null;
        }
    }

    //Tab, Group, Field metadata
    @Override
    public Map<String, TabMetadata> getReadonlyTabMetadataMap() {
        return this.tabMetadataMap;
    }

    @Override
    public Map<String, GroupMetadata> getReadonlyGroupMetadataMap() {
        return this.groupMetadataMap;
    }

    @Override
    public Map<String, IFieldMetadata> getReadonlyFieldMetadataMap() {
        return this.fieldMetadataMap;
    }

    @Override
    public IFieldMetadata getFieldMetadata(String fieldName) {
        return fieldMetadataMap.get(fieldName);
    }

    @Override
    public boolean hasField(String fieldName) {
        return fieldMetadataMap.containsKey(fieldName);
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
    public boolean isReferencingClassMetadataPublished() {
        return this.referencingClassMetadataPublished;
    }

    @Override
    public Map<String, IClassMetadata> getReadonlyReferencingClassMetadataMap() {
        return this.referencingClassMetadata;
    }

    @Override
    public IClassMetadata getReferencingClassMetadata(Class entity) {
        return referencingClassMetadata.get(entity.getName());
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
    public IClassMetadata clone() {
        return SerializationUtils.clone(this);
    }
}
