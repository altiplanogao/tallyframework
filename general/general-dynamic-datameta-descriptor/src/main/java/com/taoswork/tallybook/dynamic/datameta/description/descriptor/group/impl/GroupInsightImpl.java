package com.taoswork.tallybook.dynamic.datameta.description.descriptor.group.impl;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.impl.NamedOrderedInfoImpl;

import java.util.*;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public class GroupInsightImpl
        extends NamedOrderedInfoImpl
        implements GroupInsightRW {

    private final Set<String> fields = new HashSet<String>();

    @Override
    public void addField(String field) {
        fields.add(field);
    }

    @Override
    public void clearFields() {
        fields.clear();
    }

    @Override
    public void setFields(Collection<String> fields) {
        this.fields.clear();
        for(String field : fields){
            this.fields.add(field);
        }
    }

    @Override
    public Collection<String> getFields() {
        return Collections.unmodifiableCollection(fields);
    }

}
