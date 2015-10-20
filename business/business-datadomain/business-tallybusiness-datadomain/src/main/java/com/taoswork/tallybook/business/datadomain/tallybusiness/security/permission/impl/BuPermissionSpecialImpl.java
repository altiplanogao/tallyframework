package com.taoswork.tallybook.business.datadomain.tallybusiness.security.permission.impl;

import com.taoswork.tallybook.business.datadomain.tallybusiness.security.permission.BuPermission;
import com.taoswork.tallybook.business.datadomain.tallybusiness.security.permission.BuPermissionSpecial;
import com.taoswork.tallybook.general.authority.domain.permission.impl.PermissionSpecialBaseImpl;
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
@Table(name="TB_SEC_PERM_ENTRY")
public class BuPermissionSpecialImpl
    extends PermissionSpecialBaseImpl<BuPermission>
    implements BuPermissionSpecial {

    @FieldRelation(RelationType.TwoWay_ManyToOneOwner)
    @ManyToOne(targetEntity = BuPermissionSpecialImpl.class)
    @JoinColumn(name = "PERM_ID")
    @PresentationField(order = 7)
    protected BuPermission permission;
    public static final String OWN_M2O_PERM = "permission";

    @Override
    public BuPermission getPermission() {
        return this.permission;
    }

    @Override
    public void setPermission(BuPermission permission) {
        this.permission = permission;
    }
}
