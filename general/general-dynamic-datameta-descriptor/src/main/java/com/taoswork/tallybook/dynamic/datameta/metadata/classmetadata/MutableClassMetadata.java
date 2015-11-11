package com.taoswork.tallybook.dynamic.datameta.metadata.classmetadata;

import com.taoswork.tallybook.dynamic.datameta.metadata.GroupMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.IClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.IFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.TabMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.embedded.EmbeddedFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typed.ExternalForeignEntityFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typed.ForeignEntityFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typedcollection.CollectionLikeFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typedcollection.MapFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.friendly.FriendlyMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.utils.NativeClassHelper;
import com.taoswork.tallybook.general.datadomain.support.entity.validation.IEntityValidator;
import com.taoswork.tallybook.general.datadomain.support.entity.valuecopier.IEntityValueCopier;
import com.taoswork.tallybook.general.datadomain.support.entity.valuegate.IEntityValueGate;
import com.taoswork.tallybook.general.extension.collections.MapUtility;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Represents a class with its self containing metadata, super information not included by default.
 */
public class MutableClassMetadata extends FriendlyMetadata implements IClassMetadata, Cloneable {
    private static final Logger LOGGER = LoggerFactory.getLogger(MutableClassMetadata.class);
    private final Map<String, TabMetadata> tabMetadataMap = new HashMap<String, TabMetadata>();
    private final Map<String, GroupMetadata> groupMetadataMap = new HashMap<String, GroupMetadata>();
    private final Map<String, IFieldMetadata> fieldMetadataMap = new HashMap<String, IFieldMetadata>();
    private final Map<String, IClassMetadata> referencingClassMetadata = new HashMap<String, IClassMetadata>();
    private boolean referencingClassMetadataPublished = false;
    private final Set<String> collectionFields = new HashSet<String>();
    private final Set<String> nonCollectionFields = new HashSet<String>();
    private final Set<String> validators = new HashSet();
    private final Set<String> valueGates = new HashSet();
    private String valueCopier = null;
    public final Class<?> entityClz;
    public boolean containsSuper = false;
    private String idFieldName;
    private String nameFieldName;
    private transient Field idField;
    private transient Field nameField;

    public MutableClassMetadata(Class<?> entityClz) {
        this.entityClz = entityClz;
    }

