package com.taoswork.tallybook.business.datadomain.tallybusiness;


import com.taoswork.tallybook.datadomain.onmongo.AbstractDocument;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

/**
 * Created by Gao Yuan on 2015/4/16.
 */
@Entity("worksuite")
public class WorkSuite extends AbstractDocument {
    protected String name;
    protected String description;
    protected String moduleType;
    protected String serviceType;

    @Reference
    protected BusinessUnit producer;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModuleType() {
        return moduleType;
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public BusinessUnit getProducer() {
        return producer;
    }

    public void setProducer(BusinessUnit producer) {
        this.producer = producer;
    }
}
