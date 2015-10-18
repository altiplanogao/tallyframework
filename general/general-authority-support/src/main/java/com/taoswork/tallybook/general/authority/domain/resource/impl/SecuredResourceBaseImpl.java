package com.taoswork.tallybook.general.authority.domain.resource.impl;

import com.taoswork.tallybook.general.authority.domain.resource.SecuredResourceFilter;
import com.taoswork.tallybook.general.authority.domain.resource.SecuredResource;
import com.taoswork.tallybook.general.authority.core.basic.ProtectionMode;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationClass;
import com.taoswork.tallybook.general.datadomain.support.presentation.relation.FieldRelation;
import com.taoswork.tallybook.general.datadomain.support.presentation.relation.RelationType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/6/5.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@PresentationClass(instantiable =false)
public abstract class SecuredResourceBaseImpl<RF extends SecuredResourceFilter>
        implements SecuredResource<RF> {

    @Id
    @Column(name = "ID")
    protected Long id;

    @Column(name = "ORG")
    protected Long organization;

    @Column(name = "FRIENDLY_NAME")
    protected String friendlyName;

    //A Data line without resourceEntity, means its a main source line, only its version column is useful
    @Column(name = "TYPE")
    protected String resourceEntity;

    @Column(name = "CATEGORY")
    protected String category;

    @Column(name = "MASTER_CTRL")
    protected boolean masterControlled = true;

    @Column(name = "PROT_MOD")
    protected String protectionMode;

    @Version
    @Column(name="OPTLOCK")
    protected Integer version;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getOrganization() {
        return organization;
    }

    @Override
    public void setOrganization(Long organization) {
        this.organization = organization;
    }

    @Override
    public String getFriendlyName() {
        return friendlyName;
    }

    @Override
    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    @Override
    public String getResourceEntity() {
        return resourceEntity;
    }

    @Override
    public void setResourceEntity(String resourceEntity) {
        this.resourceEntity = resourceEntity;
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean isMasterControlled() {
        return masterControlled;
    }

    @Override
    public void setMasterControlled(boolean masterControlled) {
        this.masterControlled = masterControlled;
    }

    @Override
    public ProtectionMode getProtectionMode() {
        return ProtectionMode.fromType(protectionMode);
    }

    @Override
    public void setProtectionMode(ProtectionMode protectionMode) {
        this.protectionMode = ProtectionMode.toType(protectionMode);
    }


    @Override
    public int getVersion() {
        return version;
    }

    @Override
    public boolean isMainLine() {
        //A Data line without resourceEntity, means its a main source line, only its version column is useful
        return (null == resourceEntity) || "".equals(resourceEntity);
    }
}