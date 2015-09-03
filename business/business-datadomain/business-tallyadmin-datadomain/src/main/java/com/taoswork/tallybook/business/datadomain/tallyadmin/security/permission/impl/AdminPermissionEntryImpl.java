package com.taoswork.tallybook.business.datadomain.tallyadmin.security.permission.impl;

import com.taoswork.tallybook.business.datadomain.tallyadmin.security.permission.AdminPermission;
import com.taoswork.tallybook.business.datadomain.tallyadmin.security.permission.AdminPermissionEntry;
import com.taoswork.tallybook.general.authority.domain.permission.impl.PermissionEntryBaseImpl;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationField;
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
    extends PermissionEntryBaseImpl<AdminPermission>
    implements AdminPermissionEntry{

    @FieldRelation(RelationType.TwoWay_ManyToOneOwner)
    @ManyToOne(targetEntity = AdminPermissionEntryImpl.class)
    @JoinColumn(name = "PERM_ID")
    @PresentationField(order = 7)
    protected AdminPermission permission;
    public static final String OWN_M2O_PERM = "permission";

    @Override
    public AdminPermission getPermission() {
        return this.permission;
    }

    @Override
    public void setPermission(AdminPermission permission) {
        this.permission = permission;
    }
}