    @Override
    public String getIdFieldName() {
        return idFieldName;
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

    @Override
    public Class<?> getEntityClz() {
        return entityClz;
    }

    @Override
    public boolean containsSuper() {
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

    @Override
    public Map<String, TabMetadata> getReadonlyTabMetadataMap() {
        return Collections.unmodifiableMap(tabMetadataMap);
    }

    @Override
    public Map<String, GroupMetadata> getReadonlyGroupMetadataMap() {
        return Collections.unmodifiableMap(groupMetadataMap);
    }

    @Override
    public Map<String, IFieldMetadata> getReadonlyFieldMetadataMap() {
        return Collections.unmodifiableMap(fieldMetadataMap);
    }

    @Override
    public Map<String, IClassMetadata> getReadonlyReferencingClassMetadataMap() {
        return Collections.unmodifiableMap(referencingClassMetadata);
    }

    @Override
    public IFieldMetadata getFieldMetadata(String fieldName) {
        return fieldMetadataMap.get(fieldName);
    }

    public void addValidator(Class<? extends IEntityValidator> validator) {
        if(IEntityValidator.class.equals(validator))
            return;
        if (validator != null)
            validators.add(validator.getName());
    }

    public void addValueGate(Class<? extends IEntityValueGate> valueGate) {
        if(IEntityValueGate.class.equals(valueGate))
            return;
        if (valueGate != null)
            valueGates.add(valueGate.getName());
    }

    public void setValueCopierIfNotSet(Class<? extends IEntityValueCopier> valueCopier){
        if(StringUtils.isEmpty(this.valueCopier)){
            setValueCopier(valueCopier);
        }
    }

    public void setValueCopier(Class<? extends IEntityValueCopier> valueCopier){
        if(IEntityValueCopier.class.equals(valueCopier))
            return;
        if(valueCopier != null){
            this.valueCopier = valueCopier.getName();
        }
    }

    protected void setValueCopierIfNotSet(String valueCopier){
        if(StringUtils.isEmpty(this.valueCopier) && StringUtils.isNotEmpty(valueCopier)){
            this.valueCopier = valueCopier;
        }
    }

    @Override
    public Collection<String> getReadonlyValidators() {
        return Collections.unmodifiableCollection(this.validators);
    }

    @Override
    public Collection<String> getReadonlyValueGates() {
        return Collections.unmodifiableCollection(this.valueGates);
    }

    @Override
    public String getValueCopier() {
        return valueCopier;
    }

    public void absorbSuper(IClassMetadata superMeta) {
        if (superMeta.getEntityClz().isAssignableFrom(this.getEntityClz())) {
            containsSuper = true;
            absorb(superMeta);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void absorb(IClassMetadata thatMeta) {
        this.setIdFieldIfNone(thatMeta.getIdField());
        this.setNameFieldIfNone(thatMeta.getNameField());
        MapUtility.putIfAbsent(thatMeta.getReadonlyTabMetadataMap(), getRWTabMetadataMap());
        MapUtility.putIfAbsent(thatMeta.getReadonlyGroupMetadataMap(), getRWGroupMetadataMap());
        MapUtility.putIfAbsent(thatMeta.getReadonlyFieldMetadataMap(), getRWFieldMetadataMap());
        MapUtility.putIfAbsent(thatMeta.getReadonlyReferencingClassMetadataMap(), referencingClassMetadata);

        this.setIdFieldIfNone(thatMeta.getIdField());

        this.nonCollectionFields.addAll(thatMeta.getNonCollectionFields());
        this.validators.addAll(thatMeta.getReadonlyValidators());
        this.valueGates.addAll(thatMeta.getReadonlyValueGates());
        this.setValueCopierIfNotSet(thatMeta.getValueCopier());
        this.referencingClassMetadataPublished &= thatMeta.isReferencingClassMetadataPublished();
    }

    public void finishBuilding() {
        if (this.nonCollectionFields.size() == 0) {
            nonCollectionFields.clear();
            collectionFields.clear();
            List<String> nonCollection = new ArrayList<String>();
            List<String> collection = new ArrayList<String>();
            for (Map.Entry<String, IFieldMetadata> fieldMetadataEntry : fieldMetadataMap.entrySet()) {
                String fieldName = fieldMetadataEntry.getKey();
                IFieldMetadata fieldMetadata = fieldMetadataEntry.getValue();
                (fieldMetadata.isCollectionField() ? collection : nonCollection).add(fieldName);
            }
            this.nonCollectionFields.addAll(nonCollection);
            this.collectionFields.addAll(collection);
        }
    }

    @Override
    public Collection<String> getCollectionFields() {
        return Collections.unmodifiableCollection(collectionFields);
    }

    @Override
    public Collection<String> getNonCollectionFields() {
        return Collections.unmodifiableCollection(nonCollectionFields);
    }

    public boolean isReferencingClassMetadataPublished() {
        return referencingClassMetadataPublished;
    }

    public void publishReferencingClassMetadatas(Collection<IClassMetadata> cms) {
        if (null != cms && !cms.isEmpty()) {
            for (IClassMetadata cm : cms) {
                this.referencingClassMetadata.put(cm.getEntityClz().getName(), cm);
            }
        }
        referencingClassMetadataPublished = true;
    }

    @Override
    public IClassMetadata getReferencingClassMetadata(Class entity) {
        return this.referencingClassMetadata.get(entity.getName());
    }

    @Override
    public boolean hasField(String fieldName) {
        return fieldMetadataMap.containsKey(fieldName);
    }

    @Override
    public MutableClassMetadata clone() {
        MutableClassMetadata copy = SerializationUtils.clone(this);
        copy.nameField = this.nameField;
        copy.idField = this.idField;
        return copy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MutableClassMetadata)) return false;

        MutableClassMetadata that = (MutableClassMetadata) o;

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
