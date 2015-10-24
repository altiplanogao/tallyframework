package com.taoswork.tallybook.dynamic.datameta.description.builder;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.OrderedName;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.impl.NamedInfoImpl;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.IFieldInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.basic.IFieldInfoRW;

import java.util.*;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
class RawEntityInsightImpl
    extends NamedInfoImpl
    implements RawEntityInsightRW {

    private final Map<String, IFieldInfo> fields = new HashMap<String, IFieldInfo>();
    private final Map<String, RawTabInsightRW> tabs = new HashMap<String, RawTabInsightRW>();
    private final Set<String> gridFields = new HashSet<String>();
    private String idField;
    private String nameField;
    private String primarySearchField;
    private transient boolean dirty = false;

    @Override
    public void addField(IFieldInfoRW fieldInfo) {
        fields.put(fieldInfo.getName(), fieldInfo);
        dirty = true;
    }

    @Override
    public void addTab(RawTabInsightRW tabInfo) {
        tabs.put(tabInfo.getName(), tabInfo);
        dirty = true;
    }

    @Override
    public IFieldInfo getField(String fieldName) {
        return fields.get(fieldName);
    }

    @Override
    public Map<String, IFieldInfo> getFields() {
        return Collections.unmodifiableMap(fields);
    }

    @Override
    public RawTabInsight getTab(String tabName) {
        return tabs.get(tabName);
    }

    @Override
    public Collection<? extends RawTabInsight> getTabs() {
        return Collections.unmodifiableCollection(tabs.values());
    }

    @Override
    public IFieldInfoRW getFieldRW(String fieldName) {
        IFieldInfoRW fieldInfoRW = (IFieldInfoRW) fields.get(fieldName);
        dirty = true;
        return fieldInfoRW;
    }

    @Override
    public RawTabInsightRW getTabRW(String tabName) {
        RawTabInsightRW tabInfoRW = tabs.get(tabName);
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
    public RawEntityInsightImpl setIdField(String idField) {
        this.idField = idField;
        return this;
    }

    @Override
    public String getNameField() {
        return nameField;
    }

    @Override
    public RawEntityInsightRW setNameField(String nameField) {
        this.nameField = nameField;
        return this;
    }

    @Override
    public String getPrimarySearchField() {
        return primarySearchField;
    }

    @Override
    public void finishWriting() {
        if (dirty) {
            Map<OrderedName, IFieldInfo> fieldsOrdered = new TreeMap<OrderedName, IFieldInfo>(new OrderedName.OrderedComparator());
            for (Map.Entry<String, IFieldInfo> entry : fields.entrySet()) {
                IFieldInfo IFieldInfo = entry.getValue();
                fieldsOrdered.put(new OrderedName(entry.getKey(), IFieldInfo.getOrder()), IFieldInfo);
            }
            {
                IFieldInfo firstIFieldInfo = null;
                for (Map.Entry<OrderedName, IFieldInfo> fieldInfoEntry : fieldsOrdered.entrySet()) {
                    IFieldInfo IFieldInfo = fieldInfoEntry.getValue();
                    if (IFieldInfo == null) {
                        continue;
                    }
                    if (firstIFieldInfo == null) {
                        firstIFieldInfo = IFieldInfo;
                    }
                    if (IFieldInfo.isIdField()) {
                        if (this.idField == null) {
                            this.idField = IFieldInfo.getName();
                        }
                    }
                    if (IFieldInfo.isNameField() || (IFieldInfo.getName().toLowerCase().equals("name"))) {
                        if (this.nameField == null) {
                            this.nameField = IFieldInfo.getName();
                        }
                    }
                }
                if (null != this.nameField) {
                    primarySearchField = this.nameField;
                } else if (null != firstIFieldInfo) {
                    primarySearchField = firstIFieldInfo.getName();
                } else {
                    primarySearchField = null;
                }
            }
        }
        dirty = false;
    }

}
