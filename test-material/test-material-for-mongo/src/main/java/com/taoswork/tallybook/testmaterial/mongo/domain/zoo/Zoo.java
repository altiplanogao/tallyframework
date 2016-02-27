package com.taoswork.tallybook.testmaterial.mongo.domain.zoo;

import com.taoswork.tallybook.datadomain.onmongo.AbstractDocument;
import com.taoswork.tallybook.testmaterial.mongo.domain.common.Address;
import com.taoswork.tallybook.testmaterial.mongo.domain.zoo.impl.ZooKeeperImpl;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

import java.util.Collection;
import java.util.List;

@Entity
public class Zoo extends AbstractDocument {

    protected String name;

    @Embedded
    private Address address;


    @Reference
    private List<Dormitory> dormitories;

    @Reference
    private Collection<ZooKeeperImpl> zooKeepers;
}
