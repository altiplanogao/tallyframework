package com.taoswork.tallybook.dynamic.datameta.metadata.facet.collections;

import com.taoswork.tallybook.dynamic.datameta.metadata.CollectionTypesSetting;
import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.IFieldMetadataFacet;
import com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection.CollectionMode;
import com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection.entry.IPrimitiveEntry;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
public class CollectionFieldMetadataFacet implements IFieldMetadataFacet {

    /**
     * the entry-type used for user editing, assume we have collection field:
     * @Relation(target=XxxImp.class)
     * @PresentationCollection(collectionMode=mode, joinEntity=XxxRefImp.class, primitiveDelegate= primitiveDelegate.class)
     * List<Xxx> xxxs;
     *
     * typical relationships are:
     * Model:               EntryType:    EntryTargetType:  EntryUiType
     * Primitive,           Xxx           XxxImp            primitiveDelegate
     * Embeddable,          Xxx           XxxImp            XxxImp(new embeddable object)
     * Entity,              Xxx           XxxImp            XxxImp(new entity, with foreign-key assigned)
     * Lookup,              Xxx           XxxImp            XxxImp(a list for lookup)
     * AdornedLookup        Xxx           XxxImp            XxxRefImp
     *
     * Typical entryUiType
     */
    protected final CollectionTypesSetting collectionTypesSetting;

    public CollectionFieldMetadataFacet(Class entryType, Class entryTargetType,
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

    @Override
    public FieldFacetType getType() {
        return FieldFacetType.Collection;
    }

    public CollectionTypesSetting getCollectionTypesSetting(){
        return this.collectionTypesSetting;
    }
}
