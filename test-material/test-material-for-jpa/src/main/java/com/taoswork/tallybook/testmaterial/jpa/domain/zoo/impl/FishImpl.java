package com.taoswork.tallybook.testmaterial.jpa.domain.zoo.impl;

import com.taoswork.tallybook.testmaterial.jpa.domain.zoo.Fish;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Gao Yuan on 2015/10/2.
 */
@Entity
@Table(name = "FISH")
public class FishImpl extends AnimalImpl implements Fish {
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

    @Column(name = "AGE_DAY", nullable = true)
    protected Integer ageInDays;

    public Long getId() {
        return id;
    }

    public FishImpl setId(Long id) {
        this.id = id;
        return this;
    }

    public Integer getAgeInDays() {
        return ageInDays;
    }

    public FishImpl setAgeInDays(Integer ageInDays) {
        this.ageInDays = ageInDays;
        return this;
    }
}
