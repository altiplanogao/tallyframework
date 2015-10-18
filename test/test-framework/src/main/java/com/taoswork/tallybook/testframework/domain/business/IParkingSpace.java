package com.taoswork.tallybook.testframework.domain.business;

import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;
import com.taoswork.tallybook.testframework.domain.business.impl.EmployeeImpl;

public interface IParkingSpace extends Persistable {
    int getId();

    void setId(int id);

    int getLot();

    void setLot(int lot);

    String getLocation();

    void setLocation(String location);

    IEmployee getEmployee();

    void setEmployee(IEmployee employee);

    Object getEmployeeObj();

    void setEmployeeObj(Object employeeObj);

    EmployeeImpl getEmployeeImpl();

    void setEmployeeImpl(EmployeeImpl employeeImpl);
}
