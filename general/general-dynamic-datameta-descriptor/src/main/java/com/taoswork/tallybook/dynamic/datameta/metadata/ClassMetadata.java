package com.taoswork.tallybook.dynamic.datameta.metadata;

import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typed.ForeignEntityFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typedcollection.CollectionFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typedcollection.MapFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.friendly.FriendlyMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.utils.NativeClassHelper;
import com.taoswork.tallybook.general.datadomain.support.entity.validation.IEntityValidator;
import com.taoswork.tallybook.general.datadomain.support.entity.valuegate.IEntityValueGate;
import com.taoswork.tallybook.general.extension.collections.MapUtility;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Represents a class with its self containing metadata, super information not included by default.
 */
public class ClassMetadata extends FriendlyMetadata implements Cloneable, Serializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassMetadata.class);
    private final Map<String, TabMetadata> tabMetadataMap = new HashMap<String, TabMetadata>();
    private final Map<String, GroupMetadata> groupMetadataMap = new HashMap<String, GroupMetadata>();
    private final Map<String, IFieldMetadata> fieldMetadataMap = new HashMap<String, IFieldMetadata>();
    private final Map<String, ClassMetadata> referencedEntityMetadata = new HashMap<String, ClassMetadata>();
    private final Set<String> nonCollectionFields = new HashSet<String>();
    private final Set<String> validators = new HashSet();
    private final Set<String> valueGates = new HashSet();
    public Class<?> entityClz;
    public boolean containsSuper = false;
    private String idFieldName;
    private String nameFieldName;
    private transient Field idField;
    private transient Field nameField;
    private boolean referencedEntityMetadataPublished = false;

    public ClassMetadata() {
        this(null);
    }

    private ClassMetadata(Class<?> entityClz) {
        setEntityClz(entityClz);
    }

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

    public void setIdField(Field idField) {
        if (idField != null) {
            this.idField = idField;
            this.idField.setAccessible(true);
            this.idFieldName = idField.getName();
        }
    }

    public void setIdFieldIfNone(Field idField) {
        if (null == this.idField) {
            this.setIdField(idField);
        }
    }

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

    public void setNameField(Field nameField) {
        if (nameField != null) {
            this.nameField = nameField;
            this.nameField.setAccessible(true);
            this.nameFieldName = nameField.getName();
        }
    }

    public void setNameFieldIfNone(Field nameField) {
        if (null == this.nameField) {
            this.setNameField(nameField);
        }
    }

    public Class<?> getEntityClz() {
        return entityClz;
    }

    public void setEntityClz(Class<?> entityClz) {
        this.entityClz = entityClz;
    }

    public boolean isContainsSuper() {
        return containsSuper;
    }

    public void setContainsSuper(boolean containsSuper) {
        this.containsSuper = containsSuper;
    }

    public Map<String, TabMetadata> getRWTabMetadataMap() {
        return (tabMetadataMap);
    }

    public Map<String, GroupMetadata> getRWGroupMetadataMap() {
        return (groupMetadataMap);
    }

    public Map<String, IFieldMetadata> getRWFieldMetadataMap() {
        return (fieldMetadataMap);
    }

    public Map<String, TabMetadata> getReadonlyTabMetadataMap() {
        return Collections.unmodifiableMap(tabMetadataMap);
    }

    public Map<String, GroupMetadata> getReadonlyGroupMetadataMap() {
        return Collections.unmodifiableMap(groupMetadataMap);
    }

    public Map<String, IFieldMetadata> getReadonlyFieldMetadataMap() {
        return Collections.unmodifiableMap(fieldMetadataMap);
    }

    public IFieldMetadata getFieldMetadata(String fieldName) {
        return fieldMetadataMap.getOrDefault(fieldName, null);
    }

    public void addValidator(Class<? extends IEntityValidator> validator) {
        if (validator != null)
            validators.add(validator.getName());
    }

    public void addValueGate(Class<? extends IEntityValueGate> valueGate) {
        if (valueGate != null)
            valueGates.add(valueGate.getName());
    }

    public Collection<String> getValidators() {
        return Collections.unmodifiableCollection(this.validators);
    }

    public Collection<String> getValueGates() {
        return Collections.unmodifiableCollection(this.valueGates);
    }

    public void absorbSuper(ClassMetadata superMeta) {
        if (superMeta.getEntityClz().isAssignableFrom(this.getEntityClz())) {
            containsSuper = true;
            absorb(superMeta);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void absorb(ClassMetadata thatMeta) {
        this.setIdFieldIfNone(thatMeta.getIdField());
        this.setNameFieldIfNone(thatMeta.getNameField());
        MapUtility.putIfAbsent(thatMeta.getReadonlyTabMetadataMap(), getRWTabMetadataMap());
        MapUtility.putIfAbsent(thatMeta.getReadonlyGroupMetadataMap(), getRWGroupMetadataMap());
        MapUtility.putIfAbsent(thatMeta.getReadonlyFieldMetadataMap(), getRWFieldMetadataMap());
        MapUtility.putIfAbsent(thatMeta.referencedEntityMetadata, referencedEntityMetadata);

        this.nonCollectionFields.addAll(thatMeta.nonCollectionFields);
        this.validators.addAll(thatMeta.getValidators());
        this.valueGates.addAll(thatMeta.getValueGates());
        this.referencedEntityMetadataPublished &= thatMeta.referencedEntityMetadataPublished;
    }

    public void finishBuilding() {
        if (this.nonCollectionFields.size() == 0) {
            List<String> nonCollection = new ArrayList<String>();
            List<String> collection = new ArrayList<String>();
            for (Map.Entry<String, IFieldMetadata> fieldMetadataEntry : fieldMetadataMap.entrySet()) {
                String fieldName = fieldMetadataEntry.getKey();
                IFieldMetadata fieldMetadata = fieldMetadataEntry.getValue();
                (fieldMetadata.isCollectionField() ? collection : nonCollection).add(fieldName);
            }
            this.nonCollectionFields.addAll(nonCollection);
        }
    }

    public Collection<String> getNonCollectionFields() {
        return Collections.unmodifiableCollection(nonCollectionFields);
    }

    public boolean isReferencedEntityMetadataPublished() {
        return referencedEntityMetadataPublished;
    }

    public void publishReferencedEntityMetadatas(Collection<ClassMetadata> cms) {
        if(null != cms) {
            for (ClassMetadata cm : cms) {
                this.referencedEntityMetadata.put(cm.getEntityClz().getName(), cm);
            }
        }
        referencedEntityMetadataPublished = true;
    }

    public ClassMetadata getReferencedEntityMetadata(String entity) {
        return this.referencedEntityMetadata.getOrDefault(entity, null);
    }

    public Collection<Class> getReferencedEntities() {
        Set<Class> entities = new HashSet<Class>();
        for (Map.Entry<String, IFieldMetadata> fieldMetadataEntry : fieldMetadataMap.entrySet()) {
            IFieldMetadata fieldMetadata = fieldMetadataEntry.getValue();
            if (fieldMetadata instanceof ForeignEntityFieldMetadata) {
                entities.add(((ForeignEntityFieldMetadata) fieldMetadata).getEntityType());
            } else if (fieldMetadata instanceof CollectionFieldMetadata) {
                if (((CollectionFieldMetadata) fieldMetadata).getElementType().isEntity()) {
                    entities.add(((CollectionFieldMetadata) fieldMetadata).getElementType().getEntityType());
                }
            } else if (fieldMetadata instanceof MapFieldMetadata) {
                if (((MapFieldMetadata) fieldMetadata).getKeyType().isEntity()) {
                    entities.add(((MapFieldMetadata) fieldMetadata).getKeyType().getEntityType());
                }
                if (((MapFieldMetadata) fieldMetadata).getValueType().isEntity()) {
                    entities.add(((MapFieldMetadata) fieldMetadata).getValueType().getEntityType());
                }
            }
        }
        return entities;
    }

    public boolean hasField(String fieldName) {
        return fieldMetadataMap.containsKey(fieldName);
    }

    @Override
    public ClassMetadata clone() {
        ClassMetadata copy = SerializationUtils.clone(this);
        copy.nameField = this.nameField;
        copy.idField = this.idField;
        return copy;
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
