package com.taoswork.tallybook.dynamic.datameta.metadata;

import com.taoswork.tallybook.dynamic.datameta.metadata.utils.NativeClassHelper;
import com.taoswork.tallybook.general.datadomain.support.entity.validation.IEntityValidator;
import com.taoswork.tallybook.general.datadomain.support.entity.valuegate.IEntityValueGate;
import com.taoswork.tallybook.general.extension.collections.MapUtility;
import com.taoswork.tallybook.general.extension.utils.CloneUtility;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Represents a class with its self containing metadata, super information not included by default.
 *
 */
public class ClassMetadata extends FriendlyMetadata implements Cloneable, Serializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassMetadata.class);

    public Class<?> entityClz;
    private String idFieldName;
    private String nameFieldName;
    private transient Field idField;
    private transient Field nameField;
    public boolean containsSuper = false;
    private final Map<String, TabMetadata> tabMetadataMap = new HashMap<String, TabMetadata>();
    private final Map<String, GroupMetadata> groupMetadataMap = new HashMap<String, GroupMetadata>();
    private final Map<String, FieldMetadata> fieldMetadataMap = new HashMap<String, FieldMetadata>();
    private final Set<String> validators = new HashSet();
    private final Set<String> valueGates = new HashSet();

    public ClassMetadata(){
        this(null);
    }
    private ClassMetadata(Class<?> entityClz){
        setEntityClz(entityClz);
    }

    public Field getIdField(){
        if(idField != null)
            return idField;
        if(StringUtils.isNotEmpty(this.idFieldName)){
            idField = NativeClassHelper.getFieldOfName(entityClz, idFieldName, true);
            idField.setAccessible(true);
            return idField;
        } else {
            return null;
        }
    }

    public void setIdField(Field idField) {
        if(idField != null) {
            this.idField = idField;
            this.idField.setAccessible(true);
            this.idFieldName = idField.getName();
        }
    }

    public void setIdFieldIfNone(Field idField) {
        if(null == this.idField){
            this.setIdField(idField);
        }
    }

    public Field getNameField() {
        if(nameField != null)
            return nameField;
        if(StringUtils.isNotEmpty(this.nameFieldName)){
            nameField = NativeClassHelper.getFieldOfName(entityClz, nameFieldName, true);
            nameField.setAccessible(true);
            return nameField;
        } else {
            return null;
        }
    }

    public void setNameField(Field nameField) {
        if(nameField != null){
            this.nameField = nameField;
            this.nameField.setAccessible(true);
            this.nameFieldName = nameField.getName();
        }
    }

    public void setNameFieldIfNone(Field nameField) {
        if(null == this.nameField){
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

    public FieldMetadata getFieldMetadata(String fieldName){
        return fieldMetadataMap.getOrDefault(fieldName, null);
    }

    public void addValidator(Class<? extends IEntityValidator> validator){
        if(validator != null)
            validators.add(validator.getName());
    }

    public void addValueGate(Class<? extends IEntityValueGate> valueGate){
        if(valueGate != null)
            valueGates.add(valueGate.getName());
    }

    public Collection<String> getValidators(){
        return Collections.unmodifiableCollection(this.validators);
    }

    public Collection<String> getValueGates() {
        return Collections.unmodifiableCollection(this.valueGates);
    }

    public void absorbSuper(ClassMetadata superMeta){
        if(superMeta.getEntityClz().isAssignableFrom(this.getEntityClz())){
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
        this.validators.addAll(thatMeta.getValidators());
        this.valueGates.addAll(thatMeta.getValueGates());
    }

    public boolean hasField(String fieldName){
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
