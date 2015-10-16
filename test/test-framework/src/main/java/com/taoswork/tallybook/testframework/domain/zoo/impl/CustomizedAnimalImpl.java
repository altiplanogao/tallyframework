package com.taoswork.tallybook.testframework.domain.zoo.impl;

import com.taoswork.tallybook.testframework.domain.zoo.CustomizedAnimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Gao Yuan on 2015/10/2.
 */
@Entity
@Table(name = "CUSTOM_ANIMAL")
public class CustomizedAnimalImpl extends AnimalImpl implements CustomizedAnimal {
    protected static final String ID_GENERATOR_NAME = "CustomAnimalImpl_IdGen";

    @Column(name = "SPECIES", nullable = false)
    protected String species;

    @Column(name = "AGE", nullable = true)
    protected Integer age;

    public String getSpecies() {
        return species;
    }

    public CustomizedAnimalImpl setSpecies(String species) {
        this.species = species;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public CustomizedAnimalImpl setAge(Integer age) {
        this.age = age;
        return this;
    }
}
