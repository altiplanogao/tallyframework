package com.taoswork.tallybook.dynamic.datameta.description.infos.handy;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.IFieldInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.EntityInfoType;
import com.taoswork.tallybook.dynamic.datameta.description.infos.IEntityInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.tab.ITabInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.main.EntityInfo;
import com.taoswork.tallybook.general.extension.utils.CloneUtility;

import java.util.List;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/8/9.
 */
public class EntityFullInfo extends _BaseEntityHandyInfo implements IEntityInfo {
    public final String idField;
    public final String nameField;
    public final String primarySearchField;
    public final Map<String, IFieldInfo> fields;
    public final List<ITabInfo> tabs;
    public final List<String> gridFields;
    private final Map<String, IEntityInfo> referencingEntryInfos;

    public EntityFullInfo(EntityInfo entityInfo) {
        super(entityInfo);
        this.idField = entityInfo.getIdField();
        this.nameField = entityInfo.getNameField();
        this.primarySearchField = entityInfo.getPrimarySearchField();
        this.fields = CloneUtility.makeClone(entityInfo.getFields());
        this.tabs = CloneUtility.makeClone(entityInfo.getTabs());
        this.gridFields = CloneUtility.makeClone(entityInfo.getGridFields());
        this.referencingEntryInfos = CloneUtility.makeClone(entityInfo.getReferencingInfosAsType(EntityInfoType.Grid));
    }

    @Override
    public String getType() {
        return EntityInfoType.Full.getType();
    }

    @Override
    public Map<String, IEntityInfo> getEntryInfos() {
        return referencingEntryInfos;
    }
}
