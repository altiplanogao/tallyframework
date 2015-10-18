package com.taoswork.tallybook.dynamic.dataservice.core.persistence.translate;

import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.ElementTypeUnion;
import com.taoswork.tallybook.dynamic.datameta.metadata.IFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.embedded.EmbeddedFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typed.ExternalForeignEntityFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typed.ForeignEntityFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typedcollection.CollectionFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typedcollection.MapFieldMetadata;
import com.taoswork.tallybook.dynamic.dataservice.core.exception.ServiceException;
import com.taoswork.tallybook.dynamic.dataservice.core.metaaccess.DynamicEntityMetadataAccess;
import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

/**
 * Copy level start from 0
 * If the level limit is 1, there is only 1 level copied
 */
public class CrossEntityManagerPersistableCopier {
    private class TtFieldCopier {
        final ClassMetadata topClassMetadata;
        final ElementTypeUnion elementType;
        final ClassMetadata entityClassMetadata;
        final int model;

        private final static int MODEL_BASIC = 1;
        private final static int MODEL_EMBEDDED = 2;
        private final static int MODEL_ENTITY = 3;
        private final static int MODEL_UNKNOWN = 4;

        public TtFieldCopier(final ClassMetadata topClassMetadata, ElementTypeUnion elementType) {
            this.topClassMetadata = topClassMetadata;
            this.elementType = elementType;
            if (elementType.isBasic()) {
                entityClassMetadata = null;
                model = MODEL_BASIC;
            } else if (elementType.isEmbedded()) {
                entityClassMetadata = null;
                model = MODEL_EMBEDDED;
            } else if (elementType.isEntity()) {
                Class entityCls = elementType.getEntityType();
                entityClassMetadata = topClassMetadata.getReferencedEntityMetadata(entityCls.getName());
                model = MODEL_ENTITY;
            } else {
                entityClassMetadata = null;
                model = MODEL_UNKNOWN;
            }
        }

        public TtFieldCopier(final ClassMetadata topClassMetadata, CollectionFieldMetadata fieldMetadata) {
            this(topClassMetadata, fieldMetadata.getElementType());
        }

        public TtFieldCopier(final ClassMetadata topClassMetadata, MapFieldMetadata fieldMetadata, boolean asKey, boolean asValue) {
            this(topClassMetadata, asKey ? fieldMetadata.getKeyType() : fieldMetadata.getValueType());
            if (!(asKey ^ asValue))
                throw new IllegalArgumentException();
        }

        public Object doCopy(Object source, final int currentLevel, final int levelLimit) throws InstantiationException, IllegalAccessException {
            switch (model) {
                case MODEL_BASIC:
                    return source;
                case MODEL_EMBEDDED:
                    return makeCopyForEmbeddable(topClassMetadata, source, elementType.getAsEmbeddedClassMetadata(), currentLevel, levelLimit);
                case MODEL_ENTITY:
                    return makeCopyForEntity(topClassMetadata, source, entityClassMetadata, currentLevel, levelLimit);
                default:
                    throw new IllegalStateException();
            }
        }
    }

    private final DynamicEntityMetadataAccess dynamicEntityMetadataAccess;

    public CrossEntityManagerPersistableCopier(DynamicEntityMetadataAccess dynamicEntityMetadataAccess) {
        this.dynamicEntityMetadataAccess = dynamicEntityMetadataAccess;
    }

    private <T> T makeCopyForEmbeddable(final ClassMetadata topClassMetadata, T embeddable, ClassMetadata embedCm,
                                        final int currentLevel, final int levelLimit) throws IllegalAccessException, InstantiationException {
        T emptyCopy = walkFieldsAndCopy(topClassMetadata, embedCm, embeddable, currentLevel, levelLimit);
        return emptyCopy;
    }

    private <T> T makeCopyForEntity(final ClassMetadata topClassMetadata, T entity, ClassMetadata entityCm,
                                    final int currentLevel, final int levelLimit) throws IllegalAccessException, InstantiationException {
        T emptyCopy = walkFieldsAndCopy(topClassMetadata, entityCm, entity, currentLevel, levelLimit);
        return emptyCopy;
    }

