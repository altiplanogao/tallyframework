package com.taoswork.tallybook.dynamic.datameta.description.builder;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.impl.NamedInfoRW;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.IFieldInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.base.IFieldInfoRW;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
interface RawEntityInsightRW extends NamedInfoRW, RawEntityInsight {
    void addField(IFieldInfo fieldInfo);

    IFieldInfoRW getFieldRW(String fieldName);

    void addTab(RawTabInsightRW tabInfo);

    RawTabInsightRW getTabRW(String tabName);

    void addGridField(String fieldName);

    RawEntityInsightRW setIdField(String idField);

    RawEntityInsightRW setNameField(String nameField);

    void finishWriting();

}
