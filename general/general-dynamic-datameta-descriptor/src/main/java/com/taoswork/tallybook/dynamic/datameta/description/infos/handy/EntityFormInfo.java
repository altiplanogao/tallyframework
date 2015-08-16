package com.taoswork.tallybook.dynamic.datameta.description.infos.handy;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.impl.NamedInfoImpl;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.FieldInfo;
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
public class EntityFormInfo extends NamedInfoImpl implements IEntityInfo {
    @Override
    public String getType() {
        return EntityInfoType.Form.getName();
    }

    public final String idField;
    public final String nameField;
//    public final String primarySearchField;

    public final Map<String, FieldInfo> fields;
    public final List<ITabInfo> tabs;

    public EntityFormInfo(EntityInfo entityInfo){
        this.copyNamedInfo(entityInfo);
        this.idField = entityInfo.getIdField();
        this.nameField = entityInfo.getNameField();
//        this.primarySearchField = entityInfo.getPrimarySearchField();
        this.fields = CloneUtility.makeClone(entityInfo.getFields());
        this.tabs = CloneUtility.makeClone(entityInfo.getTabs());
    }


}
