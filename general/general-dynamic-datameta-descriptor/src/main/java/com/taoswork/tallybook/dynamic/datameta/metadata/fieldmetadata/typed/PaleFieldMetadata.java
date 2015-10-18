package com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typed;

import com.taoswork.tallybook.dynamic.datameta.metadata.IFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.BaseNonCollectionFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.FieldMetadataIntermediate;

public class PaleFieldMetadata extends BaseNonCollectionFieldMetadata implements IFieldMetadata {
    public PaleFieldMetadata(FieldMetadataIntermediate intermediate) {
        super(intermediate);
    }

    @Override
    public boolean isPrimitiveField() {
        return true;
    }
}
