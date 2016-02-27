package com.taoswork.tallybook.testmaterial.jpa.domain.zoo;

import java.util.Date;

public interface Cage {
    Long getId();

    Cage setId(Long id);

    String getSn();

    Cage setSn(String sn);

    Date getCreateDate();

    Cage setCreateDate(Date createDate);
}
