package com.taoswork.tallybook.testmaterial.jpa.domain.business;

import com.taoswork.tallybook.datadomain.base.entity.Persistable;
import com.taoswork.tallybook.testmaterial.jpa.domain.business.embed.EmployeeName;
import com.taoswork.tallybook.testmaterial.jpa.domain.business.embed.EmployeeNameX;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IDepartment extends Persistable {
    Long getId();

    void setId(Long id);

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

    Map<EmployeeNameX, IEmployee> getEmployeesByNameX();

    void setEmployeesByNameX(Map<EmployeeNameX, IEmployee> employeesByNameX);

    Map getEmployeesByUnTypedId();

    void setEmployeesByUnTypedId(Map employeesByUnTypedId);

    Map getEmployeesByUnTypedName();

    void setEmployeesByUnTypedName(Map employeesByUnTypedName);
}
