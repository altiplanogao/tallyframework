package com.taoswork.tallybook.testmaterial.jpa.domain.business;

import com.taoswork.tallybook.datadomain.base.entity.Persistable;

public interface IProduct extends Persistable {
    int getId();

    void setId(int id);

    String getName();

    void setName(String name);

    Double getPrice();

    void setPrice(Double price);
}
