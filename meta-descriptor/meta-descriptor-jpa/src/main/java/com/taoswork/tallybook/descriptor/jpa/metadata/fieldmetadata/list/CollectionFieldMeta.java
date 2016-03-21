package com.taoswork.tallybook.descriptor.jpa.metadata.fieldmetadata.list;

import com.taoswork.tallybook.datadomain.base.entity.CollectionMode;
import com.taoswork.tallybook.datadomain.base.presentation.FieldType;
import com.taoswork.tallybook.datadomain.base.presentation.typedcollection.entry.IPrimitiveEntry;
import com.taoswork.tallybook.descriptor.jpa.metadata.CollectionTypesSetting;
import com.taoswork.tallybook.descriptor.metadata.IFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.BaseCollectionFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.BasicFieldMetaObject;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.IFieldMetaSeed;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.list.ListFacet;
import org.apache.commons.lang3.SerializationUtils;

import java.util.Collection;

@Deprecated
public final class CollectionFieldMeta extends BaseCollectionFieldMeta {
    private final CollectionTypesSetting collectionTypesSetting;

    private final Class presentationCeilingClass;
    private final Class presentationClass;
    private final Class collectionImplementType;

    public CollectionFieldMeta(BasicFieldMetaObject bfmo,
                               CollectionTypesSetting collectionTypesSetting,
                               Class presentationCeilingClass,
                               Class presentationClass,
                               Class collectionImplementType) {
        super(bfmo);
        this.collectionTypesSetting = SerializationUtils.clone(collectionTypesSetting);
        this.presentationCeilingClass = presentationCeilingClass;
        this.presentationClass = presentationClass;
        this.collectionImplementType = collectionImplementType;
    }

    @Override
    protected FieldType overrideUnknownFieldType() {
        return FieldType.COLLECTION;
    }

    public CollectionTypesSetting getCollectionTypesSetting() {
        return collectionTypesSetting;
    }

    @Override
    public Class getPresentationClass() {
        return presentationClass;
    }

    @Override
    public Class getPresentationCeilingClass() {
        return presentationCeilingClass;
    }

    public Class getCollectionImplementType() {
        return collectionImplementType;
    }

    @Override
    public int gatherReferencingTypes(Collection<Class> collection) {
        collection.add(presentationClass);
        return 1;
    }


    public static class Seed implements IFieldMetaSeed {

        /**
         * the entry-type used for user editing, assume we have collection field:
         *
         * @Relation(target=XxxImp.class)
         * @PresentationCollection(collectionMode=mode, joinEntity=XxxRefImp.class, primitiveDelegate= primitiveDelegate.class)
         * List<Xxx> xxxs;
         * <p>
         * typical relationships are:
         * Model:               EntryType:    EntryTargetType:  EntryUiType
         * Primitive,           Xxx           XxxImp            primitiveDelegate
         * Embeddable,          Xxx           XxxImp            XxxImp(new embeddable object)
         * Entity,              Xxx           XxxImp            XxxImp(new entity, with foreign-key assigned)
         * Lookup,              Xxx           XxxImp            XxxImp(a list for lookup)
         * AdornedLookup        Xxx           XxxImp            XxxRefImp
         * <p>
         * Typical entryUiType
         */
        protected final CollectionTypesSetting collectionTypesSetting;

        public Seed(Class entryType, Class entryTargetType,
                    Class collectionClass,
                    CollectionMode collectionMode,
                    Class<? extends IPrimitiveEntry> entryPrimitiveDelegateType,
                    Class entryJoinEntityType) {
            this.collectionTypesSetting = new CollectionTypesSetting(entryType, entryTargetType,
                    collectionClass,
                    collectionMode,
                    entryPrimitiveDelegateType,
                    entryJoinEntityType);
        }

        public CollectionTypesSetting getCollectionTypesSetting() {
            return this.collectionTypesSetting;
        }

        @Override
        public IFieldMeta makeFieldMeta(BasicFieldMetaObject bfmo) {
            final Class presentationCeilingClass;
            final Class presentationClass;
            final Class collectionImplementType;

            collectionImplementType = ListFacet.workOutCollectionImplementType(collectionTypesSetting.getCollectionClass());

            switch (collectionTypesSetting.getCollectionMode()) {
                case Primitive:
                    presentationClass = collectionTypesSetting.getEntryPrimitiveDelegateType();
                    presentationCeilingClass = presentationClass;
                    break;
                case Basic:
                    presentationClass = collectionTypesSetting.getEntryTargetType();
                    presentationCeilingClass = presentationClass;
                    break;
                case Entity:
                    presentationClass = collectionTypesSetting.getEntryTargetType();
                    presentationCeilingClass = collectionTypesSetting.getEntryType();
                    break;
                case Lookup:
                    presentationClass = collectionTypesSetting.getEntryTargetType();
                    presentationCeilingClass = collectionTypesSetting.getEntryType();
                    break;
                case AdornedLookup:
                    presentationClass = collectionTypesSetting.getEntryJoinEntityType();
                    presentationCeilingClass = collectionTypesSetting.getEntryType();
                    break;
                default:
                    throw new IllegalArgumentException("Collection Model not specified.");
            }
            if (null == presentationClass) {
                throw new IllegalArgumentException("presentationClass couldn't be null.");
            }
            return new CollectionFieldMeta(bfmo, collectionTypesSetting, presentationCeilingClass, presentationClass, collectionImplementType);
        }
    }
}
