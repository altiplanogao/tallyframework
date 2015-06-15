package com.taoswork.tallybook.business.datadomain.tallybusiness.impl;

import com.taoswork.tallybook.business.datadomain.tallybusiness.Employee;
import com.taoswork.tallybook.business.datadomain.tallybusiness.WorkFeedback;
import com.taoswork.tallybook.business.datadomain.tallybusiness.WorkTicket;

import java.util.Date;

/**
 * Created by Gao Yuan on 2015/4/16.
 */
public class WorkFeedbackImpl implements WorkFeedback {
    protected Long id;
    protected WorkTicket workTicket;
    protected Employee writer;
    protected Date date;
    protected String content;
}
