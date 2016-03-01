package com.taoswork.tallybook.business.datadomain.tallybusiness;

/**
 * Created by Gao Yuan on 2015/10/5.
 */
public class TallyBusinessDataDomain {
    public static Class[] persistableEntities(){
        return new Class[]{
                BusinessUnit.class,
                BusinessPartner.class,
                Employee.class,
                Role.class,
                ModuleUsage.class,

                Asset.class,
                WorkPlan.class,
                WorkTicket.class,
                WorkSuite.class,
                WorkFeedback.class
        };
    }
}
