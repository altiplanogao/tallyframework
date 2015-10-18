package com.taoswork.tallybook.testframework.domain.business;

import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;

public interface IProduct extends Persistable {
    int getId();

    void setId(int id);

    String getName();

    void setName(String name);

    Double getPrice();

    void setPrice(Double price);
}
