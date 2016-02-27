package com.taoswork.tallybook.testmaterial.jpa.domain.zoo.impl;

import com.taoswork.tallybook.testmaterial.jpa.domain.common.Address;
import com.taoswork.tallybook.testmaterial.jpa.domain.zoo.Dormitory;
import com.taoswork.tallybook.testmaterial.jpa.domain.zoo.Zoo;
import com.taoswork.tallybook.testmaterial.jpa.domain.zoo.ZooKeeper;

import javax.persistence.*;

@Entity
@Table(name = "DORMITORY")
public class DormitoryImpl implements Dormitory {
    @Id
    @Column(name = "ID")
    protected Long id;

    @Column(name = "NAME")
    protected String name;

    @Embedded
    private Address address;

    @ManyToOne(targetEntity = ZooImpl.class)
    private Zoo zoo;

    //The guy lives in this dormitory
    @OneToOne(targetEntity = ZooKeeperImpl.class)
    protected ZooKeeper zooKeeper;
    public static final String ZOOKEEPER_COLUMN_NAME = "zooKeeper";

}
