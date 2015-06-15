package com.taoswork.tallybook.business.datadomain.tallybusiness.impl;

import com.taoswork.tallybook.business.datadomain.tallybusiness.*;

import java.util.Date;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/4/16.
 */
public class WorkTicketImp implements WorkTicket {
    protected Long id;
    protected Employee worker;
    protected Employee supervisior;

    protected WorkPlan workPlan;

    protected Date startWorkingTime;
    protected Date endWorkingTime;

    protected List<WorkFeedback> feedbacks;

    protected WorkTicketStatus status;
}
