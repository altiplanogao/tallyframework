package com.taoswork.tallybook.business.datadomain.tallybusiness;

import com.taoswork.tallybook.business.datadomain.tallybusiness.module.ModuleEntry;

/**
 * Created by Gao Yuan on 2015/10/5.
 */
public class TallyBusinessDataDomain {
    public static Class[] persistableEntities(){
        return new Class[]{
                Bu.class,
                Bp.class,
                Employee.class,
                Role.class,
                ModuleEntry.class,
                ModuleUsage.class,

                Asset.class,
                WorkPlan.class,
                WorkTicket.class,
                WorkFeedback.class
        };
    }
}
