package com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.typedcollection;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.actions.CollectionActions;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.actions.CollectionRelativeUrlBuilder;

import java.util.*;

/**
 * Created by Gao Yuan on 2015/11/15.
 */

public class BasicCollectionFieldInfo extends  _CollectionFieldInfo {
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
        tempActions.add(CollectionActions.UPDATE);
        tempActions.add(CollectionActions.DELETE);
        tempActions.add(CollectionActions.QUERY);
//        tempActions.add(CollectionActions.REORDER);

        supportedActions = Collections.unmodifiableCollection(tempActions);
    }

    private final Map<String, String> actionRefUrls;

    /**
     *
     * @param name
     * @param friendlyName
     * @param editable
     * @param instanceType
     */
    public BasicCollectionFieldInfo(String name, String friendlyName, boolean editable, String instanceType) {
        super(name, friendlyName, editable, instanceType);
        Map<String, String> actionRefUrlsTemp = new HashMap<String, String>();
        for(String action : supportedActions){
            String relUrl = CollectionRelativeUrlBuilder.makeRelativeActionUrl(name, action);
            actionRefUrlsTemp.put(action, relUrl);
        }
        actionRefUrls = Collections.unmodifiableMap(actionRefUrlsTemp);
    }

    @Override
    public String getEntryType() {
        return "basic";
    }

    public Collection<String> getActions(){
        return supportedActions;
    }

    public Map<String, String> getLinks(){return actionRefUrls;}
}
