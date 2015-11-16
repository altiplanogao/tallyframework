package com.taoswork.tallybook.dynamic.datameta.description.infos.main.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.impl.NamedInfoImpl;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.IFieldInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.tab.ITabInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.EntityInfoType;
import com.taoswork.tallybook.dynamic.datameta.description.infos.IEntityInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.main.EntityInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.*;

/**
 * Created by Gao Yuan on 2015/8/9.
 */
public class EntityInfoImpl
    extends NamedInfoImpl
    implements EntityInfo, EntityInfoRW {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntityInfoImpl.class);

    private boolean containsHierarchy = true;
    private Class entityType = null;

    private String idField;
    private String nameField;
    private String primarySearchField;

    private Map<String, IFieldInfo> fields = new HashMap<String, IFieldInfo>();
    private List<ITabInfo> tabs = new ArrayList<ITabInfo>();

    private List<String> gridFields = new ArrayList<String>();
    private final Map<String, EntityInfo> referencingEntryEntityInfos = new HashMap<String, EntityInfo>();
    private transient Map<String, IEntityInfo> entryInfos;

    public EntityInfoImpl(Class entityType, boolean containsHierarchy, List<ITabInfo> tabs, Map<String, IFieldInfo> fields) {
        this.containsHierarchy = containsHierarchy;
        this.entityType = entityType;
        this.tabs = tabs;
        this.fields = new HashMap<String, IFieldInfo>();
        for (Map.Entry<String, IFieldInfo> field : fields.entrySet()) {
            this.fields.put(field.getKey(), field.getValue());
        }
    }

    @Override
    public Map<String, IFieldInfo> getFields() {
        return fields;
    }

    @Override
    public void setFields(Map<String, IFieldInfo> fields) {
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
    public IFieldInfo getField(String fieldName) {
        return this.fields.get(fieldName);
    }

    @Override
    public String getType() {
        return EntityInfoType.Main.getType();
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

    @Override
    public void addReferencingInfo(String entryName, EntityInfo entityInfo){
        referencingEntryEntityInfos.put(entryName, entityInfo);
    }

    @Override
    public Map<String, EntityInfo> getReferencingInfos() {
        if(referencingEntryEntityInfos.isEmpty())
            return null;
        return Collections.unmodifiableMap(referencingEntryEntityInfos);
    }

    @Override
    @JsonIgnore
    public Map<String, IEntityInfo> getReferencing() {
        if(entryInfos == null){
            synchronized (this){
                if(entryInfos != null){
                    return entryInfos;
                }
                Map<String, IEntityInfo> tempInfos = getReferencingInfosAsType(EntityInfoType.Grid);
                entryInfos = tempInfos;
            }
        }
        return entryInfos;
    }

    @Override
    public Map<String, IEntityInfo> getReferencingInfosAsType(EntityInfoType entityInfoType) {
        Map<String, IEntityInfo> iEntityInfos = new HashMap<String, IEntityInfo>();
        for (Map.Entry<String, EntityInfo> entry : referencingEntryEntityInfos.entrySet()){
            String key = entry.getKey();
            EntityInfo val = entry.getValue();
            IEntityInfo ival = convert(val, entityInfoType);
            iEntityInfos.put(key, ival);
        }
        return iEntityInfos;
    }

    private static IEntityInfo convert(EntityInfo entityInfo, EntityInfoType type) {
        Class<? extends IEntityInfo> cls = type.getEntityInfoClass();
        if (cls != null) {
            try {
                Constructor cons = cls.getConstructor(new Class[]{EntityInfo.class});
                IEntityInfo cinfo = (IEntityInfo) cons.newInstance(entityInfo);
                return cinfo;
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        }
        return null;
    }
}
