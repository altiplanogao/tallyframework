package com.taoswork.tallybook.business.datadomain.tallyuser.impl;


import com.taoswork.tallybook.business.datadomain.tallyuser.Gender;
import com.taoswork.tallybook.business.datadomain.tallyuser.Person;
import com.taoswork.tallybook.business.datadomain.tallyuser.TallyUserDataDomain;
import com.taoswork.tallybook.business.datadomain.tallyuser.convert.GenderToStringConverter;
import com.taoswork.tallybook.dynamic.datadomain.converters.BooleanToStringConverter;
import com.taoswork.tallybook.general.datadomain.support.entity.PersistField;
import com.taoswork.tallybook.general.datadomain.support.entity.handyprotect.valuegate.FieldCreateDateValueGate;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationClass;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationField;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.Visibility;
import com.taoswork.tallybook.general.datadomain.support.presentation.typed.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TB_PERSON",
    indexes = {
        @Index(name = "idx_birth", columnList = "BIRTH"),
        @Index(name = "idx_email", columnList = "EMAIL"),
        @Index(name = "idx_mobile", columnList = "MOBILE"),
        @Index(name = "idx_uuid", columnList = "UUID")})
@NamedQueries({
        @NamedQuery(name="Person.ReadPersonByName",
                query="SELECT person FROM com.taoswork.tallybook.business.datadomain.tallyuser.Person person" +
                        " WHERE person.name = :name"),
        @NamedQuery(name="Person.ReadPersonByUUID",
                query="SELECT person FROM com.taoswork.tallybook.business.datadomain.tallyuser.Person person" +
                        " WHERE person.uuid = :uuid"),
        @NamedQuery(name="Person.ReadPersonByEmail",
                query="SELECT person FROM com.taoswork.tallybook.business.datadomain.tallyuser.Person person" +
                        " WHERE person.email = :email"),
        @NamedQuery(name="Person.ReadPersonByMobile",
                query="SELECT person FROM com.taoswork.tallybook.business.datadomain.tallyuser.Person person" +
                        " WHERE person.mobile = :mobile"),
        @NamedQuery(name="Person.ListPerson",
                query="SELECT person FROM com.taoswork.tallybook.business.datadomain.tallyuser.Person person"),

})
@PresentationClass(
)
public class PersonImpl
        implements Person {

    protected static final String ID_GENERATOR_NAME = "PersonImpl_IdGen";

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = ID_GENERATOR_NAME)
    @TableGenerator(
            name = ID_GENERATOR_NAME,
            table = TallyUserDataDomain.ID_GENERATOR_TABLE_NAME,
            initialValue = 0)
    @Column(name = "ID")
    @PersistField(fieldType = FieldType.ID)
    @PresentationField(group = "General", order = 1, visibility = Visibility.HIDDEN_ALL)
    protected Long id;

    @Column(name = "NAME", nullable = false)
    @PersistField(fieldType = FieldType.NAME)
    @PresentationField(group = "General", order = 2)
    protected String name;

    @Column(name = "GENDER", nullable = false, length = 1
        ,columnDefinition = "VARCHAR(1) DEFAULT '" + Gender.DEFAULT_CHAR + "'"
    )
    @PersistField(fieldType = FieldType.ENUMERATION)
    @PresentationField(group = "General", order = 3)
    @PresentationEnum(enumeration = Gender.class)
    @Convert(converter = GenderToStringConverter.class)
    protected Gender gender = Gender.unknown;

    @Column(name = "BIRTH")
    @Temporal(TemporalType.TIMESTAMP)
    @PersistField(fieldType = FieldType.DATE)
    @PresentationField(order = 4)
    @PresentationDate(mode = DateMode.DateTime, cellMode = DateCellMode.Date)
    public Date birth = new Date();

    @Column(name = "EMAIL", length = 120)
    @PersistField(fieldType = FieldType.EMAIL)
    @PresentationField(group = "General", order = 5)
    protected String email;

    @Column(name = "MOBILE", length = 20)
    @PersistField(fieldType = FieldType.PHONE)
    @PresentationField(group = "General", order = 6)
    protected String mobile;

    @Column(name = "ACTIVE", nullable = false, length = 2,
        columnDefinition = "VARCHAR(2) DEFAULT 'Y'")
    @Convert(converter = BooleanToStringConverter.class)
    @PersistField(fieldType = FieldType.BOOLEAN)
    @PresentationField(order = 7)
    @PresentationBoolean(mode = BooleanMode.YesNo)
    protected Boolean active = true;

    @Column(name = "UUID", unique = true)
    @PersistField(fieldType = FieldType.CODE, editable = false)
    @PresentationField(visibility = Visibility.HIDDEN_ALL)
    protected String uuid;

    @Column(name = "CREATE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    @PersistField(fieldType = FieldType.DATE, fieldValueGateOverride = FieldCreateDateValueGate.class, skipDefaultFieldValueGate = true, editable = false)
    @PresentationField(order = 99, visibility = Visibility.GRID_HIDE)
    @PresentationDate(mode = DateMode.DateTime, cellMode = DateCellMode.Date)
    public Date createDate = new Date();

    public PersonImpl() {
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public PersonImpl setId(Long id) {
        this.id = id;
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public PersonImpl setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public Gender getGender() {
        return gender;
    }

    @Override
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public PersonImpl setEmail(String emailAddress) {
        this.email = emailAddress;
        return this;
    }

    @Override
    public String getMobile() {
        return mobile;
    }

    @Override
    public PersonImpl setMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    @Override
    public Boolean isActive() {
        return active;
    }

    @Override
    public PersonImpl setActive(Boolean active) {
        this.active = active;
        return this;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
    public PersonImpl setUuid(String uuid) {
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
        return "Person[#" + id +
                " '" + name + '\'' +
                " {" + uuid + '}' +
                ']';
    }
}
