package com.taoswork.tallybook.business.datadomain.tallyuser;

import com.taoswork.tallybook.business.datadomain.tallyuser.gate.PersonValueGate;
import com.taoswork.tallybook.business.datadomain.tallyuser.validation.PersonValidator;
import com.taoswork.tallybook.general.datadomain.support.entity.PersistEntity;
import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;

import java.util.Date;

@PersistEntity(
    validators = {PersonValidator.class},
    valueGates = {PersonValueGate.class}
)
public interface Person extends Persistable {
    Long getId();

    Person setId(Long id);

    String getName();

    Person setName(String name);

    Boolean isActive();

    Person setActive(Boolean active);

    String getUuid();

    Person setUuid(String uuid);

    Gender getGender();

    void setGender(Gender gender);

    String getEmail();

    Person setEmail(String email);

    String getMobile();

    Person setMobile(String mobile);

    Date getCreateDate();

    void setCreateDate(Date createDate);
}
