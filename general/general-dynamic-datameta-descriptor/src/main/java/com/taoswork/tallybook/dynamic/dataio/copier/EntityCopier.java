package com.taoswork.tallybook.dynamic.dataio.copier;

import com.taoswork.tallybook.dynamic.datameta.metadata.*;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.embedded.EmbeddedFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typed.ExternalForeignEntityFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typed.ForeignEntityFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typedcollection.CollectionFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typedcollection.MapFieldMetadata;
import com.taoswork.tallybook.dynamic.dataio.reference.ExternalReference;
import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;
import com.taoswork.tallybook.general.datadomain.support.entity.valuecopier.IEntityValueCopier;
import com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection.CollectionMode;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

/**
 * Copy level start from 0
 * If the level limit is 1, there is only 1 level copied
 */
public class EntityCopier {
    private class CollectionFieldCopier{
        final IClassMetadata topClassMetadata;
        private final boolean primitive;
        private final IClassMetadata referencingClassMetadata;
        public CollectionFieldCopier(final IClassMetadata topClassMetadata, CollectionFieldMetadata fieldMetadata) {
            CollectionTypesSetting collectionTypesSetting = fieldMetadata.getCollectionTypesSetting();
            this.topClassMetadata = topClassMetadata;
            this.primitive = CollectionMode.Primitive.equals(collectionTypesSetting.getCollectionMode());
            if(primitive){
                referencingClassMetadata = topClassMetadata.getReferencingClassMetadata(collectionTypesSetting.getEntryTargetType());
            }else {
                referencingClassMetadata = null;
            }
        }

        public Object doCopy(Object source, final int currentLevel, final int levelLimit) throws InstantiationException, IllegalAccessException {
            if(primitive){
                return source;
            }else {
                return makeCopyForEntity(topClassMetadata, source, referencingClassMetadata, currentLevel, levelLimit);
            }
        }
    }
    private class TtFieldCopier {
        final IClassMetadata topClassMetadata;
        final EntryTypeUnion elementType;
        final IClassMetadata embeddableClassMetadata;
        final IClassMetadata entityClassMetadata;
        final int model;

        private final static int MODEL_BASIC = 1;
        private final static int MODEL_EMBEDDED = 2;
        private final static int MODEL_ENTITY = 3;
        private final static int MODEL_UNKNOWN = 4;

        public TtFieldCopier(final IClassMetadata topClassMetadata, EntryTypeUnion elementType) {
            this.topClassMetadata = topClassMetadata;
            this.elementType = elementType;
            if (elementType.isSimple()) {
                embeddableClassMetadata = null;
                entityClassMetadata = null;
                model = MODEL_BASIC;
            } else if (elementType.isEmbeddable()) {
                Class entryCls = elementType.getEntryClass();
                embeddableClassMetadata = topClassMetadata.getReferencingClassMetadata(entryCls);
                entityClassMetadata = null;
                model = MODEL_EMBEDDED;
            } else if (elementType.isEntity()) {
                embeddableClassMetadata = null;
                Class entryCls = elementType.getEntryClass();
                entityClassMetadata = topClassMetadata.getReferencingClassMetadata(entryCls);
                model = MODEL_ENTITY;
            } else {
                embeddableClassMetadata = null;
                entityClassMetadata = null;
                model = MODEL_UNKNOWN;
            }
        }

        public TtFieldCopier(final IClassMetadata topClassMetadata, MapFieldMetadata fieldMetadata, boolean asKey, boolean asValue) {
            this(topClassMetadata, asKey ? fieldMetadata.getKeyType() : fieldMetadata.getValueType());
            if (!(asKey ^ asValue))
                throw new IllegalArgumentException();
        }

        public Object doCopy(Object source, final int currentLevel, final int levelLimit) throws InstantiationException, IllegalAccessException {
            switch (model) {
                case MODEL_BASIC:
                    return source;
                case MODEL_EMBEDDED:
                    return makeCopyForEmbeddable(topClassMetadata, source, embeddableClassMetadata, currentLevel, levelLimit);
                case MODEL_ENTITY:
                    return makeCopyForEntity(topClassMetadata, source, entityClassMetadata, currentLevel, levelLimit);
                default:
                    throw new IllegalStateException();
            }
        }
    }

