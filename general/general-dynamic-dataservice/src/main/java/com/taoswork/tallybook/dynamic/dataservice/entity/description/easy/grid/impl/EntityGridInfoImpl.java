package com.taoswork.tallybook.dynamic.dataservice.entity.description.easy.grid.impl;

import com.taoswork.tallybook.dynamic.dataservice.entity.description.descriptor.base.impl.NamedInfoImpl;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.descriptor.field.FieldInfo;
import com.taoswork.tallybook.general.extension.collections.CollectionUtility;
import com.taoswork.tallybook.general.extension.utils.TPredicate;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public class EntityGridInfoImpl extends NamedInfoImpl
    implements EntityGridInfoRW {

    private String primarySearchField;
    private final Set<FieldInfo> fields = new TreeSet<FieldInfo>(new InfoOrderedComparator());

    @Override
    public void addField(FieldInfo fieldInfo){
        fields.add(fieldInfo);
    }

    @Override
    public Collection<FieldInfo> getFields() {
        return Collections.unmodifiableCollection(fields);
    }

    @Override
    public EntityGridInfoRW setPrimarySearchField(String primarySearchField) {
        this.primarySearchField = primarySearchField;
        return this;
    }

    @Override
    public String getPrimarySearchField() {
        return primarySearchField;
    }

    @Override
    public String fetchPrimarySearchFieldFriendlyName() {
        if(null != primarySearchField){
            FieldInfo fieldInfo = CollectionUtility.find(fields, new TPredicate<FieldInfo>() {
                @Override
                public boolean evaluate(FieldInfo notNullObj) {
                    return notNullObj.getName().equals(primarySearchField);
                }
            });
            return  (fieldInfo == null) ? null : fieldInfo.getFriendlyName();
        }
        return null;
    }
}
