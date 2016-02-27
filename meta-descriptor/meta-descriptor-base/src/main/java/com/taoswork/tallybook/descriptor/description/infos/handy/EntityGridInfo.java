package com.taoswork.tallybook.descriptor.description.infos.handy;

import com.taoswork.tallybook.descriptor.description.descriptor.field.IFieldInfo;
import com.taoswork.tallybook.descriptor.description.infos.EntityInfoType;
import com.taoswork.tallybook.descriptor.description.infos.IEntityInfo;
import com.taoswork.tallybook.descriptor.description.infos.main.EntityInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/8/9.
 */
public final class EntityGridInfo extends _BaseEntityHandyInfo implements IEntityInfo {
    private final String idField;
    private final String nameField;
    public final String primarySearchField;
    public final List<IFieldInfo> fields;

    public EntityGridInfo(EntityInfo entityInfo) {
        super(entityInfo);
        idField = entityInfo.getIdField();
        nameField = entityInfo.getNameField();
        primarySearchField = entityInfo.getPrimarySearchField();
        fields = new ArrayList<IFieldInfo>();
        Map<String, IFieldInfo> fieldMap = entityInfo.getFields();
        for (String gridField : entityInfo.getGridFields()) {
            IFieldInfo field = fieldMap.get(gridField);
            fields.add(field);
        }
    }

    @Override
    public String getInfoType() {
        return EntityInfoType.Grid.getType();
    }

    @Override
    public String getIdField() {
        return idField;
    }

    @Override
    public String getNameField() {
        return nameField;
    }

    @Override
    public Map<String, IEntityInfo> getReferencing() {
        return null;
    }
}
