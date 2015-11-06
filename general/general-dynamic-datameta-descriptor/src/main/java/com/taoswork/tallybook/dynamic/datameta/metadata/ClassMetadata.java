package com.taoswork.tallybook.dynamic.datameta.metadata;

import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.embedded.EmbeddedFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typed.ExternalForeignEntityFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typed.ForeignEntityFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typedcollection.CollectionFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typedcollection.CollectionLikeFieldMetadata;
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
    private final Map<Class, ClassMetadata> referencingClassMetadata = new HashMap<Class, ClassMetadata>();
    private final Set<String> nonCollectionFields = new HashSet<String>();
    private final Set<String> validators = new HashSet();
    private final Set<String> valueGates = new HashSet();
    public Class<?> entityClz;
    public boolean containsSuper = false;
    private String idFieldName;
    private String nameFieldName;
    private transient Field idField;
    private transient Field nameField;
    private boolean referencingClassMetadataPublished = false;

    public ClassMetadata() {
        this(null);
    }

    private ClassMetadata(Class<?> entityClz) {
        setEntityClz(entityClz);
    }

    public String getIdFieldName() {
        return idFieldName;
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

    public String getNameFieldName() {
        return nameFieldName;
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
        return fieldMetadataMap.get(fieldName);
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
        MapUtility.putIfAbsent(thatMeta.referencingClassMetadata, referencingClassMetadata);

        this.nonCollectionFields.addAll(thatMeta.nonCollectionFields);
        this.validators.addAll(thatMeta.getValidators());
        this.valueGates.addAll(thatMeta.getValueGates());
        this.referencingClassMetadataPublished &= thatMeta.referencingClassMetadataPublished;
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

    public boolean isReferencingClassMetadataPublished() {
        return referencingClassMetadataPublished;
    }

    public void publishReferencingClassMetadatas(Collection<ClassMetadata> cms) {
        if (null != cms && !cms.isEmpty()) {
            for (ClassMetadata cm : cms) {
                this.referencingClassMetadata.put(cm.getEntityClz(), cm);
            }
        }
        referencingClassMetadataPublished = true;
    }

    public ClassMetadata getReferencingClassMetadata(Class entity) {
        return this.referencingClassMetadata.get(entity);
    }

    public Collection<Class> getReferencedTypes() {
        Set<Class> entities = new HashSet<Class>();
        for (Map.Entry<String, IFieldMetadata> fieldMetadataEntry : fieldMetadataMap.entrySet()) {
            IFieldMetadata fieldMetadata = fieldMetadataEntry.getValue();
            if(fieldMetadata.getIgnored()){
                continue;
            }
            if (fieldMetadata instanceof ForeignEntityFieldMetadata) {
                entities.add(((ForeignEntityFieldMetadata) fieldMetadata).getEntityType());
            } else if (fieldMetadata instanceof ExternalForeignEntityFieldMetadata) {
                //add or not?
                entities.add(((ExternalForeignEntityFieldMetadata) fieldMetadata).getEntityType());
            } else if (fieldMetadata instanceof CollectionLikeFieldMetadata) {
                Class entryType = ((CollectionLikeFieldMetadata) fieldMetadata).getEntryTypeUnion().getPresentationClass();
                if (entryType != null) {
                    entities.add(entryType);
                }
            } else if (fieldMetadata instanceof MapFieldMetadata) {
                MapFieldMetadata typedFieldMetadata = (MapFieldMetadata) fieldMetadata;
                {
                    Class keyEntryType = typedFieldMetadata.getKeyType().getPresentationClass();
                    if (keyEntryType != null) {
                        entities.add(keyEntryType);
                    }
                }
                {
                    Class valEntryType = typedFieldMetadata.getValueType().getPresentationClass();
                    if (valEntryType != null) {
                        entities.add(valEntryType);
                    }
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

    //Usually used by query filter
    public static IFieldMetadata getRoutedFieldMetadata(ClassMetadata classMetadata, String propertyPath) {
        int dpos = propertyPath.indexOf(".");
        if (dpos < 0) {
            IFieldMetadata fieldMetadata = classMetadata.getFieldMetadata(propertyPath);
            return fieldMetadata;
        }
        String currentPiece = propertyPath.substring(0, dpos);
        String remainPiece = propertyPath.substring(dpos + 1);

        IFieldMetadata fieldMetadata = classMetadata.getFieldMetadata(currentPiece);
        if (fieldMetadata instanceof ForeignEntityFieldMetadata) {
            ForeignEntityFieldMetadata foreignEntityFieldMetadata = (ForeignEntityFieldMetadata) fieldMetadata;
            Class entityTypeName = foreignEntityFieldMetadata.getEntityType();
            ClassMetadata subCm = classMetadata.getReferencingClassMetadata(entityTypeName);
            return getRoutedFieldMetadata(subCm, remainPiece);
        } else if (fieldMetadata instanceof EmbeddedFieldMetadata) {
            EmbeddedFieldMetadata embeddedFieldMetadata = (EmbeddedFieldMetadata) fieldMetadata;
            ClassMetadata subCm = embeddedFieldMetadata.getClassMetadata();
            return getRoutedFieldMetadata(subCm, remainPiece);
        } else {
            LOGGER.error("Get Routed FieldMetadata of '{}' not handled.", fieldMetadata.getName());
            return null;
        }
    }
}
