package com.taoswork.tallybook.dynamic.datameta.description.infos.main.impl;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.impl.NamedInfoImpl;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.FieldInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.EntityInfoType;
import com.taoswork.tallybook.dynamic.datameta.description.infos.base.ITabInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.main.EntityInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/8/9.
 */
public class EntityInfoImpl
    extends NamedInfoImpl
    implements EntityInfo, EntityInfoRW {

    private boolean containsHierarchy = true;
    private Class entityType = null;

    private String idField;
    private String nameField;
    private String primarySearchField;

    private Map<String, FieldInfo> fields = new HashMap<String, FieldInfo>();
    private List<ITabInfo> tabs = new ArrayList<ITabInfo>();

    private List<String> gridFields = new ArrayList<String>();

    public EntityInfoImpl(Class entityType, boolean containsHierarchy, List<ITabInfo> tabs, Map<String, FieldInfo> fields) {
        this.containsHierarchy = containsHierarchy;
        this.entityType = entityType;
        this.tabs = tabs;
        this.fields = new HashMap<String, FieldInfo>();
        for (Map.Entry<String, FieldInfo> field : fields.entrySet()) {
            this.fields.put(field.getKey(), field.getValue());
        }
    }

    @Override
    public Map<String, FieldInfo> getFields() {
        return fields;
    }

    @Override
    public void setFields(Map<String, FieldInfo> fields) {
        this.fields = fields;
    }

    @Override
    public List<ITabInfo> getTabs() {
        return tabs;
    }

    @Override
    public void setTabs(List<ITabInfo> tabs) {
        this.tabs = tabs;
    }

    @Override
    public String getIdField() {
        return idField;
    }

    @Override
    public void setIdField(String idField) {
        this.idField = idField;
    }

    @Override
    public String getNameField() {
        return nameField;
    }

    @Override
    public void setNameField(String nameField) {
        this.nameField = nameField;
    }

    @Override
    public String getPrimarySearchField() {
        return primarySearchField;
    }

    @Override
    public void setPrimarySearchField(String primarySearchField) {
        this.primarySearchField = primarySearchField;
    }

    @Override
    public List<String> getGridFields() {
        return gridFields;
    }

    @Override
    public void setGridFields(List<String> gridFields) {
        this.gridFields = gridFields;
    }

    @Override
    public FieldInfo getField(String fieldName) {
        return this.fields.getOrDefault(fieldName, null);
    }

    @Override
    public String getType() {
        return EntityInfoType.Main.getName();
    }

    @Override
    public boolean isContainsHierarchy() {
        return containsHierarchy;
    }

    @Override
    public String getEntityType() {
        if (entityType != null)
            return entityType.getName();
        return null;
    }
}
