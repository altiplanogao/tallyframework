package com.taoswork.tallybook.testframework.domain.impl;

import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationClass;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationField;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.Visibility;
import com.taoswork.tallybook.testframework.domain.TPerson;

import javax.persistence.*;

/**
 * Created by Gao Yuan on 2015/4/15.
 */
@Entity
@Table(name = "TB_TEST_PERSON")
@NamedQueries({
        @NamedQuery(name="Person.ReadPersonByName",
                query="SELECT person FROM com.taoswork.tallybook.testframework.domain.TPerson person" +
                        " WHERE person.name = :name"),
        @NamedQuery(name="Person.ReadPersonByUUID",
                query="SELECT person FROM com.taoswork.tallybook.testframework.domain.TPerson person" +
                        " WHERE person.uuid = :uuid"),
        @NamedQuery(name="Person.ReadPersonByEmail",
                query="SELECT person FROM com.taoswork.tallybook.testframework.domain.TPerson person" +
                        " WHERE person.email = :email"),
        @NamedQuery(name="Person.ReadPersonByMobile",
                query="SELECT person FROM com.taoswork.tallybook.testframework.domain.TPerson person" +
                        " WHERE person.mobile = :mobile"),
        @NamedQuery(name="Person.ListPerson",
                query="SELECT person FROM com.taoswork.tallybook.testframework.domain.TPerson person"),

})
@PresentationClass(
)
public class TPersonImpl
        implements TPerson {

    protected static final String ID_GENERATOR_NAME = "PersonImpl_IdGen";

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = ID_GENERATOR_NAME)
    @TableGenerator(
            name = ID_GENERATOR_NAME,
            table = "ID_GENERATOR_TABLE",
            initialValue = 1)
    @Column(name = "ID")
    @PresentationField(group = "General")
    protected Long id;

    @Column(name = "NAME", nullable = false)
    protected String name;

    @Column(name = "EMAIL")
    protected String email;

    @Column(name = "MOBILE", length = 20)
    @PresentationField(fieldType = FieldType.PHONE)
    protected String mobile;

    @Column(name = "UUID", unique = true)
    @PresentationField(visibility = Visibility.GRID_HIDE)
    protected String uuid;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public TPersonImpl setId(Long id) {
        this.id = id;
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public TPersonImpl setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public TPersonImpl setEmail(String emailAddress) {
        this.email = emailAddress;
        return this;
    }

    @Override
    public String getMobile() {
        return mobile;
    }

    @Override
    public TPersonImpl setMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
    public TPersonImpl setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    @Override
    public String toString() {
        return "TPerson[#" + id +
                " '" + name + '\'' +
                " {" + uuid + '}' +
                ']';
    }
}
