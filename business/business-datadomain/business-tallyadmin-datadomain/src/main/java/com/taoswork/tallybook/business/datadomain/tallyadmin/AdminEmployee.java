package com.taoswork.tallybook.business.datadomain.tallyadmin;

import com.taoswork.tallybook.authority.solution.domain.user.GroupAuthority;
import com.taoswork.tallybook.authority.solution.domain.user.UserAuthority;
import com.taoswork.tallybook.business.datadomain.tallyadmin.valueprotect.AdminEmployeeGate;
import com.taoswork.tallybook.business.datadomain.tallyuser.AccountStatus;
import com.taoswork.tallybook.business.datadomain.tallyuser.Person;
import com.taoswork.tallybook.business.datadomain.tallyuser.impl.PersonImpl;
import com.taoswork.tallybook.datadomain.base.entity.CollectionField;
import com.taoswork.tallybook.datadomain.base.entity.CollectionMode;
import com.taoswork.tallybook.datadomain.base.entity.PersistEntity;
import com.taoswork.tallybook.datadomain.base.entity.PersistField;
import com.taoswork.tallybook.datadomain.base.presentation.PresentationField;
import com.taoswork.tallybook.datadomain.base.presentation.FieldType;
import com.taoswork.tallybook.datadomain.base.presentation.Visibility;
import com.taoswork.tallybook.datadomain.base.presentation.typed.PresentationExternalForeignKey;
import org.mongodb.morphia.annotations.Reference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Gao Yuan on 2016/2/15.
 */
@PersistEntity(value = "admin",
        valueGates = {AdminEmployeeGate.class}
)
public class AdminEmployee extends UserAuthority<AdminGroup> {
    //protected Big
//    @Transient
//    private transient Person person;

    private String name;

    protected String title;

    protected AccountStatus status;

    @Reference
    @CollectionField(mode = CollectionMode.Lookup)
    List<AdminGroup> groups = new ArrayList<AdminGroup>();

    @PersistField(fieldType = FieldType.EXTERNAL_FOREIGN_KEY)
    @PresentationField(visibility = Visibility.VISIBLE_ALL)
    @PresentationExternalForeignKey(targetType = PersonImpl.class, dataField = "person")
    private Long personId;
    public static final String FN_PERSON_ID = "personId";

    private transient Person person;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Long getPersonId() {
        return personId;
    }

    public List<AdminGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<AdminGroup> groups) {
        this.groups = groups;
    }

    @Override
    public Collection<? extends GroupAuthority> theGroups() {
        return getGroups();
    }
}
