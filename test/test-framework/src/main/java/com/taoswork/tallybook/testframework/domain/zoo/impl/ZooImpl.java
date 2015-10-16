package com.taoswork.tallybook.testframework.domain.zoo.impl;

import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationField;
import com.taoswork.tallybook.testframework.domain.common.Address;
import com.taoswork.tallybook.testframework.domain.zoo.Dormitory;
import com.taoswork.tallybook.testframework.domain.zoo.Zoo;
import com.taoswork.tallybook.testframework.domain.zoo.ZooKeeper;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "ZOO")
public class ZooImpl implements Zoo {
    protected static final String ID_GENERATOR_NAME = "Zoo_IdGen";

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = ID_GENERATOR_NAME)
    @TableGenerator(
        name = ID_GENERATOR_NAME,
        table = "ID_GENERATOR_TABLE",
        initialValue = 1)
    @Column(name = "ID")
    @PresentationField(group = "General")
    protected Long id;

    @Column(name = "NAME")
    protected String name;

    @Embedded
    private Address address;

    @OneToMany(targetEntity = DormitoryImpl.class, mappedBy = "zoo")
    private List<Dormitory> dormitories;

    @OneToMany(targetEntity = ZooKeeperImpl.class, mappedBy = "zoo")
    private Collection<ZooKeeper> zooKeepers;
}
