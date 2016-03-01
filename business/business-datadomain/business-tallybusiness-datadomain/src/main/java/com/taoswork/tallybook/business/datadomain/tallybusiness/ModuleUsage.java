package com.taoswork.tallybook.business.datadomain.tallybusiness;

import com.taoswork.tallybook.datadomain.base.entity.PersistEntity;
import com.taoswork.tallybook.datadomain.onmongo.AbstractDocument;
import org.mongodb.morphia.annotations.Entity;

import java.util.Date;

/**
 * Created by Gao Yuan on 2015/4/16.
 */
@Entity("moduleusage")
@PersistEntity
public class ModuleUsage extends AbstractDocument {
    protected String moduleName;
    protected String description;
    protected String moduleService; // a key for module service url

    protected BusinessUnit bu;
    protected boolean producer;
    protected boolean consumer;
    protected boolean hide = false;

    protected Date availableFrom;
    protected Date availableTo;
}
