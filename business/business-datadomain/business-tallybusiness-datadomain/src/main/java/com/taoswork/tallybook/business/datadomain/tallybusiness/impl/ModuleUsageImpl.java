package com.taoswork.tallybook.business.datadomain.tallybusiness.impl;

import com.taoswork.tallybook.business.datadomain.tallybusiness.BusinessUnit;
import com.taoswork.tallybook.business.datadomain.tallybusiness.ModuleUsage;
import com.taoswork.tallybook.datadomain.base.entity.PersistEntity;

import java.util.Date;

/**
 * Created by Gao Yuan on 2015/4/16.
 */
@PersistEntity
public class ModuleUsageImpl implements ModuleUsage {
    protected Long id;
    protected BusinessUnit businessUnit;
    protected String moduleName;
    protected String description;
    protected String moduleService; // a key for module service url

    protected boolean producer;
    protected boolean consumer;
    protected boolean hide = false;

    protected Date availableFrom;
    protected Date availableTo;
}
