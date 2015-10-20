package com.taoswork.tallybook.business.datadomain.tallyadmin.security.permission.impl;

import com.taoswork.tallybook.business.datadomain.tallyadmin.security.permission.AdminSecuredResource;
import com.taoswork.tallybook.business.datadomain.tallyadmin.security.permission.AdminSecuredResourceFilter;
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
@Table(name = "ADMIN_RES")
public class AdminSecuredResourceImpl
    extends SecuredResourceBaseImpl<AdminSecuredResourceFilter>
    implements AdminSecuredResource {

    @FieldRelation(RelationType.TwoWay_ManyToOneBelonging)
    @OneToMany(
        targetEntity = AdminSecuredResourceFilterImpl.class,
        mappedBy = AdminSecuredResourceFilterImpl.OWN_M2O_RES_ENTITY,
        cascade = {},
        fetch = FetchType.LAZY)
    @PresentationField(visibility = Visibility.HIDDEN_ALL)
    protected List<AdminSecuredResourceFilter> filters = new ArrayList<AdminSecuredResourceFilter>();

    @Override
    public List<AdminSecuredResourceFilter> getFilters() {
        return filters;
    }

    @Override
    public void setFilters(List<AdminSecuredResourceFilter> filters) {
        this.filters = filters;
    }

}
