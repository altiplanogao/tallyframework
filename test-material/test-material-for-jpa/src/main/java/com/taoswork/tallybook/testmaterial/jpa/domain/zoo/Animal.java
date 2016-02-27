package com.taoswork.tallybook.testmaterial.jpa.domain.zoo;

import com.taoswork.tallybook.datadomain.base.entity.Persistable;
import com.taoswork.tallybook.testmaterial.jpa.domain.common.Gender;

import java.util.List;
import java.util.Set;

/**
 * Created by Gao Yuan on 2015/10/2.
 */
public interface Animal extends Persistable {

    Long getId();

    Animal setId(Long id);

    String getName();

    Animal setName(String name);

    Set<String> getNickNameSet();

    Animal setNickNameSet(Set<String> nickNames);

    Gender getGender();

    Animal setGender(Gender gender);

    List<String> getNickNameList();

    Animal setNickNameList(List<String> nickNameList);
}
