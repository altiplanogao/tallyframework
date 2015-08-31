package com.taoswork.tallybook.business.datadomain.tallyadmin.security.permission.impl;

import com.taoswork.tallybook.business.datadomain.tallyadmin.security.permission.AdminPermission;
import com.taoswork.tallybook.business.datadomain.tallyadmin.security.permission.AdminRole;
import com.taoswork.tallybook.general.authority.domain.permission.impl.RoleBaseImpl;
import com.taoswork.tallybook.general.datadomain.support.presentation.relation.FieldRelation;
import com.taoswork.tallybook.general.datadomain.support.presentation.relation.RelationType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Gao Yuan on 2015/8/27.
 */
@Entity
@Table(name="ADMIN_ROLE")
public class AdminRoleImpl
    extends RoleBaseImpl<AdminPermission>
    implements AdminRole {

    @FieldRelation(RelationType.OneWay_ManyToMany)
    @ManyToMany(
            targetEntity = AdminPermissionImpl.class,
            fetch = FetchType.LAZY)
    @JoinTable(name = TABLE_NAME_JOIN_ROLE_PERM,
            joinColumns = @JoinColumn(name = TABLE_NAME_JOIN_ROLE_PERM_ROLE_COL, referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = TABLE_NAME_JOIN_ROLE_PERM_PERM_COL, referencedColumnName = "ID"))
    protected Set<AdminPermission> allPermissions = new HashSet<AdminPermission>();
    public static final String OWN_M2M_ALL_PERMS = "allPermissions";

    @Override
    public Set<AdminPermission> getAllPermissions() {
        return allPermissions;
    }

    @Override
    public void setAllPermissions(Set<AdminPermission> allPermissions) {
        this.allPermissions = allPermissions;
    }
}
