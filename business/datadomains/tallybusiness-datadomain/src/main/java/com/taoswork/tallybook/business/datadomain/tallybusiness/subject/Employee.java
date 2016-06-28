package com.taoswork.tallybook.business.datadomain.tallybusiness.subject;

import com.taoswork.tallycheck.authority.solution.domain.user.GroupAuthority;
import com.taoswork.tallycheck.authority.solution.domain.user.UserAuthority;
import com.taoswork.tallycheck.datadomain.tallyuser.Person;
import com.taoswork.tallycheck.datadomain.tallyuser.impl.PersonImpl;
import com.taoswork.tallycheck.datadomain.base.entity.CollectionField;
import com.taoswork.tallycheck.datadomain.base.entity.CollectionMode;
import com.taoswork.tallycheck.datadomain.base.entity.PersistEntity;
import com.taoswork.tallycheck.datadomain.base.entity.PersistField;
import com.taoswork.tallycheck.datadomain.base.presentation.FieldType;
import com.taoswork.tallycheck.datadomain.base.presentation.PresentationField;
import com.taoswork.tallycheck.datadomain.base.presentation.typed.PresentationExternalForeignKey;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;
import org.mongodb.morphia.annotations.Transient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/4/14.
 */
@Entity("employee")
@PersistEntity(value = "employee",
        asDefaultPermissionGuardian = true
)
public class Employee extends
        //IPermissionUser,
        UserAuthority<Role> {

    @Reference(lazy = true)
    @PersistField(required = true, fieldType = FieldType.FOREIGN_KEY)
    protected Bu bu;

    @PersistField(required = true, fieldType = FieldType.EXTERNAL_FOREIGN_KEY)
    @PresentationExternalForeignKey(targetType = PersonImpl.class, dataField = "person")
    protected Long personId;
    @Transient
    private transient Person person;

    protected EmployeeStatus activeStatus;

    @PersistField(fieldType = FieldType.NAME, required = true)
    @PresentationField(order = 2)
    protected String name;

    @PersistField(fieldType = FieldType.STRING, required = false)
    @PresentationField(order = 2)
    protected String title;

    @Embedded
    protected EmployeeOwnedSetting setting;

    @Reference
    @CollectionField(mode = CollectionMode.Lookup)
    List<Role> groups = new ArrayList<Role>();

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Employee setUserId(Long personId) {
        this.personId = personId;
        return this;
    }

    public Bu getBu() {
        return bu;
    }

    public void setBu(Bu bu) {
        this.bu = bu;
    }

    public Employee setOrganization(Bu bu) {
        this.bu = bu;
        return this;
    }

    public EmployeeStatus getActiveStatus() {
        return activeStatus;
    }

    public Employee setActiveStatus(EmployeeStatus activeStatus) {
        this.activeStatus = activeStatus;
        return this;
    }

    public String getName() {
        return name;
    }

    public Employee setName(String name) {
        this.name = name;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Employee setTitle(String title) {
        this.title = title;
        return this;
    }

    public EmployeeOwnedSetting getSetting() {
        return setting;
    }

    public Employee setSetting(EmployeeOwnedSetting setting) {
        this.setting = setting;
        return this;
    }

    public List<Role> getGroups() {
        return groups;
    }

    public void setGroups(List<Role> groups) {
        this.groups = groups;
    }

    @Override
    public Collection<? extends GroupAuthority> theGroups() {
        return getGroups();
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
