package com.taoswork.tallybook.dynamic.datameta.metadata.facet.collections;

import com.taoswork.tallybook.dynamic.datameta.metadata.EntryTypeUnion;
import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.IFieldMetadataFacet;
import com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection.entry.ISimpleEntryDelegate;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
public class MapFieldMetadataFacet implements IFieldMetadataFacet {
    private final Class _keyType;
    private final Class _valueType;

    private final EntryTypeUnion keyType;
    private final EntryTypeUnion valueType;

    private final Class mapType;

    public MapFieldMetadataFacet(Class mapType,
                                 Class keyType,Class<? extends ISimpleEntryDelegate> simpleKeyEntryDelegate,
                                 Class valueType,Class<? extends ISimpleEntryDelegate> simpleValueEntryDelegate) {
        super();
        this.mapType = mapType;

        this._keyType = keyType;
        this._valueType = valueType;

        this.keyType = new EntryTypeUnion(keyType, simpleKeyEntryDelegate);
        this.valueType = new EntryTypeUnion(valueType, simpleValueEntryDelegate);
    }

    @Override
    public FieldFacetType getType() {
        return FieldFacetType.Map;
    }

    public EntryTypeUnion getKeyType() {
        return this.keyType;
    }

    public EntryTypeUnion getValueType() {
        return this.valueType;
    }

    public Class getMapType() {
        return mapType;
    }
}
