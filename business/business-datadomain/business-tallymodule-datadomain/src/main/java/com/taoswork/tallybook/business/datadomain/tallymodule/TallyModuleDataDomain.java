package com.taoswork.tallybook.business.datadomain.tallymodule;

import com.taoswork.tallybook.business.datadomain.tallymodule.impl.ModuleImpl;

/**
 * Created by Gao Yuan on 2016/2/29.
 */
public class TallyModuleDataDomain {
    public static Class[] persistableEntities(){
        return new Class[]{ModuleImpl.class};
    }
}
