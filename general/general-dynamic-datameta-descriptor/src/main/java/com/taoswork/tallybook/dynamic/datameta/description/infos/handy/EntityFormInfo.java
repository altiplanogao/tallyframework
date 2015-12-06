package com.taoswork.tallybook.dynamic.datameta.description.infos.handy;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.IFieldInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.tab.ITabInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.EntityInfoType;
import com.taoswork.tallybook.dynamic.datameta.description.infos.IEntityInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.main.EntityInfo;
import com.taoswork.tallybook.general.extension.utils.CloneUtility;

import java.util.List;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/8/9.
 */
public class EntityFormInfo extends _BaseEntityHandyInfo implements IEntityInfo {
    private final String idField;
    private final String nameField;
    public final Map<String, IFieldInfo> fields;
    public final List<ITabInfo> tabs;
    private final Map<String, IEntityInfo> referencingEntryInfos;

    public EntityFormInfo(EntityInfo entityInfo) {
        super(entityInfo);
        this.idField = entityInfo.getIdField();
        this.nameField = entityInfo.getNameField();
        this.fields = CloneUtility.makeClone(entityInfo.getFields());
        this.tabs = CloneUtility.makeClone(entityInfo.getTabs());
        this.referencingEntryInfos = CloneUtility.makeClone(entityInfo.getReferencingInfosAsType(EntityInfoType.Grid));
    }

    @Override
    public String getType() {
        return EntityInfoType.Form.getType();
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
        return referencingEntryInfos;
    }

}
