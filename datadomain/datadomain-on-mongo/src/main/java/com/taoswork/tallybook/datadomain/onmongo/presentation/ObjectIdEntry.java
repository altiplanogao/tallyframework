package com.taoswork.tallybook.datadomain.onmongo.presentation;

import com.taoswork.tallybook.datadomain.base.presentation.typedcollection.entry.IPrimitiveEntry;
import org.bson.types.ObjectId;

/**
 * Created by Gao Yuan on 2016/2/23.
 */
public class ObjectIdEntry implements IPrimitiveEntry<ObjectId> {
    private ObjectId id;

    @Override
    public ObjectId getValue() {
        return id;
    }

    @Override
    public void setValue(ObjectId val) {
        this.id = val;
    }
}
