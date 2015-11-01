package com.taoswork.tallybook.business.datadomain.tallybusiness.impl;

import com.taoswork.tallybook.business.datadomain.tallybusiness.*;
import com.taoswork.tallybook.business.datadomain.tallybusiness.security.permission.BuPermission;
import com.taoswork.tallybook.business.datadomain.tallybusiness.security.permission.BuRole;
import com.taoswork.tallybook.business.datadomain.tallybusiness.security.permission.impl.BuPermissionImpl;
import com.taoswork.tallybook.business.datadomain.tallybusiness.security.permission.impl.BuRoleImpl;
import com.taoswork.tallybook.business.datadomain.tallyuser.Person;
import com.taoswork.tallybook.general.authority.domain.permission.Permission;
import com.taoswork.tallybook.general.authority.domain.permission.Role;
import com.taoswork.tallybook.general.datadomain.support.entity.PersistField;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationField;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.Visibility;
import com.taoswork.tallybook.general.datadomain.support.presentation.relation.FieldRelation;
import com.taoswork.tallybook.general.datadomain.support.presentation.relation.RelationType;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Gao Yuan on 2015/4/16.
 */
@Entity
@Table(name = "TB_EMPLOYEE")
public class EmployeeImpl implements Employee {

    protected static final String ID_GENERATOR_NAME = "EmployeeImpl_IdGen";

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = ID_GENERATOR_NAME)
    @TableGenerator(
        name = ID_GENERATOR_NAME,
        table = TallyBusinessDataDomain.ID_GENERATOR_TABLE_NAME,
        initialValue = 1)
    @Column(name = "ID")
    @PersistField(fieldType = FieldType.ID)
    @PresentationField(order = 1, visibility = Visibility.HIDDEN_ALL)
    protected Long id;

    @Column(name = "PERSON_ID", nullable = false)
    protected Long personId;
    @Transient
    private transient Person person;

    @FieldRelation(RelationType.OneWay_ManyToOne)
    @ManyToOne(targetEntity = BusinessUnitImpl.class)
    @JoinColumn(name = "host_id",  nullable = false, updatable = false)
    @PersistField(required = true, fieldType = FieldType.FOREIGN_KEY)
    protected BusinessUnit businessUnit;

    protected EmployeeStatus activeStatus;

    @Column(name = "NAME", nullable = false)
    @PersistField(fieldType = FieldType.NAME)
    @PresentationField(order = 2)
    protected String name;

    @Column(name = "TITLE", nullable = true)
    @PersistField(fieldType = FieldType.STRING)
    @PresentationField(order = 2)
    protected String title;

    @Embedded
    protected EmployeeOwnedSetting setting;

    /** All roles that this user has */
    @FieldRelation(RelationType.TwoWay_ManyToManyOwner)
    @ManyToMany(fetch = FetchType.LAZY, targetEntity = BuRoleImpl.class)
    @JoinTable(name = OWN_M2M_EMPLOYEE_ROLES_XTABLE,
        joinColumns = @JoinColumn(name = XTABLE_EMPLOYEE_ROLES__EMPLOYEE_COL),
        inverseJoinColumns = @JoinColumn(name = XTABLE_EMPLOYEE_ROLES__ROLE_COL))
//    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="blStandardElements")
//    @BatchSize(size = 50)
//    @AdminPresentationCollection(addType = AddMethodType.LOOKUP, friendlyName = "roleListTitle", manyToField = "allEmployees",
//        operationTypes = @AdminPresentationOperationTypes(removeType = OperationType.NONDESTRUCTIVEREMOVE))
    protected Set<BuRole> roles;
    public static final String OWN_M2M_ALL_ROLES = "roles";
    public static final String OWN_M2M_EMPLOYEE_ROLES_XTABLE = "TB_EMPLOYEE_ROLE_XREF";
    public static final String XTABLE_EMPLOYEE_ROLES__EMPLOYEE_COL = "TB_EMPLOYEE_ID";
    public static final String XTABLE_EMPLOYEE_ROLES__ROLE_COL = "TB_ROLE_ID";

    @FieldRelation(RelationType.TwoWay_ManyToManyOwner)
    @ManyToMany(fetch = FetchType.LAZY, targetEntity = BuPermissionImpl.class)
    @JoinTable(name = OWN_M2M_EMPLOYEE_PERMS_XTABLE,
        joinColumns = @JoinColumn(name = XTABLE_EMPLOYEE_PERMS__EMPLOYEE_COL),
        inverseJoinColumns = @JoinColumn(name = XTABLE_EMPLOYEE_PERMS__PERM_COL))
//    @BatchSize(size = 50)
//    @AdminPresentationCollection(addType = AddMethodType.LOOKUP,
//        friendlyName = "permissionListTitle",
//        customCriteria = "includeFriendlyOnly",
//        manyToField = "allEmployees",
//        operationTypes = @AdminPresentationOperationTypes(removeType = OperationType.NONDESTRUCTIVEREMOVE))
    protected Set<BuPermission> permissions;
    public static final String OWN_M2M_ALL_PERMS = "permissions";
    public static final String OWN_M2M_EMPLOYEE_PERMS_XTABLE = "TB_EMPLOYEE_PERM_XREF";
    public static final String XTABLE_EMPLOYEE_PERMS__EMPLOYEE_COL = "TB_EMPLOYEE_ID";
    public static final String XTABLE_EMPLOYEE_PERMS__PERM_COL = "TB_PERM_ID";

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public Employee setId(Long id) {
        this.id = id;
        return this;
    }

    @Override
    public Long getPersonId() {
        return personId;
    }

    @Override
    public Employee setUserId(Long userId) {
        this.personId = this.personId;
        return this;
    }

    @Override
    public BusinessUnit getBusinessUnit() {
        return businessUnit;
    }

    @Override
    public Employee setOrganization(BusinessUnit businessUnit) {
        this.businessUnit = businessUnit;
        return this;
    }

    @Override
    public EmployeeStatus getActiveStatus() {
        return activeStatus;
    }

    @Override
    public EmployeeImpl setActiveStatus(EmployeeStatus activeStatus) {
        this.activeStatus = activeStatus;
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Employee setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Employee setTitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    public EmployeeOwnedSetting getSetting() {
        return setting;
    }

    @Override
    public Employee setSetting(EmployeeOwnedSetting setting) {
        this.setting = setting;
        return this;
    }
//
//    @Override
//    public Set<Role> getRoles() {
//        return roles;
//    }
//
//    @Override
//    public void setRoles(Set<Role> roles) {
//        this.roles = roles;
//    }
//
//    @Override
//    public Set<Permission> getPermissions() {
//        return permissions;
//    }
//
//    @Override
//    public void setPermissions(Set<Permission> permissions) {
//        this.permissions = permissions;
//    }
}
