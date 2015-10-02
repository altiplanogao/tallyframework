package com.taoswork.tallybook.testframework.domain.impl;

import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationField;
import com.taoswork.tallybook.testframework.domain.TAnimal;
import com.taoswork.tallybook.testframework.domain.TFish;

import javax.persistence.*;

/**
 * Created by Gao Yuan on 2015/10/2.
 */
@Entity
@Table(name = "TB_TEST_DOG")
public class TFishImpl implements TFish {
    protected static final String ID_GENERATOR_NAME = "FishImpl_IdGen";

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = ID_GENERATOR_NAME)
    @TableGenerator(
        name = ID_GENERATOR_NAME,
        table = "ID_GENERATOR_TABLE",
        initialValue = 1)
    @Column(name = "ID")
    @PresentationField(group = "General")
    protected Long id;

    @Column(name = "NICKNAME", nullable = false)
    protected String nickName;

    @Column(name = "AGE_DAY", nullable = true)
    protected Integer ageInDays;

    public Long getId() {
        return id;
    }

    public TFishImpl setId(Long id) {
        this.id = id;
        return this;
    }

    public String getNickName() {
        return nickName;
    }

    public TFishImpl setNickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public Integer getAgeInDays() {
        return ageInDays;
    }

    public TFishImpl setAgeInDays(Integer ageInDays) {
        this.ageInDays = ageInDays;
        return this;
    }
}
