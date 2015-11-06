package com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typedcollection;

import com.taoswork.tallybook.dynamic.datameta.metadata.EntryTypeUnion;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.BaseCollectionFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.FieldMetadataIntermediate;

/**
 * Created by Gao Yuan on 2015/11/6.
 */
public abstract class CollectionLikeFieldMetadata extends BaseCollectionFieldMetadata {
    public CollectionLikeFieldMetadata(FieldMetadataIntermediate intermediate) {
        super(intermediate);
    }

    public abstract EntryTypeUnion getEntryTypeUnion();
}
