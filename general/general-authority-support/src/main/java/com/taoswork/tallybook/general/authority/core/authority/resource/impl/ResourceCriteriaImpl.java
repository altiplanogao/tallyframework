package com.taoswork.tallybook.general.authority.core.authority.resource.impl;

import com.taoswork.tallybook.general.authority.core.authority.resource.ResourceCriteria;
import com.taoswork.tallybook.general.authority.core.authority.resource.ResourceType;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationField;

import javax.persistence.*;

/**
 * Created by Gao Yuan on 2015/6/5.
 */
@Entity
@Table(name = "AUTH_RESOURCE_CRITERIA")
public class ResourceCriteriaImpl implements ResourceCriteria {

    public static final String COL_NAME_4_RES_TYPE = "RES_TYPE_ID";

    @Id
    @Column(name = "ID")
    @PresentationField(order = 1)
    public Long id;

    @Column(name = "FRIENDLY_NAME")
    @PresentationField(order = 2, nameField = true)
    public String name;

    @ManyToOne(targetEntity = ResourceTypeImpl.class)
    @JoinColumn(name = COL_NAME_4_RES_TYPE)
    @PresentationField(order = 3)
    public ResourceType resourceType;
    public static final String OWN_M2O_RES_TYPE = "resourceType";

    @Column(name = "FILTER")
    @PresentationField(order = 6)
    public String filter;

    @Column(name = "FILTER_PARAM")
    @PresentationField(order = 7)
    public String filterParameter;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ResourceCriteria setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public ResourceType getResourceType() {
        return resourceType;
    }

    @Override
    public ResourceCriteria setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
        return this;
    }

    @Override
    public String getFilter() {
        return filter;
    }

    @Override
    public ResourceCriteria setFilter(String filter) {
        this.filter = filter;
        return this;
    }

    @Override
    public String getFilterParameter() {
        return filterParameter;
    }

    @Override
    public ResourceCriteria setFilterParameter(String filterParameter) {
        this.filterParameter = filterParameter;
        return this;
    }

    @Override
    public boolean isRootTypeCriteria(){
        return ((null == filter) || "".equals(filter));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(name + "[" + resourceType.getType());
        if(null != filter){
            sb.append(", " + filter + "(" + filterParameter + ")");
        }
        sb.append("]");
        return sb.toString();
    }
}