    private final IClassMetadataAccess classMetadataAccess;
    private final ExternalReference externalReference;
    private final EntityCopierManager entityCopierManager;

    public EntityCopier(IClassMetadataAccess classMetadataAccess, ExternalReference externalReference, EntityCopierManager entityCopierManager) {
        this.classMetadataAccess = classMetadataAccess;
        this.externalReference = externalReference;
        this.entityCopierManager = entityCopierManager;
    }

    public <T extends Persistable> T makeSafeCopyForQuery(T rec) throws CopyException {
        return this.makeSafeCopy(rec, 1);
    }

    public <T extends Persistable> T makeSafeCopyForRead(T rec) throws CopyException {
        return this.makeSafeCopy(rec, 2);
    }

    private <T extends Persistable> T makeSafeCopy(T rec, int levelLimit) throws CopyException {
        if (rec == null)
            return null;
        if (levelLimit < 1)
            levelLimit = 1;
        try {
            IClassMetadata topClassMetadata = this.classMetadataAccess.getClassMetadata(rec.getClass(), false);
            return this.walkFieldsAndCopy(topClassMetadata, null, rec, 0, levelLimit);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new CopyException(e);
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new CopyException(e);
        }
    }

    private <T> T walkFieldsAndCopy(final IClassMetadata topClassMetadata, IClassMetadata classMetadata, T source,
                                    final int currentLevel, final int levelLimit) throws IllegalAccessException, InstantiationException {
        if (source == null)
            return null;
        T target = (T) source.getClass().newInstance();

        if(classMetadata == null)
            classMetadata = topClassMetadata;

        final Collection<String> handledFields;
        String valueCopierName = classMetadata.getValueCopier();
        IEntityValueCopier valueCopier = this.entityCopierManager.getValueCopier(valueCopierName);
        if(valueCopier != null){
            valueCopier.copy(source, target);
            handledFields = valueCopier.handledFields();
            if (valueCopier.allHandled()){
                return target;
            }
        } else {
            handledFields = null;
        }

        Map<String, IFieldMetadata> fieldMetadataMap = classMetadata.getReadonlyFieldMetadataMap();
        for (Map.Entry<String, IFieldMetadata> fieldMetaEntry : fieldMetadataMap.entrySet()) {
            String fieldName = fieldMetaEntry.getKey();
            if(handledFields != null && handledFields.contains(fieldName)){
                continue;
            }
            IFieldMetadata fieldMetadata = fieldMetaEntry.getValue();
            if (fieldMetadata.isPrimitiveField()) {
                Field field = fieldMetadata.getField();
                field.set(target, field.get(source));
                continue;
            }

            if (fieldMetadata instanceof EmbeddedFieldMetadata) {
                Field field = fieldMetadata.getField();
                IClassMetadata embeddedClassMetadata = ((EmbeddedFieldMetadata) fieldMetadata).getClassMetadata();
                Object fo = field.get(source);
                Object fn = this.makeCopyForEmbeddable(topClassMetadata, fo, embeddedClassMetadata, currentLevel, levelLimit);
                field.set(target, fn);
            } else if (fieldMetadata instanceof ForeignEntityFieldMetadata) {
                Class entityType = ((ForeignEntityFieldMetadata) fieldMetadata).getEntityType();
                IClassMetadata foreignClassMetadata = topClassMetadata.getReferencingClassMetadata(entityType);
                Field field = fieldMetadata.getField();
                Object fo = field.get(source);
                Object fn = walkFieldsAndCopy(topClassMetadata, foreignClassMetadata, fo, currentLevel, levelLimit);
                field.set(target, fn);
            } else if (fieldMetadata instanceof ExternalForeignEntityFieldMetadata) {
                ExternalForeignEntityFieldMetadata efeFm = (ExternalForeignEntityFieldMetadata) fieldMetadata;
                Field foreignKeyField = fieldMetadata.getField();
                Field foreignValField = efeFm.getEntityField();
                Object keyVal = foreignKeyField.get(source);
                foreignKeyField.set(target, keyVal);
                if (null == keyVal) {
                    foreignValField.set(target, null);
                } else {
                    if(externalReference != null) {
                        Class entityType = efeFm.getEntityType();
                        //backlog data: [type: entityType, key: keyVal]
                        //slot: [target: target, position: foreignValField]
                        externalReference.publishReference(target, foreignValField, entityType, keyVal);
                    }
                    foreignValField.set(target, null);
//                    throw new IllegalAccessException("Not Implemented");
                }
            } else if (fieldMetadata instanceof CollectionFieldMetadata) {
                Field field = fieldMetadata.getField();
                Collection fo = (Collection) field.get(source);
                Collection fn = this.makeCopyForCollection(topClassMetadata, fo, (CollectionFieldMetadata) fieldMetadata, currentLevel, levelLimit);
                field.set(target, fn);
            } else if (fieldMetadata instanceof MapFieldMetadata) {
                Field field = fieldMetadata.getField();
                Map fo = (Map) field.get(source);
                Map fn = this.makeCopyForMap(topClassMetadata, fo, (MapFieldMetadata) fieldMetadata, currentLevel, levelLimit);
                field.set(target, fn);
            } else {
                throw new IllegalAccessException();
            }
        }
        return target;
    }

