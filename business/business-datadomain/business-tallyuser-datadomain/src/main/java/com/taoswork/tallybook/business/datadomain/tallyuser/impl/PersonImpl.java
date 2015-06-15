package com.taoswork.tallybook.business.datadomain.tallyuser.impl;


import com.taoswork.tallybook.business.datadomain.tallyuser.Person;
import com.taoswork.tallybook.business.datadomain.tallyuser.TallyUserDataDomain;
import com.taoswork.tallybook.dynamic.datadomain.presentation.PresentationClass;
import com.taoswork.tallybook.dynamic.datadomain.presentation.PresentationField;
import com.taoswork.tallybook.dynamic.datadomain.presentation.client.SupportedFieldType;
import com.taoswork.tallybook.dynamic.datadomain.presentation.client.Visibility;

import javax.persistence.*;

/**
 * Created by Gao Yuan on 2015/4/15.
 */
@Entity
@Table(name = "TB_PERSON")
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
            initialValue = 1)
    @Column(name = "ID")
    @PresentationField(group = "General", order = 1, fieldType = SupportedFieldType.ID, visibility = Visibility.HIDDEN_ALL)
    protected Long id;

    @Column(name = "NAME", nullable = false)
    @PresentationField(group = "General", order = 2, fieldType = SupportedFieldType.NAME)
    protected String name;

    @Column(name = "EMAIL")
    @PresentationField(fieldType = SupportedFieldType.EMAIL)
    protected String email;

    @Column(name = "MOBILE", length = 20)
    @PresentationField(fieldType = SupportedFieldType.PHONE)
    protected String mobile;

    @Column(name = "UUID", unique = true)
    @PresentationField(fieldType = SupportedFieldType.CODE, visibility = Visibility.GRID_HIDE)
    protected String uuid;

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
    public String getUuid() {
        return uuid;
    }

    @Override
    public PersonImpl setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    @Override
    public String toString() {
        return "Person[#" + id +
                " '" + name + '\'' +
                " {" + uuid + '}' +
                ']';
    }
}
