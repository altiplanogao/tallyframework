package com.taoswork.tallybook.testframework.domain;

import java.io.Serializable;

/**
 * Created by Gao Yuan on 2015/5/8.
 */
public interface TPerson extends Serializable {
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
