package com.taoswork.tallybook.business.datadomain.tallyuser;

import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;

/**
 * Created by Gao Yuan on 2015/5/8.
 */
public interface Person extends Persistable {
    Long getId();

    Person setId(Long id);

    String getName();

    Person setName(String name);

    String getUuid();

    Person setUuid(String uuid);

    Gender getGender();

    void setGender(Gender gender);

    String getEmail();

    Person setEmail(String email);

    String getMobile();

    Person setMobile(String mobile);
}
