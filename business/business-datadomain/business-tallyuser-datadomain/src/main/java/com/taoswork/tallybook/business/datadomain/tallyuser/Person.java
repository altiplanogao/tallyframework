package com.taoswork.tallybook.business.datadomain.tallyuser;

import java.io.Serializable;

/**
 * Created by Gao Yuan on 2015/5/8.
 */
public interface Person extends Serializable {
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
