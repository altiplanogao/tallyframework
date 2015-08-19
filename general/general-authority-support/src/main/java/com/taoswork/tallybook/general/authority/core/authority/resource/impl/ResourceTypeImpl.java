package com.taoswork.tallybook.general.authority.core.authority.resource.impl;

import com.taoswork.tallybook.general.authority.core.authority.resource.ResourceCriteria;
import com.taoswork.tallybook.general.authority.core.authority.resource.ResourceType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/6/5.
 */
@Entity
@Table(name = "AUTH_RESOURCE_TYPE")
public class ResourceTypeImpl implements ResourceType{
    @Id
    @Column(name = "ID")
    protected Long id;

    @Column(name = "TYPE")
    protected String type;

    @Column(name = "FRIENDLY_NAME")
    protected String friendlyName;

    @OneToMany(
            targetEntity = ResourceCriteriaImpl.class,
            mappedBy = ResourceCriteriaImpl.OWN_M2O_RES_TYPE,
            fetch = FetchType.LAZY)
    protected List<ResourceCriteria> criterias = new ArrayList<ResourceCriteria>();

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
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
    public List<ResourceCriteria> getCriterias() {
        return criterias;
    }

    @Override
    public void setCriterias(List<ResourceCriteria> criterias) {
        this.criterias = criterias;
    }
}
