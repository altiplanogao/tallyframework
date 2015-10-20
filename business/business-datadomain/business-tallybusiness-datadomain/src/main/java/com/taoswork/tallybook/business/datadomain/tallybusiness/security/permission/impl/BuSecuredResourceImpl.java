package com.taoswork.tallybook.business.datadomain.tallybusiness.security.permission.impl;

import com.taoswork.tallybook.business.datadomain.tallybusiness.TallyBusinessDataDomain;
import com.taoswork.tallybook.business.datadomain.tallybusiness.security.permission.BuSecuredResource;
import com.taoswork.tallybook.business.datadomain.tallybusiness.security.permission.BuSecuredResourceFilter;
import com.taoswork.tallybook.general.authority.domain.resource.impl.SecuredResourceBaseImpl;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationField;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.Visibility;
import com.taoswork.tallybook.general.datadomain.support.presentation.relation.FieldRelation;
import com.taoswork.tallybook.general.datadomain.support.presentation.relation.RelationType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//@NamedQueries(
//    {
//        @NamedQuery(name = "SecuredResource.findByOrg",
//            query = "SELECT sr FROM com.taoswork.tallybook.general.authority.domain.resource.SecuredResource sr" +
//                " WHERE sr.organization = :organization")
//    }
//)
@Entity
@Table(name = "TB_SEC_RES")
public class BuSecuredResourceImpl
    extends SecuredResourceBaseImpl<BuSecuredResourceFilter>
    implements BuSecuredResource {

    protected static final String ID_GENERATOR_NAME = "BuSecuredResourceImpl_IdGen";

    @Column(name = "ORG")
    protected Long organization;

    @FieldRelation(RelationType.TwoWay_ManyToOneBelonging)
    @OneToMany(
        targetEntity = BuSecuredResourceFilterImpl.class,
        mappedBy = BuSecuredResourceFilterImpl.OWN_M2O_RES_ENTITY,
        cascade = {CascadeType.ALL},
        fetch = FetchType.LAZY)
    protected List<BuSecuredResourceFilter> filters = new ArrayList<BuSecuredResourceFilter>();

    @Override
    public Long getOrganization() {
        return organization;
    }

    @Override
    public void setOrganization(Long organization) {
        this.organization = organization;
    }

    @Override
    public List<BuSecuredResourceFilter> getFilters() {
        return filters;
    }

    @Override
    public void setFilters(List<BuSecuredResourceFilter> filters) {
        this.filters = filters;
    }

}
