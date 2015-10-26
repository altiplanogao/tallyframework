package com.taoswork.tallybook.business.datadomain.tallybusiness.security.permission.impl;

import com.taoswork.tallybook.business.datadomain.tallybusiness.security.permission.BuSecuredResource;
import com.taoswork.tallybook.business.datadomain.tallybusiness.security.permission.BuSecuredResourceSpecial;
import com.taoswork.tallybook.general.authority.domain.resource.impl.SecuredResourceBaseImpl;
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
    extends SecuredResourceBaseImpl<BuSecuredResourceSpecial>
    implements BuSecuredResource {

    @Column(name = "ORG")
    protected Long organization;

    @FieldRelation(RelationType.TwoWay_ManyToOneBelonging)
    @OneToMany(
        targetEntity = BuSecuredResourceSpecialImpl.class,
        mappedBy = BuSecuredResourceSpecialImpl.OWN_M2O_RES_ENTITY,
        cascade = {CascadeType.ALL},
        fetch = FetchType.LAZY)
    protected List<BuSecuredResourceSpecial> filters = new ArrayList<BuSecuredResourceSpecial>();

    @Override
    public Long getOrganization() {
        return organization;
    }

    @Override
    public void setOrganization(Long organization) {
        this.organization = organization;
    }

    @Override
    public List<BuSecuredResourceSpecial> getFilters() {
        return filters;
    }

    @Override
    public void setFilters(List<BuSecuredResourceSpecial> filters) {
        this.filters = filters;
    }

}
