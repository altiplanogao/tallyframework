package com.taoswork.tallybook.testmaterial.jpa.domain.zoo.impl;

import com.taoswork.tallybook.datadomain.base.entity.MapField;
import com.taoswork.tallybook.datadomain.base.entity.MapMode;
import com.taoswork.tallybook.datadomain.base.entity.PersistField;
import com.taoswork.tallybook.datadomain.base.presentation.PresentationClass;
import com.taoswork.tallybook.datadomain.base.presentation.PresentationField;
import com.taoswork.tallybook.datadomain.base.presentation.FieldType;
import com.taoswork.tallybook.datadomain.base.presentation.Visibility;
import com.taoswork.tallybook.testmaterial.jpa.domain.business.impl.PhoneNumberByType;
import com.taoswork.tallybook.testmaterial.jpa.domain.common.PhoneType;
import com.taoswork.tallybook.testmaterial.jpa.domain.zoo.Dormitory;
import com.taoswork.tallybook.testmaterial.jpa.domain.zoo.Zoo;
import com.taoswork.tallybook.testmaterial.jpa.domain.zoo.ZooKeeper;

import javax.persistence.*;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/4/15.
 */
@Entity
@Table(name = "ZOOKEEPER",
        indexes = {
                @Index(name = "idx_uuid", columnList = "UUID")})
@NamedQueries({
        @NamedQuery(name = "Person.ReadPersonByName",
                query = "SELECT person FROM com.taoswork.tallybook.testmaterial.jpa.domain.zoo.ZooKeeper person" +
                        " WHERE person.name = :name"),
        @NamedQuery(name = "Person.ReadPersonByUUID",
                query = "SELECT person FROM com.taoswork.tallybook.testmaterial.jpa.domain.zoo.ZooKeeper person" +
                        " WHERE person.uuid = :uuid"),
        @NamedQuery(name = "Person.ReadPersonByEmail",
                query = "SELECT person FROM com.taoswork.tallybook.testmaterial.jpa.domain.zoo.ZooKeeper person" +
                        " WHERE person.email = :email"),
        @NamedQuery(name = "Person.ReadPersonByMobile",
                query = "SELECT person FROM com.taoswork.tallybook.testmaterial.jpa.domain.zoo.ZooKeeper person" +
                        " WHERE person.mobile = :mobile"),
        @NamedQuery(name = "Person.ListPerson",
                query = "SELECT person FROM com.taoswork.tallybook.testmaterial.jpa.domain.zoo.ZooKeeper person"),

})
@PresentationClass(
)
public class ZooKeeperImpl
        implements ZooKeeper {

    protected static final String ID_GENERATOR_NAME = "ZooKeeper_IdGen";

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
    @PersistField(required = true)
    protected String name;

    @Column(name = "EMAIL")
    protected String email;

    @Column(name = "MOBILE", length = 20)
    @PersistField(fieldType = FieldType.PHONE, length = 20)
    @PresentationField
    protected String mobile;

    @ElementCollection
    @CollectionTable(name = "KEEPERS_PHONE")
    @MapKeyColumn(name = "PHONE_TYPE")
    @Column(name = "PHONE_NUMBER")
    @MapField(mode = MapMode.Basic, entryDelegate = PhoneNumberByType.class)
    protected Map<PhoneType, String> phoneNumbers;

    @OneToOne(targetEntity = DormitoryImpl.class, mappedBy = DormitoryImpl.ZOOKEEPER_COLUMN_NAME)
    protected Dormitory dormitory;

    @Column(name = "UUID", unique = true)
    @PresentationField(visibility = Visibility.GRID_HIDE)
    protected String uuid;

    @ManyToOne(targetEntity = ZooImpl.class)
    protected Zoo zoo;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public ZooKeeperImpl setId(Long id) {
        this.id = id;
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ZooKeeperImpl setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public ZooKeeperImpl setEmail(String emailAddress) {
        this.email = emailAddress;
        return this;
    }

    @Override
    public String getMobile() {
        return mobile;
    }

    @Override
    public ZooKeeperImpl setMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
    public ZooKeeperImpl setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    @Override
    public String toString() {
        return "ZooKeeper[#" + id +
                " '" + name + '\'' +
                " {" + uuid + '}' +
                ']';
    }
}