    private <T> T makeCopyForEmbeddable(final IClassMetadata topClassMetadata, T embeddable, IClassMetadata embedCm,
                                        final int currentLevel, final int levelLimit) throws IllegalAccessException, InstantiationException {
        T emptyCopy = walkFieldsAndCopy(topClassMetadata, embedCm, embeddable, currentLevel, levelLimit);
        return emptyCopy;
    }

    private <T> T makeCopyForEntity(final IClassMetadata topClassMetadata, T entity, IClassMetadata entityCm,
                                    final int currentLevel, final int levelLimit) throws IllegalAccessException, InstantiationException {
        T emptyCopy = walkFieldsAndCopy(topClassMetadata, entityCm, entity, currentLevel, levelLimit);
        return emptyCopy;
    }

    private Collection makeCopyForCollection(final IClassMetadata topClassMetadata, Collection source, CollectionFieldMetadata collectionFieldMetadata,
                                             final int currentLevel, final int levelLimit) throws IllegalAccessException, InstantiationException {
        if (source == null)
            return null;
        final int nextLevel = currentLevel + 1;
        if (nextLevel >= levelLimit)
            return null;

        //The 'source' could be in jpa-type (like org.hibernate.collection.internal.PersistentBag)
        //So we cannot use direct copy: (Collection) SerializationUtils.clone((Serializable) source);
        CollectionFieldCopier copier = new CollectionFieldCopier(topClassMetadata, collectionFieldMetadata);
        Collection target = (Collection) collectionFieldMetadata.getCollectionImplementType().newInstance();
        for (Object ele : source) {
            Object eleCpy = copier.doCopy(ele, nextLevel, levelLimit);
            target.add(eleCpy);
        }

        return target;
    }

    private Map makeCopyForMap(final IClassMetadata topClassMetadata, Map source, MapFieldMetadata mapFieldMetadata,
                               final int currentLevel, final int levelLimit) throws IllegalAccessException, InstantiationException {
        if (source == null)
            return null;
        final int nextLevel = currentLevel + 1;
        if (nextLevel >= levelLimit)
            return null;

        //The 'source' could be in jpa-type (like org.hibernate.collection.internal.PersistentMap)
        //So we cannot use direct copy: (Map) SerializationUtils.clone((Serializable) source);
        Map target = (Map) mapFieldMetadata.getMapImplementType().newInstance();
        final TtFieldCopier keyCopier = new TtFieldCopier(topClassMetadata, mapFieldMetadata, true, false);
        final TtFieldCopier valueCopier = new TtFieldCopier(topClassMetadata, mapFieldMetadata, false, true);
        for (Object entryInObj : source.entrySet()) {
            Map.Entry entry = (Map.Entry) entryInObj;
            Object key = entry.getKey();
            Object value = entry.getValue();
            Object keyCpy = keyCopier.doCopy(key, nextLevel, levelLimit);
            Object valueCpy = valueCopier.doCopy(value, nextLevel, levelLimit);
            target.put(keyCpy, valueCpy);
        }
        return target;
    }

}