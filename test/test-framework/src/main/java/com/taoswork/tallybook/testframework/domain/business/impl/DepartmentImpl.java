package com.taoswork.tallybook.testframework.domain.business.impl;

import com.taoswork.tallybook.testframework.domain.business.IDepartment;
import com.taoswork.tallybook.testframework.domain.business.IEmployee;
import com.taoswork.tallybook.testframework.domain.business.embed.EmployeeName;
import com.taoswork.tallybook.testframework.domain.business.embed.EmployeeNameX;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "DEPT")
public class DepartmentImpl implements IDepartment {
    @Id
    private int id;

    private String name;

    @OneToMany(targetEntity = EmployeeImpl.class, mappedBy="department")
    private Collection<IEmployee> employees;

    @OneToMany(targetEntity = EmployeeImpl.class, mappedBy="department")
    @OrderBy("cube ASC")
    private List<IEmployee> employeesList;

//
//    @OneToMany(mappedBy="department")
//    @OrderColumn
//    private EmployeeImpl[] employeesArray;

    @OneToMany(targetEntity = EmployeeImpl.class, mappedBy="department")
    @MapKeyColumn(name="CUB_ID")
    private Map<String, IEmployee> employeesByCubicle;

    @OneToMany(targetEntity = EmployeeImpl.class, mappedBy="department")
    @MapKey(name="id")
    private Map<Integer, IEmployee> employeesMap;

    @OneToMany(targetEntity = EmployeeImpl.class, mappedBy="department")
    private Map<EmployeeName, IEmployee> employeesByName;

    @OneToMany(targetEntity = EmployeeImpl.class, mappedBy="department")
    private Map<EmployeeNameX, IEmployee> employeesByNameX;

    @OneToMany(targetEntity = EmployeeImpl.class, mappedBy="department")
    @MapKey//default is to use the identifier attribute
    private Map employeesByUnTypedId;

    @OneToMany(targetEntity = EmployeeImpl.class, mappedBy="department")
    @MapKey(name = "name")
    private Map employeesByUnTypedName;

}
