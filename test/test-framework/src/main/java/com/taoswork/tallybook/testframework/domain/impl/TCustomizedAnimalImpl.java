package com.taoswork.tallybook.testframework.domain.impl;

import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationField;
import com.taoswork.tallybook.testframework.domain.TAnimal;
import com.taoswork.tallybook.testframework.domain.TCustomizedAnimal;

import javax.persistence.*;

/**
 * Created by Gao Yuan on 2015/10/2.
 */
@Entity
@Table(name = "TB_TEST_CUSTOM_ANIMAL")
public class TCustomizedAnimalImpl implements TCustomizedAnimal {
    protected static final String ID_GENERATOR_NAME = "CustomAnimalImpl_IdGen";

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = ID_GENERATOR_NAME)
    @TableGenerator(
        name = ID_GENERATOR_NAME,
        table = "ID_GENERATOR_TABLE",
        initialValue = 1)
    @Column(name = "ID")
    @PresentationField(group = "General")
    protected Long id;

    @Column(name = "SPECIES", nullable = false)
    protected String species;

    @Column(name = "NICKNAME", nullable = false)
    protected String nickName;

    @Column(name = "AGE", nullable = true)
    protected Integer age;

    public Long getId() {
        return id;
    }

    public TCustomizedAnimalImpl setId(Long id) {
        this.id = id;
        return this;
    }

    public String getSpecies() {
        return species;
    }

    public TCustomizedAnimalImpl setSpecies(String species) {
        this.species = species;
        return this;
    }

    public String getNickName() {
        return nickName;
    }

    public TCustomizedAnimalImpl setNickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public TCustomizedAnimalImpl setAge(Integer age) {
        this.age = age;
        return this;
    }
}
