package com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.actions;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/11/22.
 */
public class CollectionRelativeUrlBuilder {
    private static final Map<String, String> actionName2UrlMapping = new HashMap<String, String>();
    static {
        actionName2UrlMapping.put(CollectionActions.CREATE, "add");
        actionName2UrlMapping.put(CollectionActions.QUERY, "");
    }

    public static String makeRelativeActionUrl(String name, String action) {
        String actionR = actionName2UrlMapping.getOrDefault(action, action);
        if(StringUtils.isEmpty(actionR))
            return name;
        return StringUtils.join(new Object[]{name, actionR}, "/");
    }
}
