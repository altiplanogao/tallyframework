package com.taoswork.tallybook.descriptor.description.builder;

import com.taoswork.tallybook.datadomain.base.presentation.FieldType;
import com.taoswork.tallybook.descriptor.description.descriptor.base.OrderedName;
import com.taoswork.tallybook.descriptor.description.descriptor.base.impl.NamedInfoImpl;
import com.taoswork.tallybook.descriptor.description.descriptor.field.IBasicFieldInfo;
import com.taoswork.tallybook.descriptor.description.descriptor.field.IFieldInfo;

import java.util.*;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
class RawEntityInfoImpl
        extends NamedInfoImpl
        implements RawEntityInfo {

    private final Map<String, IFieldInfo> fields = new HashMap<String, IFieldInfo>();
    private final Map<String, RawTabInfo> tabs = new HashMap<String, RawTabInfo>();
    private final Set<String> gridFields = new HashSet<String>();
    private final Set<Class> referencingEntries = new HashSet<Class>();
    private String idField;
    private String nameField;
    private String primarySearchField;
    private transient boolean dirty = false;

    //special fields
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

    //field
    @Override
    public void addField(IFieldInfo fieldInfo) {
        fields.put(fieldInfo.getName(), fieldInfo);
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

    //tab
    @Override
    public void addTab(RawTabInfo tabInfo) {
        tabs.put(tabInfo.getName(), tabInfo);
        dirty = true;
    }

    @Override
    public RawTabInfo getTab(String tabName) {
        return tabs.get(tabName);
    }

    @Override
    public Collection<? extends RawTabInfo> getTabs() {
        return Collections.unmodifiableCollection(tabs.values());
    }

    //grid
    @Override
    public void addGridField(String fieldName) {
        gridFields.add(fieldName);
        dirty = true;
    }

    @Override
    public Collection<String> getGridFields() {
        return Collections.unmodifiableCollection(gridFields);
    }

    //referencing

    @Override
    public void addReferencingEntries(Collection<Class> entries) {
        referencingEntries.addAll(entries);
    }

    @Override
    public Collection<Class> getReferencingEntries() {
        return Collections.unmodifiableCollection(referencingEntries);
    }

    //main
    @Override
    public void finishWriting() {
        if (dirty) {
            Map<OrderedName, IFieldInfo> fieldsOrdered = new TreeMap<OrderedName, IFieldInfo>(new OrderedName.OrderedComparator());
            for (Map.Entry<String, IFieldInfo> entry : fields.entrySet()) {
                IFieldInfo fieldInfo = entry.getValue();
                fieldsOrdered.put(new OrderedName(entry.getKey(), fieldInfo.getOrder()), fieldInfo);
            }
            IFieldInfo firstFieldInfo = null;
            for (Map.Entry<OrderedName, IFieldInfo> fieldInfoEntry : fieldsOrdered.entrySet()) {
                IFieldInfo fieldInfo = fieldInfoEntry.getValue();
                if (fieldInfo == null) {
                    continue;
                }
                if (fieldInfo instanceof IBasicFieldInfo) {
                    IBasicFieldInfo basicFieldInfo = (IBasicFieldInfo) fieldInfo;
                    if (firstFieldInfo == null) {
                        firstFieldInfo = fieldInfo;
                    }
                    if (FieldType.NAME.equals(basicFieldInfo.getFieldType())) {
                        this.nameField = fieldInfo.getName();
                    }
                    if (basicFieldInfo.getName().toLowerCase().equals("id")) {
                        if (this.idField == null) {
                            this.idField = fieldInfo.getName();
                        }
                    }
                    if (basicFieldInfo.getName().toLowerCase().equals("name")) {
                        if (this.nameField == null) {
                            this.nameField = fieldInfo.getName();
                        }
                    }
                }
            }
            if (null != this.nameField) {
                primarySearchField = this.nameField;
            } else if (null != firstFieldInfo) {
                primarySearchField = firstFieldInfo.getName();
            } else {
                primarySearchField = null;
            }
        }
        dirty = false;
    }

}
