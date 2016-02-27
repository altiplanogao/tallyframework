package com.taoswork.tallybook.descriptor.metadata.fieldmetadata;

import com.taoswork.tallybook.descriptor.metadata.IFieldMeta;

import java.io.Serializable;

/**
 * Created by Gao Yuan on 2015/5/24.
 */
public interface IFieldMetaSeed extends Serializable {
    IFieldMeta makeFieldMeta(BasicFieldMetaObject bfmo);
}
