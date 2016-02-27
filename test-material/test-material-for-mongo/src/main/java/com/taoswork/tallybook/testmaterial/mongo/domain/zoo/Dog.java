package com.taoswork.tallybook.testmaterial.mongo.domain.zoo;


import org.mongodb.morphia.annotations.Entity;

/**
 * Created by Gao Yuan on 2015/10/2.
 */
@Entity
public class Dog extends Animal {
//    protected static final String ID_GENERATOR_NAME = "DogImpl_IdGen";
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

    protected Integer ageInYears;

    public Integer getAgeInYears() {
        return ageInYears;
    }

    public Dog setAgeInYears(Integer ageInYears) {
        this.ageInYears = ageInYears;
        return this;
    }
}
