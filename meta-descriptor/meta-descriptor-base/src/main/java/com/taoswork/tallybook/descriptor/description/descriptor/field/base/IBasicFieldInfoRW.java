package com.taoswork.tallybook.descriptor.description.descriptor.field.base;

import com.taoswork.tallybook.descriptor.description.descriptor.field.IBasicFieldInfo;
import com.taoswork.tallybook.descriptor.description.descriptor.field.IFieldInfo;

/**
 * Created by Gao Yuan on 2015/10/24.
 */
public interface IBasicFieldInfoRW extends IBasicFieldInfo {

    IFieldInfo setSupportSort(boolean supportSort);

    IFieldInfo setSupportFilter(boolean supportFilter);

}
