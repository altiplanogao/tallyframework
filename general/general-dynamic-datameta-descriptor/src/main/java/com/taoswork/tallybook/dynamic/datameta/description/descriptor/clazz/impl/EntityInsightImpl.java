package com.taoswork.tallybook.dynamic.datameta.description.descriptor.clazz.impl;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.OrderedName;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.impl.NamedInfoImpl;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.FieldInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.impl.FieldInfoRW;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.tab.TabInsight;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.tab.impl.TabInsightRW;

import java.util.*;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public class EntityInsightImpl
        extends NamedInfoImpl
        implements EntityInsightRW {

    private String idField;
    private String nameField;
    private String primarySearchField;

    private final Map<String, FieldInfo> fields = new HashMap<String, FieldInfo>();
    private final Map<String, TabInsightRW> tabs = new HashMap<String, TabInsightRW>();
    private final Set<String> gridFields = new HashSet<String>();

    private transient boolean dirty = false;

    @Override
    public void addField(FieldInfoRW fieldInfo) {
        fields.put(fieldInfo.getName(), fieldInfo);
        dirty = true;
    }

    @Override
    public void addTab(TabInsightRW tabInfo) {
        tabs.put(tabInfo.getName(), tabInfo);
        dirty = true;
    }

    @Override
    public FieldInfo getField(String fieldName) {
        return fields.getOrDefault(fieldName, null);
    }

    @Override
    public Map<String, FieldInfo> getFields() {
        return Collections.unmodifiableMap(fields);
    }

    @Override
    public TabInsight getTab(String tabName) {
        return tabs.getOrDefault(tabName, null);
    }

    @Override
    public Collection<? extends TabInsight> getTabs() {
        return Collections.unmodifiableCollection(tabs.values());
    }

    @Override
    public FieldInfoRW getFieldRW(String fieldName) {
        FieldInfoRW fieldInfoRW = (FieldInfoRW)fields.getOrDefault(fieldName, null);
        dirty = true;
        return fieldInfoRW;
    }

    @Override
    public TabInsightRW getTabRW(String tabName) {
        TabInsightRW tabInfoRW =  tabs.getOrDefault(tabName, null);
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
    public EntityInsightImpl setIdField(String idField) {
        this.idField = idField;
        return this;
    }

    @Override
    public String getNameField() {
        return nameField;
    }

    @Override
    public EntityInsightRW setNameField(String nameField) {
        this.nameField = nameField;
        return this;
    }

    @Override
    public String getPrimarySearchField() {
        return primarySearchField;
    }

    @Override
    public void finishWriting() {
        if(dirty){
            Map<OrderedName, FieldInfo> fieldsOrdered = new TreeMap<OrderedName, FieldInfo>(new OrderedName.OrderedComparator());
            for (Map.Entry<String, FieldInfo> entry : fields.entrySet()){
                FieldInfo fieldInfo = entry.getValue();
                fieldsOrdered.put(new OrderedName(entry.getKey(), fieldInfo.getOrder()), fieldInfo);
            }
            {
                FieldInfo firstFieldInfo = null;
                for (Map.Entry<OrderedName, FieldInfo> fieldInfoEntry : fieldsOrdered.entrySet()) {
                    FieldInfo fieldInfo = fieldInfoEntry.getValue();
                    if (fieldInfo == null) {
                        continue;
                    }
                    if (firstFieldInfo == null) {
                        firstFieldInfo = fieldInfo;
                    }
                    if(fieldInfo.isIdField()){
                        if(this.idField == null){
                            this.idField = fieldInfo.getName();
                        }
                    }
                    if (fieldInfo.isNameField() || (fieldInfo.getName().toLowerCase().equals("name"))) {
                        if(this.nameField == null){
                            this.nameField = fieldInfo.getName();
                        }
                    }
                }
                if (null != this.nameField) {
                    primarySearchField = this.nameField;
                }else if(null != firstFieldInfo){
                    primarySearchField = firstFieldInfo.getName();
                }else {
                    primarySearchField = null;
                }
            }
        }
        dirty = false;
    }

}
