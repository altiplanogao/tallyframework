package com.taoswork.tallybook.testmaterial.mongo.domain.zoo;

import com.taoswork.tallybook.datadomain.base.entity.PersistEntity;
import org.mongodb.morphia.annotations.Entity;

/**
 * Created by Gao Yuan on 2015/10/2.
 */
@Entity
@PersistEntity(permissionGuardian = Fish.class)
public class Fish extends Animal {
//    protected static final String ID_GENERATOR_NAME = "FishImpl_IdGen";
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.TABLE, generator = ID_GENERATOR_NAME)
//    @TableGenerator(
//        name = ID_GENERATOR_NAME,
//        table = "ID_GENERATOR_TABLE",
//        initialValue = 1)
//    @Column(name = "ID")
//    @PresentationField(group = "General")
//    protected Long id;

    protected Integer ageInDays;

    public Integer getAgeInDays() {
        return ageInDays;
    }

    public Fish setAgeInDays(Integer ageInDays) {
        this.ageInDays = ageInDays;
        return this;
    }
}
