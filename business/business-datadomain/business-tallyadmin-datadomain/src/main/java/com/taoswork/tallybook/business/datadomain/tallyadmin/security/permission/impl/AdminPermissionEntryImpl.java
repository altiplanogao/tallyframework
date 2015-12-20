package com.taoswork.tallybook.business.datadomain.tallyadmin.security.permission.impl;

import com.taoswork.tallybook.business.datadomain.tallyadmin.security.permission.AdminPermission;
import com.taoswork.tallybook.business.datadomain.tallyadmin.security.permission.AdminPermissionEntry;
import com.taoswork.tallybook.business.datadomain.tallyadmin.security.permission.AdminSecuredResource;
import com.taoswork.tallybook.business.datadomain.tallyadmin.security.permission.AdminSecuredResourceSpecial;
import com.taoswork.tallybook.general.authority.domain.permission.impl.PermissionEntryBaseImpl;
import com.taoswork.tallybook.general.authority.domain.resource.SecuredResource;
import com.taoswork.tallybook.general.authority.domain.resource.SecuredResourceSpecial;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationField;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.Visibility;
import com.taoswork.tallybook.general.datadomain.support.presentation.relation.FieldRelation;
import com.taoswork.tallybook.general.datadomain.support.presentation.relation.RelationType;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by Gao Yuan on 2015/8/27.
 */
@Entity
@Table(name="ADMIN_PERM_ENTRY")
public class AdminPermissionEntryImpl
    extends PermissionEntryBaseImpl<AdminPermission, AdminSecuredResource, AdminSecuredResourceSpecial>
    implements AdminPermissionEntry {

    @FieldRelation(RelationType.OneWay_ManyToOne)
    @ManyToOne(targetEntity = AdminSecuredResourceImpl.class)
    @JoinColumn(name = "RES_ID")
    @PresentationField(order = 5)
    protected AdminSecuredResource securedResource;

    @FieldRelation(RelationType.OneWay_ManyToOne)
    @ManyToOne(targetEntity = AdminSecuredResourceSpecialImpl.class)
    @JoinColumn(name = "RES_SPEC_ID")
    @PresentationField(order = 5)
    protected AdminSecuredResourceSpecial securedResourceSpecial;

    @FieldRelation(RelationType.TwoWay_ManyToOneOwner)
    @ManyToOne(targetEntity = AdminPermissionEntryImpl.class)
    @JoinColumn(name = "PERM_ID")
    @PresentationField(visibility = Visibility.HIDDEN_ALL)
    protected AdminPermission permission;
    public static final String OWN_M2O_PERM = "permission";

    @Override
    public AdminSecuredResource getSecuredResource() {
        return securedResource;
    }

    @Override
    public void setSecuredResource(AdminSecuredResource securedResource) {
        this.securedResource = securedResource;
    }

    @Override
    public AdminSecuredResourceSpecial getSecuredResourceSpecial() {
        return securedResourceSpecial;
    }

    @Override
    public void setSecuredResourceSpecial(AdminSecuredResourceSpecial securedResourceSpecial) {
        this.securedResourceSpecial = securedResourceSpecial;
    }

    @Override
    public AdminPermission getPermission() {
        return this.permission;
    }

    @Override
    public void setPermission(AdminPermission permission) {
        this.permission = permission;
    }

}
