package com.taoswork.tallybook.business.datadomain.tallybusiness.security.permission.impl;

import com.taoswork.tallybook.business.datadomain.tallybusiness.Employee;
import com.taoswork.tallybook.business.datadomain.tallybusiness.impl.EmployeeImpl;
import com.taoswork.tallybook.business.datadomain.tallybusiness.security.permission.BuPermission;
import com.taoswork.tallybook.business.datadomain.tallybusiness.security.permission.BuRole;
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
@Table(name="TB_SEC_ROLE")
public class BuRoleImpl
    extends RoleBaseImpl<BuPermission>
    implements BuRole {

    @FieldRelation(RelationType.OneWay_ManyToMany)
    @ManyToMany(fetch = FetchType.LAZY, targetEntity = BuPermissionImpl.class)
    @JoinTable(name = TABLE_NAME_JOIN_ROLE_PERM,
            joinColumns = @JoinColumn(name = TABLE_NAME_JOIN_ROLE_PERM_ROLE_COL),
            inverseJoinColumns = @JoinColumn(name = TABLE_NAME_JOIN_ROLE_PERM_PERM_COL))
    protected Set<BuPermission> allPermissions = new HashSet<BuPermission>();
    public static final String OWN_M2M_ALL_PERMS = "allPermissions";
    public static final String TABLE_NAME_JOIN_ROLE_PERM = "TB_SEC_ROLE_PERM_XREF";
    public static final String TABLE_NAME_JOIN_ROLE_PERM_ROLE_COL = "ADMIN_ROLE_ID";
    public static final String TABLE_NAME_JOIN_ROLE_PERM_PERM_COL = "ADMIN_PERMISSION_ID";

    /** All employees that have this role */
    @FieldRelation(RelationType.TwoWay_ManyToManyBelonging)
    @ManyToMany(fetch = FetchType.LAZY, targetEntity = EmployeeImpl.class,
        mappedBy = EmployeeImpl.OWN_M2M_ALL_ROLES)
//    @JoinTable(name = BuEmployeeImpl.OWN_M2M_EMPLOYEE_ROLES_XTABLE,
//        joinColumns = @JoinColumn(name = BuEmployeeImpl.XTABLE_EMPLOYEE_ROLES__ROLE_COL),
//        inverseJoinColumns = @JoinColumn(name = BuEmployeeImpl.XTABLE_EMPLOYEE_ROLES__EMPLOYEE_COL))
    protected Set<Employee> allEmployees = new HashSet<Employee>();

    @Override
    public Set<BuPermission> getAllPermissions() {
        return allPermissions;
    }

    @Override
    public void setAllPermissions(Set<BuPermission> allPermissions) {
        this.allPermissions = allPermissions;
    }

    @Override
    public Set<Employee> getAllEmployees() {
        return allEmployees;
    }

    @Override
    public void setAllEmployees(Set<Employee> allEmployees) {
        this.allEmployees = allEmployees;
    }
}
