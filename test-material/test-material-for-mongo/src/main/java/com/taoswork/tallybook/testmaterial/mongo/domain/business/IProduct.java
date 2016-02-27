package com.taoswork.tallybook.testmaterial.mongo.domain.business;

import com.taoswork.tallybook.datadomain.onmongo.PersistableDocument;

public interface IProduct extends PersistableDocument {

    String getName();

    void setName(String name);

    Double getPrice();

    void setPrice(Double price);
}
