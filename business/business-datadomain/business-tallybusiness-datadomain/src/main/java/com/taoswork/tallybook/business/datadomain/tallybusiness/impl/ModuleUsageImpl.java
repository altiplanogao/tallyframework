package com.taoswork.tallybook.business.datadomain.tallybusiness.impl;

import com.taoswork.tallybook.business.datadomain.tallybusiness.ModuleUsage;
import com.taoswork.tallybook.business.datadomain.tallybusiness.Organization;

/**
 * Created by Gao Yuan on 2015/4/16.
 */
public class ModuleUsageImpl implements ModuleUsage {
    protected Long id;
    protected Organization organization;
    protected String moduleName;
    protected boolean producer;
    protected boolean consumer;
    protected boolean visible;
}
