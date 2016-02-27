package com.taoswork.tallybook.descriptor.description.descriptor.field.base;

import com.taoswork.tallybook.datadomain.base.presentation.FieldType;
import com.taoswork.tallybook.descriptor.description.descriptor.base.impl.NamedOrderedInfoRW;
import com.taoswork.tallybook.descriptor.description.descriptor.field.IFieldInfo;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public interface IFieldInfoRW extends NamedOrderedInfoRW, IFieldInfo {
    void setVisibility(int visibility);

    void setRequired(boolean required);

    void setFieldType(FieldType fieldType);

    void setIgnored(boolean ignored);
}
