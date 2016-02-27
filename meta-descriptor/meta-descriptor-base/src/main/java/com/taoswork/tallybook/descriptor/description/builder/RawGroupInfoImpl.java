package com.taoswork.tallybook.descriptor.description.builder;

import com.taoswork.tallybook.descriptor.description.descriptor.base.impl.NamedOrderedInfoImpl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
final class RawGroupInfoImpl
        extends NamedOrderedInfoImpl
        implements RawGroupInfo {

    /**
     * fields are not ordered
     */
    private final Set<String> fields = new HashSet<String>();

    @Override
    public void addField(String field) {
        fields.add(field);
    }

    @Override
    public void setFields(Collection<String> fields) {
        this.fields.clear();
        for (String field : fields) {
            this.fields.add(field);
        }
    }

    @Override
    public Collection<String> getFields() {
        return Collections.unmodifiableCollection(fields);
    }

    @Override
    public void clearFields() {
        fields.clear();
    }

}
