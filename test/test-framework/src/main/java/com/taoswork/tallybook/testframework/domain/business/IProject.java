package com.taoswork.tallybook.testframework.domain.business;

import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;

import java.util.Collection;

public interface IProject extends Persistable {
    int getId();

    void setId(int id);

    String getName();

    void setName(String name);

    Collection<IEmployee> getEmployees();

    void setEmployees(Collection<IEmployee> employees);
}
