package com.taoswork.tallybook.business.datadomain.tallyadmin.security.permission.impl;

import com.taoswork.tallybook.business.datadomain.tallyadmin.security.permission.AdminSecuredResource;
import com.taoswork.tallybook.business.datadomain.tallyadmin.security.permission.AdminSecuredResourceSpecial;
import com.taoswork.tallybook.general.authority.domain.resource.SecuredResourceSpecial;
import com.taoswork.tallybook.general.authority.domain.resource.impl.SecuredResourceSpecialBaseImpl;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationField;
import com.taoswork.tallybook.general.datadomain.support.presentation.relation.FieldRelation;
import com.taoswork.tallybook.general.datadomain.support.presentation.relation.RelationType;
import com.taoswork.tallybook.general.datadomain.support.presentation.typed.PresentationForeignKey;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ADMIN_RES_SPECIAL")
public class AdminSecuredResourceSpecialImpl
    extends SecuredResourceSpecialBaseImpl<AdminSecuredResource>
    implements AdminSecuredResourceSpecial {

    @FieldRelation(RelationType.TwoWay_ManyToOneOwner)
    @ManyToOne(targetEntity = AdminSecuredResourceImpl.class)
    @JoinColumn(name = "RES_TYPE_ID")
    @PresentationField(order = 3, required = true)
    @PresentationForeignKey(displayField = "friendlyName")
    public AdminSecuredResource securedResource;
    public static final String OWN_M2O_RES_ENTITY = "securedResource";

    @Override
    public AdminSecuredResource getSecuredResource() {
        return securedResource;
    }

    @Override
    public SecuredResourceSpecial setResourceType(AdminSecuredResource securedResource) {
        this.securedResource = securedResource;
        return this;
    }

}
