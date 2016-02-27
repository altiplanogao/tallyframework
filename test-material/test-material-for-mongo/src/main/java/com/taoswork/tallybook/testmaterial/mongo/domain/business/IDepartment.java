package com.taoswork.tallybook.testmaterial.mongo.domain.business;

import com.taoswork.tallybook.datadomain.onmongo.PersistableDocument;
import com.taoswork.tallybook.testmaterial.mongo.domain.business.embed.EmployeeName;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IDepartment extends PersistableDocument {

    String getName();

    void setName(String name);

    Collection<IEmployee> getEmployees();

    void setEmployees(Collection<IEmployee> employees);

    List<IEmployee> getEmployeesList();

    void setEmployeesList(List<IEmployee> employeesList);

    Map<String, IEmployee> getEmployeesByCubicle();

    void setEmployeesByCubicle(Map<String, IEmployee> employeesByCubicle);

    Map<Integer, IEmployee> getEmployeesMap();

    void setEmployeesMap(Map<Integer, IEmployee> employeesMap);

    Map<EmployeeName, IEmployee> getEmployeesByName();

    void setEmployeesByName(Map<EmployeeName, IEmployee> employeesByName);

    Map getEmployeesByUnTypedId();

    void setEmployeesByUnTypedId(Map employeesByUnTypedId);

    Map getEmployeesByUnTypedName();

    void setEmployeesByUnTypedName(Map employeesByUnTypedName);
}
