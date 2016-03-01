package com.taoswork.tallybook.business.datadomain.tallymanagement;

import com.taoswork.tallybook.business.datadomain.tallymanagement.impl.ModuleRegistryImpl;

/**
 * Created by Gao Yuan on 2016/2/29.
 */
public class TallyManagementDataDomain {
    public static Class[] persistableEntities(){
        return new Class[]{ModuleRegistryImpl.class};
    }
}
