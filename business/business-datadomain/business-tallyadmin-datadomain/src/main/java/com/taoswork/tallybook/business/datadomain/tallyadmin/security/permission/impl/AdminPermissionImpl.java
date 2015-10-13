package com.taoswork.tallybook.business.datadomain.tallyadmin.security.permission.impl;

import com.taoswork.tallybook.business.datadomain.tallyadmin.AdminEmployee;
import com.taoswork.tallybook.business.datadomain.tallyadmin.impl.AdminEmployeeImpl;
import com.taoswork.tallybook.business.datadomain.tallyadmin.security.permission.AdminPermission;
import com.taoswork.tallybook.business.datadomain.tallyadmin.security.permission.AdminPermissionSpecial;
import com.taoswork.tallybook.general.authority.domain.permission.impl.PermissionBaseImpl;
import com.taoswork.tallybook.general.datadomain.support.presentation.relation.FieldRelation;
import com.taoswork.tallybook.general.datadomain.support.presentation.relation.RelationType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Gao Yuan on 2015/8/27.
 */
@Entity
@Table(name = "ADMIN_PERM")
public class AdminPermissionImpl
    extends PermissionBaseImpl<AdminPermissionSpecial>
    implements AdminPermission {

    @FieldRelation(RelationType.TwoWay_ManyToOneBelonging)
    @OneToMany(
            targetEntity = AdminPermissionSpecialImpl.class,
            mappedBy = AdminPermissionSpecialImpl.OWN_M2O_PERM,
            fetch = FetchType.LAZY)
    protected List<AdminPermissionSpecial> allEntries = new ArrayList<AdminPermissionSpecial>();
    public static final String OWN_M2M_ALL_ENTRIES = "allEntries";

    /** All employees that have this role */
    @FieldRelation(RelationType.TwoWay_ManyToManyBelonging)
    @ManyToMany(fetch = FetchType.LAZY, targetEntity = AdminEmployeeImpl.class,
        mappedBy = AdminEmployeeImpl.OWN_M2M_ALL_PERMS)
//    @JoinTable(name = AdminEmployeeImpl.OWN_M2M_EMPLOYEE_PERMS_XTABLE,
//        joinColumns = @JoinColumn(name = AdminEmployeeImpl.XTABLE_EMPLOYEE_PERMS__PERM_COL),
//        inverseJoinColumns = @JoinColumn(name = AdminEmployeeImpl.XTABLE_EMPLOYEE_PERMS__EMPLOYEE_COL))
    protected Set<AdminEmployee> allEmployees = new HashSet<AdminEmployee>();

    @Override
    public List<AdminPermissionSpecial> getAllEntries() {
        return allEntries;
    }

    @Override
    public void setAllEntries(List<AdminPermissionSpecial> allEntries) {
        this.allEntries = allEntries;
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
