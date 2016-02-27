package com.taoswork.tallybook.business.datadomain.tallyadmin;

import com.taoswork.tallybook.authority.solution.domain.user.PersonAuthority;
import com.taoswork.tallybook.business.datadomain.tallyadmin.valueprotect.AdminEmployeeGate;
import com.taoswork.tallybook.business.datadomain.tallyuser.AccountStatus;
import com.taoswork.tallybook.business.datadomain.tallyuser.Person;
import com.taoswork.tallybook.business.datadomain.tallyuser.impl.PersonImpl;
import com.taoswork.tallybook.datadomain.base.entity.PersistEntity;
import com.taoswork.tallybook.datadomain.base.entity.PersistField;
import com.taoswork.tallybook.datadomain.base.presentation.PresentationField;
import com.taoswork.tallybook.datadomain.base.presentation.FieldType;
import com.taoswork.tallybook.datadomain.base.presentation.Visibility;
import com.taoswork.tallybook.datadomain.base.presentation.typed.PresentationExternalForeignKey;

/**
 * Created by Gao Yuan on 2016/2/15.
 */
@PersistEntity(nameOverride = "admin",
        valueGates = {AdminEmployeeGate.class}
)
public class AdminEmployee extends PersonAuthority {
    //protected Big
//    @Transient
//    private transient Person person;

    protected String title;

    protected AccountStatus status;

    @PersistField(fieldType = FieldType.EXTERNAL_FOREIGN_KEY)
    @PresentationField(visibility = Visibility.VISIBLE_ALL)
    @PresentationExternalForeignKey(targetType = PersonImpl.class, dataField = "person")
    private Long personId;
    public static final String FN_PERSON_ID = "personId";

    private transient Person person;

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
}
