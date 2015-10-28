package com.taoswork.tallybook.business.datadomain.tallybusiness.security.permission.impl;

import com.taoswork.tallybook.business.datadomain.tallybusiness.security.permission.BuPermission;
import com.taoswork.tallybook.business.datadomain.tallybusiness.security.permission.BuPermissionEntry;
import com.taoswork.tallybook.general.authority.domain.permission.impl.PermissionEntryBaseImpl;
import com.taoswork.tallybook.general.authority.domain.resource.SecuredResource;
import com.taoswork.tallybook.general.authority.domain.resource.SecuredResourceSpecial;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationField;
import com.taoswork.tallybook.general.datadomain.support.presentation.relation.FieldRelation;
import com.taoswork.tallybook.general.datadomain.support.presentation.relation.RelationType;

import javax.persistence.*;

/**
 * Created by Gao Yuan on 2015/8/27.
 */
@Entity
@Table(name="TB_SEC_PERM_ENTRY")
public class BuPermissionEntryImpl
    extends PermissionEntryBaseImpl<BuPermission>
    implements BuPermissionEntry {

    @Column(name = "BU_ID", nullable = false)
    @PresentationField(order = 3)
    protected Long buId;

    @FieldRelation(RelationType.OneWay_ManyToOne)
    @ManyToOne(targetEntity = BuSecuredResourceSpecialImpl.class)
    @JoinColumn(name = "RES_SPEC_ID")
    @PresentationField(order = 5)
    protected SecuredResourceSpecial securedResourceSpecial;

    @FieldRelation(RelationType.OneWay_ManyToOne)
    @ManyToOne(targetEntity = BuSecuredResourceImpl.class)
    @JoinColumn(name = "RES_ID")
    @PresentationField(order = 5)
    protected SecuredResource securedResource;

    @FieldRelation(RelationType.TwoWay_ManyToOneOwner)
    @ManyToOne(targetEntity = BuPermissionEntryImpl.class)
    @JoinColumn(name = "PERM_ID")
    @PresentationField(order = 7)
    protected BuPermission permission;
    public static final String OWN_M2O_PERM = "permission";

    @Override
    public Long getBuId() {
        return buId;
    }

    @Override
    public void setBuId(Long buId) {
        this.buId = buId;
    }

    @Override
    public SecuredResourceSpecial getSecuredResourceSpecial() {
        return securedResourceSpecial;
    }

    @Override
    public void setSecuredResourceSpecial(SecuredResourceSpecial securedResourceSpecial) {
        this.securedResourceSpecial = securedResourceSpecial;
    }

    @Override
    public SecuredResource getSecuredResource() {
        return securedResource;
    }

    @Override
    public void setSecuredResource(SecuredResource securedResource) {
        this.securedResource = securedResource;
    }

    @Override
    public BuPermission getPermission() {
        return this.permission;
    }

    @Override
    public void setPermission(BuPermission permission) {
        this.permission = permission;
    }
}
