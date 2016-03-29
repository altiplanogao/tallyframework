package com.taoswork.tallybook.descriptor.dataio.copier;

import com.taoswork.tallybook.datadomain.base.entity.Persistable;
import com.taoswork.tallybook.datadomain.base.entity.valuecopier.EntityCopierPool;
import com.taoswork.tallybook.datadomain.base.entity.valuecopier.IEntityCopier;
import com.taoswork.tallybook.descriptor.dataio.reference.ExternalReference;
import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
import com.taoswork.tallybook.descriptor.metadata.IClassMetaAccess;
import com.taoswork.tallybook.descriptor.metadata.IFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.BasePrimitiveFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.basic.ExternalForeignEntityFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.basic.ForeignEntityFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.embedded.EmbeddedFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.list.ListFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.map.MapFieldMeta;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

/**
 * Copy level start from 0
 * If the level limit is 1, there is only 1 level copied
 */
public class REntityCopier {

    private final IClassMetaAccess classMetaAccess;
    private final ExternalReference externalReference;
    private final EntityCopierPool entityCopierPool;
//    private final RFieldCopierSolution fieldCopierPool;

    public REntityCopier(IClassMetaAccess classMetaAccess, ExternalReference externalReference,
                         EntityCopierPool entityCopierPool) {
        this.classMetaAccess = classMetaAccess;
        this.externalReference = externalReference;
        this.entityCopierPool = entityCopierPool;
//        this.fieldCopierPool = new RFieldCopierSolution();
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
            IClassMeta topMeta = this.classMetaAccess.getClassMeta(rec.getClass(), false);
            return this.walkFieldsAndCopy(topMeta, null, rec, 0, levelLimit);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new CopyException(e);
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new CopyException(e);
        }
    }

    private <T> T walkFieldsAndCopy(final IClassMeta topMeta, IClassMeta classMeta,
                                    T source, final int currentLevel, final int levelLimit) throws IllegalAccessException, InstantiationException {
        if (source == null)
            return null;
        T target = (T) source.getClass().newInstance();

        if(classMeta == null)
            classMeta = topMeta;

        final Collection<String> handledFields;
        String valueCopierName = classMeta.getValueCopier();
        IEntityCopier valueCopier = this.entityCopierPool.getValueCopier(valueCopierName);
        if(valueCopier != null){
            valueCopier.copy(source, target);
            handledFields = valueCopier.handledFields();
            if (valueCopier.allHandled()){
                return target;
            }
        } else {
            handledFields = null;
        }

        Map<String, IFieldMeta> fieldMetaMap = classMeta.getReadonlyFieldMetaMap();
        for (Map.Entry<String, IFieldMeta> fieldMetaEntry : fieldMetaMap.entrySet()) {
            String fieldName = fieldMetaEntry.getKey();
            if(handledFields != null && handledFields.contains(fieldName)){
                continue;
            }
            IFieldMeta fieldMeta = fieldMetaEntry.getValue();
            if(fieldMeta instanceof BasePrimitiveFieldMeta){
                Field field = fieldMeta.getField();
                field.set(target, field.get(source));
                continue;
            }
//            if(fieldCopierPool.copy(source, target, fieldMeta)){
//                continue;
//            }

            if (fieldMeta instanceof EmbeddedFieldMeta) {
                Field field = fieldMeta.getField();
                IClassMeta embeddedClassMeta = ((EmbeddedFieldMeta) fieldMeta).getClassMetadata();
                Object fo = field.get(source);
                Object fn = this.makeCopyForEmbeddable(topMeta, fo, embeddedClassMeta, currentLevel, levelLimit);
                field.set(target, fn);
            } else if (fieldMeta instanceof ForeignEntityFieldMeta) {
                Class entityType = ((ForeignEntityFieldMeta) fieldMeta).getTargetType();
                IClassMeta foreignClassMeta = topMeta.getReferencingClassMeta(entityType);
                Field field = fieldMeta.getField();
                Object fo = field.get(source);
                Object fn = walkFieldsAndCopy(topMeta, foreignClassMeta, fo, currentLevel, levelLimit);
                field.set(target, fn);
            } else if (fieldMeta instanceof ExternalForeignEntityFieldMeta) {
                ExternalForeignEntityFieldMeta efeFm = (ExternalForeignEntityFieldMeta) fieldMeta;
                Field foreignKeyField = fieldMeta.getField();
                Field foreignValField = efeFm.getEntityField();
                Object keyVal = foreignKeyField.get(source);
                foreignKeyField.set(target, keyVal);
                if (null == keyVal) {
                    foreignValField.set(target, null);
                } else {
                    if(externalReference != null) {
                        Class entityType = efeFm.getTargetType();
                        //backlog data: [type: entityType, key: keyVal]
                        //slot: [target: target, position: foreignValField]
                        externalReference.publishReference(target, foreignValField, entityType, keyVal.toString());
                    }
                    foreignValField.set(target, null);
//                    throw new IllegalAccessException("Not Implemented");
                }
            } else if (fieldMeta instanceof ListFieldMeta) {
                throw new IllegalAccessException("Un implemented");
            } else if (fieldMeta instanceof MapFieldMeta) {
                throw new IllegalAccessException("Un implemented");

//            } else if (fieldMeta instanceof OldMapFieldMeta) {
//                Field field = fieldMeta.getField();
//                Map fo = (Map) field.get(source);
//                Map fn = this.makeCopyForMap(topMeta, fo, (OldMapFieldMeta) fieldMeta, currentLevel, levelLimit);
//                field.set(target, fn);
            } else {
                throw new IllegalAccessException();
            }
        }
        return target;
    }

    private <T> T makeCopyForEmbeddable(final IClassMeta topMeta, T embeddable, IClassMeta embedCm,
                                        final int currentLevel, final int levelLimit) throws IllegalAccessException, InstantiationException {
        T emptyCopy = walkFieldsAndCopy(topMeta, embedCm, embeddable, currentLevel, levelLimit);
        return emptyCopy;
    }

    private <T> T makeCopyForEntity(final IClassMeta topMeta, T entity, IClassMeta entityCm,
                                    final int currentLevel, final int levelLimit) throws IllegalAccessException, InstantiationException {
        T emptyCopy = walkFieldsAndCopy(topMeta, entityCm, entity, currentLevel, levelLimit);
        return emptyCopy;
    }

