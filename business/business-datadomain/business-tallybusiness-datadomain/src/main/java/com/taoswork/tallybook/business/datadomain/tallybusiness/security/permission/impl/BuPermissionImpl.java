package com.taoswork.tallybook.business.datadomain.tallybusiness.security.permission.impl;

import com.taoswork.tallybook.business.datadomain.tallybusiness.Employee;
import com.taoswork.tallybook.business.datadomain.tallybusiness.impl.EmployeeImpl;
import com.taoswork.tallybook.business.datadomain.tallybusiness.security.permission.BuPermission;
import com.taoswork.tallybook.business.datadomain.tallybusiness.security.permission.BuPermissionEntry;
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
@Table(name = "TB_PERM")
public class BuPermissionImpl
    extends PermissionBaseImpl<BuPermissionEntry>
    implements BuPermission {

    @FieldRelation(RelationType.TwoWay_ManyToOneBelonging)
    @OneToMany(
            targetEntity = BuPermissionEntryImpl.class,
            mappedBy = BuPermissionEntryImpl.OWN_M2O_PERM,
            fetch = FetchType.LAZY)
    protected List<BuPermissionEntry> allEntries = new ArrayList<BuPermissionEntry>();
    public static final String OWN_M2M_ALL_ENTRIES = "allEntries";

    /** All employees that have this role */
    @FieldRelation(RelationType.TwoWay_ManyToManyBelonging)
    @ManyToMany(fetch = FetchType.LAZY, targetEntity = EmployeeImpl.class,
        mappedBy = EmployeeImpl.OWN_M2M_ALL_PERMS)
//    @JoinTable(name = BuEmployeeImpl.OWN_M2M_EMPLOYEE_PERMS_XTABLE,
//        joinColumns = @JoinColumn(name = BuEmployeeImpl.XTABLE_EMPLOYEE_PERMS__PERM_COL),
//        inverseJoinColumns = @JoinColumn(name = BuEmployeeImpl.XTABLE_EMPLOYEE_PERMS__EMPLOYEE_COL))
    protected Set<Employee> allEmployees = new HashSet<Employee>();

    @Override
    public List<BuPermissionEntry> getAllEntries() {
        return allEntries;
    }

    @Override
    public void setAllEntries(List<BuPermissionEntry> allEntries) {
        this.allEntries = allEntries;
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
