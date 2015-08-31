package com.taoswork.tallybook.business.datadomain.tallyadmin.security.permission.impl;

import com.taoswork.tallybook.business.datadomain.tallyadmin.AdminEmployee;
import com.taoswork.tallybook.business.datadomain.tallyadmin.impl.AdminEmployeeImpl;
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
    @ManyToMany(fetch = FetchType.LAZY, targetEntity = AdminPermissionImpl.class)
    @JoinTable(name = TABLE_NAME_JOIN_ROLE_PERM,
            joinColumns = @JoinColumn(name = TABLE_NAME_JOIN_ROLE_PERM_ROLE_COL),
            inverseJoinColumns = @JoinColumn(name = TABLE_NAME_JOIN_ROLE_PERM_PERM_COL))
    protected Set<AdminPermission> allPermissions = new HashSet<AdminPermission>();
    public static final String OWN_M2M_ALL_PERMS = "allPermissions";
    public static final String TABLE_NAME_JOIN_ROLE_PERM = "ADMIN_ROLE_PERM_XREF";
    public static final String TABLE_NAME_JOIN_ROLE_PERM_ROLE_COL = "ADMIN_ROLE_ID";
    public static final String TABLE_NAME_JOIN_ROLE_PERM_PERM_COL = "ADMIN_PERMISSION_ID";

    /** All employees that have this role */
    @FieldRelation(RelationType.TwoWay_ManyToManyBelonging)
    @ManyToMany(fetch = FetchType.LAZY, targetEntity = AdminEmployeeImpl.class,
        mappedBy = AdminEmployeeImpl.OWN_M2M_ALL_ROLES)
//    @JoinTable(name = AdminEmployeeImpl.OWN_M2M_EMPLOYEE_ROLES_XTABLE,
//        joinColumns = @JoinColumn(name = AdminEmployeeImpl.XTABLE_EMPLOYEE_ROLES__ROLE_COL),
//        inverseJoinColumns = @JoinColumn(name = AdminEmployeeImpl.XTABLE_EMPLOYEE_ROLES__EMPLOYEE_COL))
    protected Set<AdminEmployee> allEmployees = new HashSet<AdminEmployee>();

    @Override
    public Set<AdminPermission> getAllPermissions() {
        return allPermissions;
    }

    @Override
    public void setAllPermissions(Set<AdminPermission> allPermissions) {
        this.allPermissions = allPermissions;
    }

    @Override
    public Set<AdminEmployee> getAllEmployees() {
        return allEmployees;
    }

    @Override
    public void setAllEmployees(Set<AdminEmployee> allEmployees) {
        this.allEmployees = allEmployees;
    }
}
