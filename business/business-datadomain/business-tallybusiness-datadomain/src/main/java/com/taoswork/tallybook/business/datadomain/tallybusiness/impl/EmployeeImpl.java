package com.taoswork.tallybook.business.datadomain.tallybusiness.impl;

import com.taoswork.tallybook.business.datadomain.tallybusiness.*;
import com.taoswork.tallybook.business.datadomain.tallyuser.Person;
import com.taoswork.tallybook.datadomain.base.entity.PersistField;
import com.taoswork.tallybook.datadomain.base.presentation.PresentationField;
import com.taoswork.tallybook.datadomain.base.presentation.FieldType;
import com.taoswork.tallybook.datadomain.base.presentation.Visibility;
import com.taoswork.tallybook.datadomain.onjpa.annotation.FieldRelation;
import com.taoswork.tallybook.datadomain.onjpa.annotation.RelationType;

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
    @JoinColumn(name = "host_id", nullable = false, updatable = false)
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

    @OneToOne(targetEntity = EmployeeOwnedSettingImpl.class)
    @JoinColumn(name = "OWN_SET_ID")
    protected EmployeeOwnedSetting setting;

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
    public Employee setUserId(Long personId) {
        this.personId = personId;
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
//    public Set<KPermission> getPermissions() {
//        return permissions;
//    }
//
//    @Override
//    public void setPermissions(Set<KPermission> permissions) {
//        this.permissions = permissions;
//    }
}
