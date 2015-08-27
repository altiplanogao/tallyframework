package com.taoswork.tallybook.business.datadomain.tallybusiness.impl;


import com.taoswork.tallybook.business.datadomain.tallybusiness.BusinessUnit;
import com.taoswork.tallybook.business.datadomain.tallybusiness.WorkSuite;

/**
 * Created by Gao Yuan on 2015/4/16.
 */
public class WorkSuiteImpl implements WorkSuite {
    protected Long id;
    protected String name;
    protected String description;
    protected String moduleType;
    protected String serviceType;

    protected BusinessUnit producer;
}
