package com.taoswork.tallybook.business.datadomain.tallybusiness.module;

import com.taoswork.tallybook.business.datadomain.tallybusiness.Privacy;
import com.taoswork.tallybook.datadomain.base.entity.PersistEntity;
import com.taoswork.tallybook.datadomain.onmongo.AbstractDocument;
import org.mongodb.morphia.annotations.Entity;

import java.util.Date;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/4/16.
 */
@Entity("moduleentry")
@PersistEntity
public class ModuleEntry extends AbstractDocument {

    protected String name;
    protected String fullName;
    protected String description;
    protected String moduleService; // a key for module service url

    protected List<String> assetFacets;
    protected String producer;
    protected Privacy privacy  = Privacy.Public;

    protected String version;
    protected Date updateDate;
}
