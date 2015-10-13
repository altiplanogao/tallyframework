package com.taoswork.tallybook.business.datadomain.tallyadmin.impl;

import com.taoswork.tallybook.business.datadomain.tallyadmin.AdminEmployee;
import com.taoswork.tallybook.business.datadomain.tallyadmin.TallyAdminDataDomain;
import com.taoswork.tallybook.business.datadomain.tallyadmin.security.permission.AdminPermission;
import com.taoswork.tallybook.business.datadomain.tallyadmin.security.permission.AdminRole;
import com.taoswork.tallybook.business.datadomain.tallyadmin.security.permission.impl.AdminPermissionImpl;
import com.taoswork.tallybook.business.datadomain.tallyadmin.security.permission.impl.AdminRoleImpl;
import com.taoswork.tallybook.business.datadomain.tallyuser.Person;
import com.taoswork.tallybook.general.authority.core.authentication.user.AccountStatus;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationField;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.Visibility;
import com.taoswork.tallybook.general.datadomain.support.presentation.relation.FieldRelation;
import com.taoswork.tallybook.general.datadomain.support.presentation.relation.RelationType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Gao Yuan on 2015/5/10.
 */
@Entity
@Table(name = "ADMIN_EMPLOYEE")
@NamedQueries(
        @NamedQuery(name = "AdminEmployee.ReadEmployeeByPersonId",
                query = "SELECT employee FROM com.taoswork.tallybook.business.datadomain.tallyadmin.AdminEmployee employee" +
                        " WHERE employee.personId = :personId")
)
public class AdminEmployeeImpl implements AdminEmployee {

    protected static final String ID_GENERATOR_NAME = "AdminEmployeeImpl_IdGen";

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = ID_GENERATOR_NAME)
    @TableGenerator(
            name = ID_GENERATOR_NAME,
            table =  TallyAdminDataDomain.ID_GENERATOR_TABLE_NAME,
            initialValue = 1
    )
    @Column(name = "ID")
    protected Long id;

    @Column(name = "PERSON_ID", nullable = false, unique = true)
    //
    protected Long personId;
//    @Transient
//    private transient Person person;

    @Column(name = "TITLE")
    @PresentationField(group = "General", order =3, fieldType = FieldType.STRING, visibility = Visibility.VISIBLE_ALL)
    protected String title;

    @Embedded
    protected AccountStatus status;

    /** All roles that this user has */
    @FieldRelation(RelationType.TwoWay_ManyToManyOwner)
    @ManyToMany(fetch = FetchType.LAZY, targetEntity = AdminRoleImpl.class)
    @JoinTable(name = OWN_M2M_EMPLOYEE_ROLES_XTABLE,
        joinColumns = @JoinColumn(name = XTABLE_EMPLOYEE_ROLES__EMPLOYEE_COL),
        inverseJoinColumns = @JoinColumn(name = XTABLE_EMPLOYEE_ROLES__ROLE_COL))
//    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="blStandardElements")
//    @BatchSize(size = 50)
//    @AdminPresentationCollection(addType = AddMethodType.LOOKUP, friendlyName = "roleListTitle", manyToField = "allEmployees",
//        operationTypes = @AdminPresentationOperationTypes(removeType = OperationType.NONDESTRUCTIVEREMOVE))
    protected Set<AdminRole> allRoles = new HashSet<AdminRole>();
    public static final String OWN_M2M_ALL_ROLES = "allRoles";
    public static final String OWN_M2M_EMPLOYEE_ROLES_XTABLE = "ADMIN_EMPLOYEE_ROLE_XREF";
    public static final String XTABLE_EMPLOYEE_ROLES__EMPLOYEE_COL = "ADMIN_EMPLOYEE_ID";
    public static final String XTABLE_EMPLOYEE_ROLES__ROLE_COL = "ADMIN_ROLE_ID";

    @FieldRelation(RelationType.TwoWay_ManyToManyOwner)
    @ManyToMany(fetch = FetchType.LAZY, targetEntity = AdminPermissionImpl.class)
    @JoinTable(name = OWN_M2M_EMPLOYEE_PERMS_XTABLE,
        joinColumns = @JoinColumn(name = XTABLE_EMPLOYEE_PERMS__EMPLOYEE_COL),
        inverseJoinColumns = @JoinColumn(name = XTABLE_EMPLOYEE_PERMS__PERM_COL))
//    @BatchSize(size = 50)
//    @AdminPresentationCollection(addType = AddMethodType.LOOKUP,
//        friendlyName = "permissionListTitle",
//        customCriteria = "includeFriendlyOnly",
//        manyToField = "allEmployees",
//        operationTypes = @AdminPresentationOperationTypes(removeType = OperationType.NONDESTRUCTIVEREMOVE))
    protected Set<AdminPermission> allPermissions = new HashSet<AdminPermission>();
    public static final String OWN_M2M_ALL_PERMS = "allPermissions";
    public static final String OWN_M2M_EMPLOYEE_PERMS_XTABLE = "ADMIN_EMPLOYEE_PERM_XREF";
    public static final String XTABLE_EMPLOYEE_PERMS__EMPLOYEE_COL = "ADMIN_EMPLOYEE_ID";
    public static final String XTABLE_EMPLOYEE_PERMS__PERM_COL = "ADMIN_PERM_ID";


    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getPersonId() {
        return personId;
    }

    @Override
    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public AccountStatus getStatus() {
        return status;
    }

    @Override
    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    @Override
    public Set<AdminRole> getAllRoles() {
        return allRoles;
    }

    @Override
    public void setAllRoles(Set<AdminRole> allRoles) {
        this.allRoles = allRoles;
    }

    @Override
    public Set<AdminPermission> getAllPermissions() {
        return allPermissions;
    }

    @Override
    public void setAllPermissions(Set<AdminPermission> allPermissions) {
        this.allPermissions = allPermissions;
    }
}
