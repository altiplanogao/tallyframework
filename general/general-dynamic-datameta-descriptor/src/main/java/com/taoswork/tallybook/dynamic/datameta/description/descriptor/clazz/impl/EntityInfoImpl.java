package com.taoswork.tallybook.dynamic.datameta.description.descriptor.clazz.impl;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.OrderedName;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.impl.NamedInfoImpl;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.FieldInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.impl.FieldInfoRW;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.tab.TabInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.tab.impl.TabInfoRW;

import java.util.*;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public class EntityInfoImpl
        extends NamedInfoImpl
        implements EntityInfoRW {

    private final Map<String, FieldInfoRW> fields = new HashMap<String, FieldInfoRW>();
    private final Map<String, TabInfoRW> tabs = new HashMap<String, TabInfoRW>();
    private final Set<String> gridFields = new HashSet<String>();
    private String idField;
    private String primarySearchField;

    private transient boolean dirty = false;

    @Override
    public void addField(FieldInfoRW fieldInfo) {
        fields.put(fieldInfo.getName(), fieldInfo);
        dirty = true;
    }

    @Override
    public void addTab(TabInfoRW tabInfo) {
        tabs.put(tabInfo.getName(), tabInfo);
        dirty = true;
    }

    @Override
    public FieldInfo getField(String fieldName) {
        return fields.getOrDefault(fieldName, null);
    }

    @Override
    public Collection<? extends FieldInfo> getFields() {
        return Collections.unmodifiableCollection(fields.values());
    }

    @Override
    public TabInfo getTab(String tabName) {
        return tabs.getOrDefault(tabName, null);
    }

    @Override
    public Collection<? extends TabInfo> getTabs() {
        return Collections.unmodifiableCollection(tabs.values());
    }

    @Override
    public FieldInfoRW getFieldRW(String fieldName) {
        FieldInfoRW fieldInfoRW = fields.getOrDefault(fieldName, null);
        dirty = true;
        return fieldInfoRW;
    }

    @Override
    public TabInfoRW getTabRW(String tabName) {
        TabInfoRW tabInfoRW =  tabs.getOrDefault(tabName, null);
        dirty = true;
        return tabInfoRW;
    }

    @Override
    public void addGridField(String fieldName) {
        gridFields.add(fieldName);
        dirty = true;
    }

    @Override
    public Collection<String> getGridFields() {
        return Collections.unmodifiableCollection(gridFields);
    }

    @Override
    public String getIdField() {
        return idField;
    }

    @Override
    public EntityInfoImpl setIdField(String idField) {
        this.idField = idField;
        return this;
    }

    @Override
    public void finishWriting() {
        if(dirty){
            Map<OrderedName, FieldInfo> gridNameOrdered = new TreeMap<OrderedName, FieldInfo>(new OrderedName.OrderedComparator());
            for (String fieldName : gridFields){
                FieldInfo fieldInfo = fields.getOrDefault(fieldName, null);
                if(fieldInfo != null){
                    gridNameOrdered.put(new OrderedName(fieldName, fieldInfo.getOrder()), fieldInfo);
                }
            }
            {
                FieldInfo nameFieldInfo = null;
                FieldInfo firstFieldInfo = null;
                for (Map.Entry<OrderedName, FieldInfo> fieldInfoEntry : gridNameOrdered.entrySet()) {
                    FieldInfo fieldInfo = fieldInfoEntry.getValue();
                    if (fieldInfo == null) {
                        continue;
                    }
                    if (firstFieldInfo == null) {
                        firstFieldInfo = fieldInfo;
                    }
                    if (fieldInfo.isNameField()) {
                        nameFieldInfo = fieldInfo;
                        break;
                    }
                    if (fieldInfo.getName().toLowerCase().equals("name")) {
                        nameFieldInfo = fieldInfo;
                    }
                }
                if (null != nameFieldInfo) {
                    primarySearchField = nameFieldInfo.getName();
                }else if(null != firstFieldInfo){
                    primarySearchField = firstFieldInfo.getName();
                }else {
                    primarySearchField = null;
                }
            }
        }
        dirty = false;
    }

    @Override
    public String getPrimarySearchField() {
        return primarySearchField;
    }
}
