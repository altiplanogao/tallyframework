package com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.typedcollection;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.actions.CollectionActions;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Gao Yuan on 2015/12/9.
 */
public class PrimitiveCollectionFieldInfo extends  _CollectionFieldInfo {
    /**
     * Potential supported actions:
     * create
     *
     */
    private final static Collection<String> supportedActions;

    static {
        Set<String> tempActions = new HashSet<String>();
        tempActions.add(CollectionActions.CREATE);
        tempActions.add(CollectionActions.READ);
//        tempActions.add(CollectionActions.UPDATE);
        tempActions.add(CollectionActions.DELETE);
//        tempActions.add(CollectionActions.QUERY);
//        tempActions.add(CollectionActions.REORDER);

        supportedActions = Collections.unmodifiableCollection(tempActions);
    }

    public PrimitiveCollectionFieldInfo(String name, String friendlyName,
                                        boolean editable, String instanceType) {
        super(name, friendlyName, editable, instanceType);
    }

    @Override
    public String getEntryType() {
        return "primitive";
    }

    public Collection<String> getActions(){
        return supportedActions;
    }

}
