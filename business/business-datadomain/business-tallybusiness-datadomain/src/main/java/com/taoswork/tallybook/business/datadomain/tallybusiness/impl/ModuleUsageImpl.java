package com.taoswork.tallybook.business.datadomain.tallybusiness.impl;

import com.taoswork.tallybook.business.datadomain.tallybusiness.BusinessUnit;
import com.taoswork.tallybook.business.datadomain.tallybusiness.ModuleUsage;

/**
 * Created by Gao Yuan on 2015/4/16.
 */
public class ModuleUsageImpl implements ModuleUsage {
    protected Long id;
    protected BusinessUnit businessUnit;
    protected String moduleName;
    protected boolean producer;
    protected boolean consumer;
    protected boolean visible;
}
