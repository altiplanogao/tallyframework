package com.taoswork.tallybook.business.datadomain.tallyuser.impl;


import com.taoswork.tallybook.business.datadomain.tallyuser.Gender;
import com.taoswork.tallybook.business.datadomain.tallyuser.Person;
import com.taoswork.tallybook.datadomain.base.entity.PersistEntity;
import com.taoswork.tallybook.datadomain.base.entity.PersistField;
import com.taoswork.tallybook.datadomain.base.entity.handyprotect.valuegate.CreateDateGate;
import com.taoswork.tallybook.datadomain.base.presentation.PresentationClass;
import com.taoswork.tallybook.datadomain.base.presentation.PresentationField;
import com.taoswork.tallybook.datadomain.base.presentation.FieldType;
import com.taoswork.tallybook.datadomain.base.presentation.Visibility;
import com.taoswork.tallybook.datadomain.base.presentation.typed.*;
import com.taoswork.tallybook.datadomain.onmongo.AbstractDocument;
import org.mongodb.morphia.annotations.Entity;

import java.util.Date;

@Entity("person")
@PersistEntity("person")

//@Table(name = "TB_PERSON",
//        indexes = {
//                @Index(name = "idx_birth", columnList = "BIRTH"),
//                @Index(name = "idx_email", columnList = "EMAIL"),
//                @Index(name = "idx_mobile", columnList = "MOBILE"),
//                @Index(name = "idx_uuid", columnList = "UUID")})
//@NamedQueries({
//        @NamedQuery(name = "Person.ReadPersonByName",
//                query = "SELECT person FROM com.taoswork.tallybook.business.datadomain.tallyuser.Person person" +
//                        " WHERE person.name = :name"),
//        @NamedQuery(name = "Person.ReadPersonByUUID",
//                query = "SELECT person FROM com.taoswork.tallybook.business.datadomain.tallyuser.Person person" +
//                        " WHERE person.uuid = :uuid"),
//        @NamedQuery(name = "Person.ReadPersonByEmail",
//                query = "SELECT person FROM com.taoswork.tallybook.business.datadomain.tallyuser.Person person" +
//                        " WHERE person.email = :email"),
//        @NamedQuery(name = "Person.ReadPersonByMobile",
//                query = "SELECT person FROM com.taoswork.tallybook.business.datadomain.tallyuser.Person person" +
//                        " WHERE person.mobile = :mobile"),
//        @NamedQuery(name = "Person.ListPerson",
//                query = "SELECT person FROM com.taoswork.tallybook.business.datadomain.tallyuser.Person person"),
//
//})
@PresentationClass(
)
public class PersonImpl
        extends AbstractDocument
        implements Person {

    @PersistField(fieldType = FieldType.NAME, required = true)
    @PresentationField(group = "General", order = 2)
    protected String name;

    @PersistField(fieldType = FieldType.ENUMERATION, required = true)
    @PresentationField(group = "General", order = 3)
    @PresentationEnum(enumeration = Gender.class)
    protected Gender gender = Gender.unknown;

    @PersistField(fieldType = FieldType.DATE)
    @PresentationField(order = 4)
    @PresentationDate(mode = DateMode.DateTime, cellMode = DateCellMode.Date)
    public Date birth = null;

    @PersistField(fieldType = FieldType.EMAIL, length = 120)
    @PresentationField(group = "General", order = 5)
    protected String email;

    @PersistField(fieldType = FieldType.PHONE, length = 20)
    @PresentationField(group = "General", order = 6)
    protected String mobile;

    @PersistField(fieldType = FieldType.BOOLEAN, required = true)
    @PresentationField(order = 7)
    @PresentationBoolean(mode = BooleanMode.YesNo)
    protected Boolean active = true;

    @PersistField(fieldType = FieldType.CODE, editable = false)
    @PresentationField(visibility = Visibility.HIDDEN_ALL)
    protected String uuid;

    @PersistField(fieldType = FieldType.DATE, fieldValueGateOverride = CreateDateGate.class, skipDefaultFieldValueGate = true, editable = false)
    @PresentationField(order = 99, visibility = Visibility.GRID_HIDE)
    @PresentationDate(mode = DateMode.DateTime, cellMode = DateCellMode.Date)
    public Date createDate = new Date();

    public PersonImpl() {
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Person setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public Gender getGender() {
        return gender;
    }

    @Override
    public Person setGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public Person setEmail(String emailAddress) {
        this.email = emailAddress;
        return this;
    }

    @Override
    public String getMobile() {
        return mobile;
    }

    @Override
    public Person setMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    @Override
    public Boolean isActive() {
        return active;
    }

    @Override
    public Person setActive(Boolean active) {
        this.active = active;
        return this;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
    public Person setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    @Override
    public Date getCreateDate() {
        return createDate;
    }

    @Override
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "Person[#" + this.getId() +
                " '" + name + '\'' +
                " {" + uuid + '}' +
                ']';
    }
}
