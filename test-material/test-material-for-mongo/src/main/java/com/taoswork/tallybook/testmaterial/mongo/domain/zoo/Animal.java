package com.taoswork.tallybook.testmaterial.mongo.domain.zoo;

import com.taoswork.tallybook.datadomain.base.entity.PersistEntity;
import com.taoswork.tallybook.datadomain.base.presentation.PresentationClass;
import com.taoswork.tallybook.datadomain.base.presentation.PresentationField;
import com.taoswork.tallybook.datadomain.base.presentation.typed.PresentationEnum;
import com.taoswork.tallybook.datadomain.onmongo.AbstractDocument;
import com.taoswork.tallybook.testmaterial.mongo.domain.common.Gender;
import org.mongodb.morphia.annotations.Embedded;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@PersistEntity(instantiable = false)
public abstract class Animal extends AbstractDocument {

    protected String name;
    //list, map, set, array

    protected Set<String> nickNameSet;

    protected List<String> nickNameList;

//    //in blob
//    @Column(name = "NICKNAME_ARRAY")
//    @Lob
//    protected String[] nickNameArray;

    @Embedded
    protected Food mainFood;

    protected Collection foodCollection;

    protected List foodList;

    protected Set foodSet;

    protected List<Food> foodListTyped;

    protected Set<Food> foodSetTyped;

//  NOT WELL SUPPORTED
//    @Column
//    @Lob
//    protected Food[] foodArray;


    @PresentationField()
    @PresentationEnum(enumeration = Gender.class)
    private Gender gender;

//    @ManyToOne
//    private ZooKeeperImpl keeper;


    public String getName() {
        return name;
    }

    public Animal setName(String name) {
        this.name = name;
        return this;
    }

    public Gender getGender() {
        return gender;
    }

    public Animal setGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public Set<String> getNickNameSet() {
        return nickNameSet;
    }

    public Animal setNickNameSet(Set<String> nickNameSet) {
        this.nickNameSet = nickNameSet;
        return this;
    }

    public List<String> getNickNameList() {
        return nickNameList;
    }

    public Animal setNickNameList(List<String> nickNameList) {
        this.nickNameList = nickNameList;
        return this;
    }
}
