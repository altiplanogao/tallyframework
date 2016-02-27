package com.taoswork.tallybook.testmaterial.jpa.domain.zoo.impl;

import com.taoswork.tallybook.testmaterial.jpa.domain.zoo.Dog;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Gao Yuan on 2015/10/2.
 */
@Entity
@Table(name = "DOG")
public class DogImpl extends AnimalImpl implements Dog {
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

    @Column(name = "AGE_YEAR", nullable = true)
    protected Integer ageInYears;

    public Integer getAgeInYears() {
        return ageInYears;
    }

    public Dog setAgeInYears(Integer ageInYears) {
        this.ageInYears = ageInYears;
        return this;
    }
}
