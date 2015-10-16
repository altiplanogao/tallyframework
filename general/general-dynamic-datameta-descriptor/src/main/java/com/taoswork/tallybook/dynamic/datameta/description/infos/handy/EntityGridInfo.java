package com.taoswork.tallybook.dynamic.datameta.description.infos.handy;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.FieldInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.EntityInfoType;
import com.taoswork.tallybook.dynamic.datameta.description.infos.IEntityInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.main.EntityInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/8/9.
 */
public class EntityGridInfo extends _BaseEntityHandyInfo implements IEntityInfo {
    public final String idField;
    public final String nameField;
    public final String primarySearchField;
    public final List<FieldInfo> fields;

    public EntityGridInfo(EntityInfo entityInfo) {
        super(entityInfo);
        idField = entityInfo.getIdField();
        nameField = entityInfo.getNameField();
        primarySearchField = entityInfo.getPrimarySearchField();
        fields = new ArrayList<FieldInfo>();
        Map<String, FieldInfo> fieldMap = entityInfo.getFields();
        for (String gridField : entityInfo.getGridFields()) {
            FieldInfo field = fieldMap.get(gridField);
            fields.add(field);
        }
    }

    @Override
    public String getType() {
        return EntityInfoType.Grid.getName();
    }

}
