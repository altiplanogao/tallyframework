package com.taoswork.tallybook.dynamic.datameta.description.builder;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.impl.NamedInfoRW;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.impl.FieldInfoRW;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
interface RawEntityInsightRW extends NamedInfoRW, RawEntityInsight {
    void addField(FieldInfoRW fieldInfo);

    FieldInfoRW getFieldRW(String fieldName);

    void addTab(RawTabInsightRW tabInfo);

    RawTabInsightRW getTabRW(String tabName);

    void addGridField(String fieldName);

    RawEntityInsightRW setIdField(String idField);

    RawEntityInsightRW setNameField(String nameField);

    void finishWriting();

}
