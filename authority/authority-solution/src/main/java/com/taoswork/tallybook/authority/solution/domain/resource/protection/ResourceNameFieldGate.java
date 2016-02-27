package com.taoswork.tallybook.authority.solution.domain.resource.protection;

import com.taoswork.tallybook.datadomain.base.entity.valuegate.BaseFieldGate;

/**
 * Created by Gao Yuan on 2016/2/24.
 */
public class ResourceNameFieldGate extends BaseFieldGate<String> {
    @Override
    protected String doStore(String val, String oldVal) {
        return unifiedResourceName(val);
    }

    public static String unifiedResourceName(String resourceName){
        if(resourceName.startsWith("#"))
            return resourceName;
        return "#"+resourceName.replaceAll("\\.", "-");
    }
}
