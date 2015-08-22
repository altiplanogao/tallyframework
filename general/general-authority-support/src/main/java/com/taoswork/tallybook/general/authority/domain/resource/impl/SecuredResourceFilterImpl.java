package com.taoswork.tallybook.general.authority.domain.resource.impl;

import com.taoswork.tallybook.general.authority.domain.resource.SecuredResourceFilter;
import com.taoswork.tallybook.general.authority.domain.resource.SecuredResource;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationField;
import com.taoswork.tallybook.general.datadomain.support.presentation.relation.FieldRelation;
import com.taoswork.tallybook.general.datadomain.support.presentation.relation.RelationType;

import javax.persistence.*;

/**
 * Created by Gao Yuan on 2015/6/5.
 */
@Entity
@Table(name = "AUTH_SECURED_RESOURCE_FILTER")
public class SecuredResourceFilterImpl implements SecuredResourceFilter {

    @Id
    @Column(name = "ID")
    @PresentationField(order = 1)
    public Long id;

    //IResourceFilter.getCode()
    @Column(name = "FRIENDLY_NAME", nullable = false)
    @PresentationField(order = 2, nameField = true)
    public String name;

    @FieldRelation(RelationType.TwoWay_ManyToOneOwner)
    @ManyToOne(targetEntity = SecuredResourceImpl.class)
    @JoinColumn(name = "RES_TYPE_ID")
    @PresentationField(order = 3)
    public SecuredResource securedResource;
    public static final String OWN_M2O_RES_ENTITY = "securedResource";

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
    public SecuredResourceFilter setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public SecuredResource getSecuredResource() {
        return securedResource;
    }

    @Override
    public SecuredResourceFilter setResourceType(SecuredResource securedResource) {
        this.securedResource = securedResource;
        return this;
    }

    @Override
    public String getFilter() {
        return filter;
    }

    @Override
    public SecuredResourceFilter setFilter(String filter) {
        this.filter = filter;
        return this;
    }

    @Override
    public String getFilterParameter() {
        return filterParameter;
    }

    @Override
    public SecuredResourceFilter setFilterParameter(String filterParameter) {
        this.filterParameter = filterParameter;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(name + "[" + securedResource.getResourceEntity());
        if(null != filter){
            sb.append(", " + filter + "(" + filterParameter + ")");
        }
        sb.append("]");
        return sb.toString();
    }
}
