package com.taoswork.tallybook.descriptor.metadata.classmetadata;

import com.taoswork.tallybook.datadomain.base.entity.PersistEntity;
import com.taoswork.tallybook.datadomain.base.entity.PersistField;
import com.taoswork.tallybook.datadomain.base.entity.validation.IEntityValidator;
import com.taoswork.tallybook.datadomain.base.entity.valuecopier.IEntityCopier;
import com.taoswork.tallybook.datadomain.base.entity.valuegate.IEntityGate;
import com.taoswork.tallybook.datadomain.base.presentation.PresentationClass;
import com.taoswork.tallybook.datadomain.base.presentation.PresentationField;
import com.taoswork.tallybook.descriptor.metadata.GroupMeta;
import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
import com.taoswork.tallybook.descriptor.metadata.IFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.TabMeta;
import com.taoswork.tallybook.descriptor.metadata.friendly.FriendlyMeta;
import com.taoswork.tallybook.general.extension.collections.MapUtility;
import com.taoswork.tallybook.general.solution.reflect.ClassUtility;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Represents a class with its self containing metadata, super information not included by default.
 */
public class MutableClassMeta extends FriendlyMeta implements IClassMeta, Cloneable {
    private static final Logger LOGGER = LoggerFactory.getLogger(MutableClassMeta.class);
    private final Map<String, TabMeta> tabMetaMap = new HashMap<String, TabMeta>();
    private final Map<String, GroupMeta> groupMetaMap = new HashMap<String, GroupMeta>();
    private final Map<String, IFieldMeta> fieldMetaMap = new HashMap<String, IFieldMeta>();
    private final Map<String, IClassMeta> referencingClassMeta = new HashMap<String, IClassMeta>();
    private boolean referencingClassMetaPublished = false;
    private final Set<String> collectionFields = new HashSet<String>();
    private final Set<String> nonCollectionFields = new HashSet<String>();
    private final Set<String> validators = new HashSet();
    private final Set<String> valueGates = new HashSet();
    private final Map<String, PresentationField> presentationFieldOverrides = new HashMap<String, PresentationField>();
    private final Map<String, PersistField> persistFieldOverrides = new HashMap<String, PersistField>();
    private String valueCopier = null;
    public final Class<?> entityClz;
    public boolean containsSuper = false;
    private String idFieldName;
    private String nameFieldName;
    private transient Field idField;
    private transient Field nameField;

