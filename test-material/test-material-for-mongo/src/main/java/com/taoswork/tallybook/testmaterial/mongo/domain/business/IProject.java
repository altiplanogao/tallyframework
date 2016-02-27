package com.taoswork.tallybook.testmaterial.mongo.domain.business;

import com.taoswork.tallybook.datadomain.onmongo.PersistableDocument;

import java.util.Collection;

public interface IProject extends PersistableDocument {

    String getName();

    void setName(String name);

    Collection<IEmployee> getEmployees();

    void setEmployees(Collection<IEmployee> employees);
}
