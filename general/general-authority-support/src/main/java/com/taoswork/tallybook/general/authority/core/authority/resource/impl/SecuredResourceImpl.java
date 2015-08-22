package com.taoswork.tallybook.general.authority.core.authority.resource.impl;

import com.taoswork.tallybook.general.authority.core.authority.resource.SecuredResourceFilter;
import com.taoswork.tallybook.general.authority.core.authority.resource.SecuredResource;
import com.taoswork.tallybook.general.authority.core.basic.ProtectionMode;
import com.taoswork.tallybook.general.datadomain.support.presentation.relation.FieldRelation;
import com.taoswork.tallybook.general.datadomain.support.presentation.relation.RelationType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/6/5.
 */
@Entity
@Table(name = "AUTH_SECURED_RESOURCE")
public class SecuredResourceImpl implements SecuredResource {
    @Id
    @Column(name = "ID")
    protected Long id;

    @Column(name = "FRIENDLY_NAME")
    protected String friendlyName;

    @Column(name = "TYPE")
    protected String resourceEntity;

    @Column(name = "CATEGORY")
    protected String category;

    @Column(name = "HAS_MASTER")
    protected boolean masterControlled = true;

    @Column(name = "PROT_MOD")
    protected String protectionMode;

    @FieldRelation(RelationType.TwoWay_ManyToOneBelonging)
    @OneToMany(
            targetEntity = SecuredResourceFilterImpl.class,
            mappedBy = SecuredResourceFilterImpl.OWN_M2O_RES_ENTITY,
            fetch = FetchType.LAZY)
    protected List<SecuredResourceFilter> filters = new ArrayList<SecuredResourceFilter>();

    @Version
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
    public List<SecuredResourceFilter> getFilters() {
        return filters;
    }

    @Override
    public void setFilters(List<SecuredResourceFilter> filters) {
        this.filters = filters;
    }
}
