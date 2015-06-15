package com.taoswork.tallybook.business.datadomain.tallybusiness.impl;


import com.taoswork.tallybook.business.datadomain.tallybusiness.Organization;
import com.taoswork.tallybook.business.datadomain.tallybusiness.WorkPlan;
import com.taoswork.tallybook.business.datadomain.tallybusiness.WorkPlanType;
import com.taoswork.tallybook.business.datadomain.tallybusiness.WorkTicket;

import java.util.List;

/**
 * Created by Gao Yuan on 2015/4/16.
 */
public class WorkPlanImpl implements WorkPlan {
    protected Long id;
    protected String name;
    protected String description;
    protected Organization host;

    protected WorkPlanType type;

    protected String moduleType;
    /**
     * there should be serviceType defined in WorkSuite,
     * The WorkSuite can serve the WorkPlan if they have the same serviceType and moduleType.
     */
    protected String serviceType;

    protected List<WorkTicket> tickets;

    //an cached index?
    protected WorkTicket nextTicket;
}