//    private Collection makeCopyForCollection(final IClassMeta topMeta, Collection source, CollectionFieldMeta collectionFieldMeta,
//                                             final int currentLevel, final int levelLimit) throws IllegalAccessException, InstantiationException {
//        if (source == null)
//            return null;
//        final int nextLevel = currentLevel + 1;
//        if (nextLevel >= levelLimit)
//            return null;
//
//        //The 'source' could be in jpa-type (like org.hibernate.collection.internal.PersistentBag)
//        //So we cannot use direct copy: (Collection) SerializationUtils.clone((Serializable) source);
//        CollectionFieldCopier copier = new CollectionFieldCopier(topMeta, collectionFieldMeta);
//        Collection target = (Collection) collectionFieldMeta.getCollectionImplementType().newInstance();
//        for (Object ele : source) {
//            Object eleCpy = copier.doCopy(ele, nextLevel, levelLimit);
//            target.add(eleCpy);
//        }
//
//        return target;
//    }

//    private Map makeCopyForMap(final IClassMeta topMeta, Map source, OldMapFieldMeta mapFieldMeta,
//                               final int currentLevel, final int levelLimit) throws IllegalAccessException, InstantiationException {
//        if (source == null)
//            return null;
//        final int nextLevel = currentLevel + 1;
//        if (nextLevel >= levelLimit)
//            return null;
//
//        //The 'source' could be in jpa-type (like org.hibernate.collection.internal.PersistentMap)
//        //So we cannot use direct copy: (Map) SerializationUtils.clone((Serializable) source);
//        Map target = (Map) mapFieldMeta.getMapImplementType().newInstance();
//        final TtFieldCopier keyCopier = new TtFieldCopier(topMeta, mapFieldMeta, true, false);
//        final TtFieldCopier valueCopier = new TtFieldCopier(topMeta, mapFieldMeta, false, true);
//        for (Object entryInObj : source.entrySet()) {
//            Map.Entry entry = (Map.Entry) entryInObj;
//            Object key = entry.getKey();
//            Object value = entry.getValue();
//            Object keyCpy = keyCopier.copy(key, nextLevel, levelLimit);
//            Object valueCpy = valueCopier.copy(value, nextLevel, levelLimit);
//            target.put(keyCpy, valueCpy);
//        }
//        return target;
//    }

}