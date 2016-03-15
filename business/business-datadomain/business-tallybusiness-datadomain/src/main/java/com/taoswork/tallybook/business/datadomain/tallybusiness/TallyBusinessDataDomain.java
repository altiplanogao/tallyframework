package com.taoswork.tallybook.business.datadomain.tallybusiness;

import com.taoswork.tallybook.business.datadomain.tallybusiness.module.ModuleUsage;
import com.taoswork.tallybook.business.datadomain.tallybusiness.subject.*;
import com.taoswork.tallybook.business.datadomain.tallybusiness.work.WorkFeedback;
import com.taoswork.tallybook.business.datadomain.tallybusiness.work.WorkPlan;
import com.taoswork.tallybook.business.datadomain.tallybusiness.work.WorkTicket;

/**
 * Created by Gao Yuan on 2015/10/5.
 */
public class TallyBusinessDataDomain {
    public static Class[] persistableEntities(){
        return new Class[]{
                //subject
                Asset.class,
                Bu.class,
                Bp.class,
                Employee.class,
                Role.class,

                //module
                ModuleUsage.class,

                //work
                WorkPlan.class,
                WorkTicket.class,
                WorkFeedback.class
        };
    }
}
