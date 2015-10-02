package com.taoswork.tallybook.testframework.domain;

import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;

/**
 * Created by Gao Yuan on 2015/5/8.
 */
public interface TPerson extends Persistable {
    Long getId();

    TPerson setId(Long id);

    String getName();

    TPerson setName(String name);

    String getUuid();

    TPerson setUuid(String uuid);

    String getEmail();

    TPerson setEmail(String email);

    String getMobile();

    TPerson setMobile(String mobile);
}
