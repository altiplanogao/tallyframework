package com.taoswork.tallybook.descriptor.description.descriptor.group;

import com.taoswork.tallybook.descriptor.description.descriptor.base.impl.NamedOrderedInfoImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/8/9.
 */
public class GroupInfoImpl extends NamedOrderedInfoImpl implements IGroupInfo {
    /**
     * The fields are ordered
     */
    private final List<String> fields;

    public GroupInfoImpl(List<String> fields) {
        this.fields = new ArrayList<String>(fields);
    }

    @Override
    public List<String> getFields() {
        return fields;
    }
}