    public MutableClassMeta(Class<?> entityClz) {
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
            idField = ClassUtility.getFieldOfName(entityClz, idFieldName, true);
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
            nameField = ClassUtility.getFieldOfName(entityClz, nameFieldName, true);
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

    @Override
    public boolean containsHierarchy() {
        return false;
    }

    public void setContainsSuper(boolean containsSuper) {
        this.containsSuper = containsSuper;
    }

    public Map<String, TabMeta> getRWTabMetadataMap() {
        return (tabMetaMap);
    }

    public Map<String, GroupMeta> getRWGroupMetadataMap() {
        return (groupMetaMap);
    }

    public Map<String, IFieldMeta> getRWFieldMetaMap() {
        return (fieldMetaMap);
    }

    @Override
    public Map<String, TabMeta> getReadonlyTabMetaMap() {
        return Collections.unmodifiableMap(tabMetaMap);
    }

    @Override
    public Map<String, GroupMeta> getReadonlyGroupMetaMap() {
        return Collections.unmodifiableMap(groupMetaMap);
    }

    @Override
    public Map<String, IFieldMeta> getReadonlyFieldMetaMap() {
        return Collections.unmodifiableMap(fieldMetaMap);
    }

    @Override
    public Map<String, IClassMeta> getReadonlyReferencingClassMetaMap() {
        return Collections.unmodifiableMap(referencingClassMeta);
    }

    public Map<String, PresentationField> getPresentationFieldOverrides() {
        return presentationFieldOverrides;
    }

    public Map<String, PersistField> getPersistFieldOverrides() {
        return persistFieldOverrides;
    }

    public PresentationField getPresentationFieldOverride(String fieldName) {
        return presentationFieldOverrides.get(fieldName);
    }

    public Collection<String> getFieldsOverrided(){
        Set<String> fs = new HashSet<String>();
        fs.addAll(persistFieldOverrides.keySet());
        fs.addAll(presentationFieldOverrides.keySet());
        return fs;
    }

    public PersistField getPersistFieldOverride(String fieldName) {
        return persistFieldOverrides.get(fieldName);
    }


    @Override
    public IFieldMeta getFieldMeta(String fieldName) {
        return fieldMetaMap.get(fieldName);
    }

    public void addValidator(Class<? extends IEntityValidator> validator) {
        if (IEntityValidator.class.equals(validator))
            return;
        if (validator != null)
            validators.add(validator.getName());
    }

    public void addValueGate(Class<? extends IEntityGate> valueGate) {
        if (IEntityGate.class.equals(valueGate))
            return;
        if (valueGate != null)
            valueGates.add(valueGate.getName());
    }

    public void setValueCopierIfNotSet(Class<? extends IEntityCopier> valueCopier) {
        if (StringUtils.isEmpty(this.valueCopier)) {
            setValueCopier(valueCopier);
        }
    }

    public void setValueCopier(Class<? extends IEntityCopier> valueCopier) {
        if (IEntityCopier.class.equals(valueCopier))
            return;
        if (valueCopier != null) {
            this.valueCopier = valueCopier.getName();
        }
    }

    protected void setValueCopierIfNotSet(String valueCopier) {
        if (StringUtils.isEmpty(this.valueCopier) && StringUtils.isNotEmpty(valueCopier)) {
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

    public void absorbSuper(IClassMeta superMeta) {
        if (superMeta.getEntityClz().isAssignableFrom(this.getEntityClz())) {
            containsSuper = true;
            absorb(superMeta);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void absorb(IClassMeta thatMeta) {
        this.setIdFieldIfNone(thatMeta.getIdField());
        this.setNameFieldIfNone(thatMeta.getNameField());
        MapUtility.putIfAbsent(thatMeta.getReadonlyTabMetaMap(), getRWTabMetadataMap());
        MapUtility.putIfAbsent(thatMeta.getReadonlyGroupMetaMap(), getRWGroupMetadataMap());
        MapUtility.putIfAbsent(thatMeta.getReadonlyFieldMetaMap(), getRWFieldMetaMap());
        MapUtility.putIfAbsent(thatMeta.getReadonlyReferencingClassMetaMap(), referencingClassMeta);

        this.setIdFieldIfNone(thatMeta.getIdField());

        this.nonCollectionFields.addAll(thatMeta.getNonCollectionFields());
        this.validators.addAll(thatMeta.getReadonlyValidators());
        this.valueGates.addAll(thatMeta.getReadonlyValueGates());
        this.setValueCopierIfNotSet(thatMeta.getValueCopier());
        this.referencingClassMetaPublished &= thatMeta.isReferencingClassMetaPublished();
    }

    public void finishBuilding() {
        if (this.nonCollectionFields.size() == 0) {
            nonCollectionFields.clear();
            collectionFields.clear();
            List<String> nonCollection = new ArrayList<String>();
            List<String> collection = new ArrayList<String>();
            for (Map.Entry<String, IFieldMeta> fieldMetaEntry : fieldMetaMap.entrySet()) {
                String fieldName = fieldMetaEntry.getKey();
                IFieldMeta fieldMeta = fieldMetaEntry.getValue();
                (fieldMeta.isCollectionField() ? collection : nonCollection).add(fieldName);
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

    public boolean isReferencingClassMetaPublished() {
        return referencingClassMetaPublished;
    }

    public void publishReferencingClassMetadatas(Collection<IClassMeta> cms) {
        if (null != cms && !cms.isEmpty()) {
            for (IClassMeta cm : cms) {
                this.referencingClassMeta.put(cm.getEntityClz().getName(), cm);
            }
        }
        referencingClassMetaPublished = true;
    }

    @Override
    public IClassMeta getReferencingClassMeta(Class entity) {
        return this.referencingClassMeta.get(entity.getName());
    }

    @Override
    public boolean hasField(String fieldName) {
        return fieldMetaMap.containsKey(fieldName);
    }

    @Override
    public MutableClassMeta clone() {
        MutableClassMeta copy = SerializationUtils.clone(this);
        copy.nameField = this.nameField;
        copy.idField = this.idField;
        return copy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MutableClassMeta)) return false;

        MutableClassMeta that = (MutableClassMeta) o;

        if (entityClz != null ? !entityClz.equals(that.entityClz) : that.entityClz != null) return false;
        if (tabMetaMap != null ? !tabMetaMap.equals(that.tabMetaMap) : that.tabMetaMap != null)
            return false;
        if (groupMetaMap != null ? !groupMetaMap.equals(that.groupMetaMap) : that.groupMetaMap != null)
            return false;
        return !(fieldMetaMap != null ? !fieldMetaMap.equals(that.fieldMetaMap) : that.fieldMetaMap != null);

    }

    @Override
    public int hashCode() {
        int result = entityClz != null ? entityClz.hashCode() : 0;
        result = 31 * result + (tabMetaMap != null ? tabMetaMap.size() : 0);
        result = 31 * result + (groupMetaMap != null ? groupMetaMap.size() : 0);
        result = 31 * result + (fieldMetaMap != null ? fieldMetaMap.size() : 0);
        return result;
    }

}
