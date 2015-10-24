package com.taoswork.tallybook.dynamic.datameta.description.infos.handy;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.IFieldInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.EntityInfoType;
import com.taoswork.tallybook.dynamic.datameta.description.infos.IEntityInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.base.ITabInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.main.EntityInfo;
import com.taoswork.tallybook.general.extension.utils.CloneUtility;

import java.util.List;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/8/9.
 */
public class EntityFormInfo extends _BaseEntityHandyInfo implements IEntityInfo {
    public final String idField;
    public final String nameField;
    public final Map<String, IFieldInfo> fields;
    public final List<ITabInfo> tabs;

    public EntityFormInfo(EntityInfo entityInfo) {
        super(entityInfo);
        this.idField = entityInfo.getIdField();
        this.nameField = entityInfo.getNameField();
        this.fields = CloneUtility.makeClone(entityInfo.getFields());
        this.tabs = CloneUtility.makeClone(entityInfo.getTabs());
    }

    @Override
    public String getType() {
        return EntityInfoType.Form.getName();
    }


}
