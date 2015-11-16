package com.taoswork.tallybook.dynamic.datameta.metadata.facet.collections;

import com.taoswork.tallybook.dynamic.datameta.metadata.CollectionTypesUnion;
import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.IFieldMetadataFacet;
import com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection.CollectionModel;
import com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection.entry.ISimpleEntryDelegate;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
public class CollectionFieldMetadataFacet implements IFieldMetadataFacet {

    /**
     * the entry-type used for user editing, assume we have collection field:
     * @Relation(target=XxxImp.class)
     * @PresentationCollection(collectionModel=model, joinEntity=XxxRefImp.class, simpleEntryDelegate= simpleEntryDelegate.class)
     * List<Xxx> xxxs;
     *
     * typical relationships are:
     * Model:               EntryType:    EntryTargetType:  EntryUiType
     * Primitive,           Xxx           XxxImp            simpleEntryDelegate
     * Embeddable,          Xxx           XxxImp            XxxImp(new embeddable object)
     * Entity,              Xxx           XxxImp            XxxImp(new entity, with foreign-key assigned)
     * Lookup,              Xxx           XxxImp            XxxImp(a list for lookup)
     * AdornedLookup        Xxx           XxxImp            XxxRefImp
     *
     * Typical entryUiType
     */
    protected final CollectionTypesUnion collectionTypesUnion;

    public CollectionFieldMetadataFacet(Class entryType, Class entryTargetType,
                                        Class<? extends ISimpleEntryDelegate> entrySimpleDelegateType,
                                        Class entryJoinEntityType,
                                        CollectionModel collectionModel,
                                        Class collectionClass) {
        this.collectionTypesUnion = new CollectionTypesUnion(entryType, entryTargetType,
            entrySimpleDelegateType,
             entryJoinEntityType,
             collectionModel,
             collectionClass);
    }

    @Override
    public FieldFacetType getType() {
        return FieldFacetType.Collection;
    }

    public CollectionTypesUnion getCollectionTypesUnion(){
        return this.collectionTypesUnion;
    }
}
