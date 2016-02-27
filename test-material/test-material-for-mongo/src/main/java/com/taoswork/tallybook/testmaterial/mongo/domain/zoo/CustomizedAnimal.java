package com.taoswork.tallybook.testmaterial.mongo.domain.zoo;

import org.mongodb.morphia.annotations.Entity;

/**
 * Created by Gao Yuan on 2015/10/2.
 */
@Entity
public class CustomizedAnimal extends Animal {
    protected String species;

    protected Integer age;

    public String getSpecies() {
        return species;
    }

    public CustomizedAnimal setSpecies(String species) {
        this.species = species;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public CustomizedAnimal setAge(Integer age) {
        this.age = age;
        return this;
    }
}
