package com.taoswork.tallybook.business.datadomain.tallybusiness.security.permission.impl;

import com.taoswork.tallybook.business.datadomain.tallybusiness.security.permission.BuSecuredResource;
import com.taoswork.tallybook.business.datadomain.tallybusiness.security.permission.BuSecuredResourceFilter;
import com.taoswork.tallybook.general.authority.domain.resource.SecuredResourceFilter;
import com.taoswork.tallybook.general.authority.domain.resource.impl.SecuredResourceFilterBaseImpl;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationField;
import com.taoswork.tallybook.general.datadomain.support.presentation.relation.FieldRelation;
import com.taoswork.tallybook.general.datadomain.support.presentation.relation.RelationType;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TB_SEC_RES_FILTER")
public class BuSecuredResourceFilterImpl
    extends SecuredResourceFilterBaseImpl<BuSecuredResource>
    implements BuSecuredResourceFilter {

    @FieldRelation(RelationType.TwoWay_ManyToOneOwner)
    @ManyToOne(targetEntity = BuSecuredResourceImpl.class)
    @JoinColumn(name = "RES_TYPE_ID")
    @PresentationField(order = 3)
    public BuSecuredResource securedResource;
    public static final String OWN_M2O_RES_ENTITY = "securedResource";

    @Override
    public BuSecuredResource getSecuredResource() {
        return securedResource;
    }

    @Override
    public SecuredResourceFilter setResourceType(BuSecuredResource securedResource) {
        this.securedResource = securedResource;
        return this;
    }

}
