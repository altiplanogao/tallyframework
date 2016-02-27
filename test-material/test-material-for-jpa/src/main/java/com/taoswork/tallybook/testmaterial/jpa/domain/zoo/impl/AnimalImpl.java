package com.taoswork.tallybook.testmaterial.jpa.domain.zoo.impl;

import com.taoswork.tallybook.datadomain.base.entity.PersistEntity;
import com.taoswork.tallybook.datadomain.base.entity.PersistField;
import com.taoswork.tallybook.datadomain.base.presentation.PresentationClass;
import com.taoswork.tallybook.datadomain.base.presentation.PresentationField;
import com.taoswork.tallybook.datadomain.base.presentation.typed.PresentationEnum;
import com.taoswork.tallybook.testmaterial.jpa.domain.common.Gender;
import com.taoswork.tallybook.testmaterial.jpa.domain.common.GenderToStringConverter;
import com.taoswork.tallybook.testmaterial.jpa.domain.zoo.Animal;
import com.taoswork.tallybook.testmaterial.jpa.domain.zoo.Food;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@PersistEntity(instantiable = false)
public abstract class AnimalImpl implements Animal {

    @Id
    @Column(name = "ID")
    protected Long id;

    @Column(name = "ANAME", nullable = false)
    @PersistField(required = true)
    protected String name;
    //list, map, set, array

    @ElementCollection
    protected Set<String> nickNameSet;

    @ElementCollection
    protected List<String> nickNameList;

//    //in blob
//    @Column(name = "NICKNAME_ARRAY")
//    @Lob
//    protected String[] nickNameArray;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "type", column = @Column(name = "MAIN_FOOD_TYPE")),
            @AttributeOverride(name = "name", column = @Column(name = "MAIN_FOOD_NAME")),
            @AttributeOverride(name = "description", column = @Column(name = "MAIN_FOOD_DESC")),
    })
    protected Food mainFood;

    @ElementCollection(targetClass = Food.class)
    protected Collection foodCollection;

    @ElementCollection(targetClass = Food.class)
    protected List foodList;

    @ElementCollection(targetClass = Food.class)
    protected Set foodSet;

    @ElementCollection
    protected List<Food> foodListTyped;

    @ElementCollection
    protected Set<Food> foodSetTyped;

//  NOT WELL SUPPORTED
//    @Column
//    @Lob
//    protected Food[] foodArray;


    @Column(name = "GENDER")
    @PresentationField()
    @PresentationEnum(enumeration = Gender.class)
    @Convert(converter = GenderToStringConverter.class)
    private Gender gender;

//    @ManyToOne
//    private ZooKeeper keeper;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public Animal setId(Long id) {
        this.id = id;
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Animal setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public Gender getGender() {
        return gender;
    }

    @Override
    public Animal setGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    @Override
    public Set<String> getNickNameSet() {
        return nickNameSet;
    }

    @Override
    public Animal setNickNameSet(Set<String> nickNameSet) {
        this.nickNameSet = nickNameSet;
        return this;
    }

    @Override
    public List<String> getNickNameList() {
        return nickNameList;
    }

    @Override
    public Animal setNickNameList(List<String> nickNameList) {
        this.nickNameList = nickNameList;
        return this;
    }
}
