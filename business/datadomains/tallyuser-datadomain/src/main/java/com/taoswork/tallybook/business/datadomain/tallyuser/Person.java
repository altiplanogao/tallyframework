package com.taoswork.tallybook.business.datadomain.tallyuser;

import com.taoswork.tallybook.business.datadomain.tallyuser.gate.PersonGate;
import com.taoswork.tallybook.business.datadomain.tallyuser.validation.PersonValidator;
import com.taoswork.tallybook.datadomain.base.entity.PersistEntity;
import com.taoswork.tallybook.datadomain.onmongo.PersistableDocument;

import java.util.Date;

@PersistEntity(
        validators = {PersonValidator.class},
        valueGates = {PersonGate.class}
)
public interface Person extends PersistableDocument {

    String getName();

    Person setName(String name);

    Boolean isActive();

    Person setActive(Boolean active);

    String getUuid();

    Person setUuid(String uuid);

    Gender getGender();

    Person setGender(Gender gender);

    String getEmail();

    Person setEmail(String email);

    String getMobile();

    Person setMobile(String mobile);

    Date getCreateDate();

    void setCreateDate(Date createDate);
}