    private Collection makeCopyForCollection(final ClassMetadata topClassMetadata, Collection source, CollectionFieldMetadata collectionFieldMetadata,
                                             final int currentLevel, final int levelLimit) throws IllegalAccessException, InstantiationException {
        if (source == null)
            return null;
        final int nextLevel = currentLevel + 1;
        if (nextLevel >= levelLimit)
            return null;

        TtFieldCopier copier = new TtFieldCopier(topClassMetadata, collectionFieldMetadata);
        Collection target = (Collection) collectionFieldMetadata.getCollectionImplementType().newInstance();
//        if (collectionFieldMetadata.getBasicType() != null) {
//            if (source instanceof Serializable) {
//                return (Collection) SerializationUtils.clone((Serializable) source);
//            }
//        }
//
//        Collection target = source.getClass().newInstance();
        for (Object ele : source) {
            Object eleCpy = copier.doCopy(ele, nextLevel, levelLimit);
            target.add(eleCpy);
        }

        return target;
    }

    private Map makeCopyForMap(final ClassMetadata topClassMetadata, Map source, MapFieldMetadata mapFieldMetadata,
                               final int currentLevel, final int levelLimit) throws IllegalAccessException, InstantiationException {
        if (source == null)
            return null;
        final int nextLevel = currentLevel + 1;
        if (nextLevel >= levelLimit)
            return null;
//        if (mapFieldMetadata.getKeyBasicType() != null) {
//            if (mapFieldMetadata.getValueBasicType() != null) {
//                if (source instanceof Serializable) {
//                    return (Map) SerializationUtils.clone((Serializable) source);
//                } else {
//                    Map target = source.getClass().newInstance();
//                    target.putAll(source);
//                    return target;
//                }
//            }
//        }
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

    private <T> T walkFieldsAndCopy(final ClassMetadata topClassMetadata, ClassMetadata classMetadata, T source,
                                    final int currentLevel, final int levelLimit) throws IllegalAccessException, InstantiationException {
        if (source == null)
            return null;
        T target = (T) source.getClass().newInstance();

        if(classMetadata == null)
            classMetadata = topClassMetadata;

        Map<String, IFieldMetadata> fieldMetadataMap = classMetadata.getReadonlyFieldMetadataMap();
        for (Map.Entry<String, IFieldMetadata> fieldMetaEntry : fieldMetadataMap.entrySet()) {
            String fieldName = fieldMetaEntry.getKey();
            IFieldMetadata fieldMetadata = fieldMetaEntry.getValue();
            if (fieldMetadata.isPrimitiveField()) {
                Field field = fieldMetadata.getField();
                field.set(target, field.get(source));
                continue;
            }

            if (fieldMetadata instanceof EmbeddedFieldMetadata) {
                Field field = fieldMetadata.getField();
                ClassMetadata embeddedClassMetadata = ((EmbeddedFieldMetadata) fieldMetadata).getClassMetadata();
                Object fo = field.get(source);
                Object fn = this.makeCopyForEmbeddable(topClassMetadata, fo, embeddedClassMetadata, currentLevel, levelLimit);
                field.set(target, fn);
            } else if (fieldMetadata instanceof ForeignEntityFieldMetadata) {
                Class entityType = ((ForeignEntityFieldMetadata) fieldMetadata).getEntityType();
                ClassMetadata foreignClassMetadata = topClassMetadata.getReferencedEntityMetadata(entityType.getName());
                Field field = fieldMetadata.getField();
                Object fo = field.get(source);
                Object fn = walkFieldsAndCopy(topClassMetadata, foreignClassMetadata, fo, currentLevel, levelLimit);
                field.set(target, fn);
            } else if (fieldMetadata instanceof ExternalForeignEntityFieldMetadata) {
                throw new IllegalAccessException("Not Implemented");
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

    public <T extends Persistable> T makeSafeCopyForQuery(T rec) throws ServiceException {
        return this.makeSafeCopy(rec, 1);
    }

    public <T extends Persistable> T makeSafeCopyForRead(T rec) throws ServiceException {
        return this.makeSafeCopy(rec, 2);
    }

    private <T extends Persistable> T makeSafeCopy(T rec, int levelLimit) throws ServiceException {
        if (rec == null)
            return null;
        if (levelLimit < 1)
            levelLimit = 1;
        try {
            ClassMetadata topClassMetadata = this.dynamicEntityMetadataAccess.getClassMetadata(rec.getClass(), false);
            return this.walkFieldsAndCopy(topClassMetadata, null, rec, 0, levelLimit);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }
}