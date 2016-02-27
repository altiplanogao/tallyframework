package com.taoswork.tallybook.testmaterial.mongo.domain.business.impl;

import com.taoswork.tallybook.datadomain.onmongo.AbstractDocument;
import org.mongodb.morphia.annotations.Entity;

@Entity
public class DepartmentImpl extends AbstractDocument {
}

//implements IDepartment {
//
//    private String name;
//
//    @Reference
//    private Collection<IEmployee> employees;
//
//    @Reference
//    private List<IEmployee> employeesList;
//
////
////    @OneToMany(mappedBy="department")
////    @OrderColumn
////    private EmployeeImpl[] employeesArray;
//
//    @OneToMany(targetEntity = EmployeeImpl.class, mappedBy="department")
//    @MapKeyColumn(name="CUB_ID")
//    private Map<String, IEmployee> employeesByCubicle;
//
//    @OneToMany(targetEntity = EmployeeImpl.class, mappedBy="department")
//    @MapKey(name="id")
//    private Map<Integer, IEmployee> employeesMap;
//
//    @OneToMany(targetEntity = EmployeeImpl.class, mappedBy="department")
//    private Map<EmployeeName, IEmployee> employeesByName;
//
//    @OneToMany(targetEntity = EmployeeImpl.class, mappedBy="department")
//    private Map<EmployeeNameX, IEmployee> employeesByNameX;
//
//    @OneToMany(targetEntity = EmployeeImpl.class, mappedBy="department")
//    @MapKey//default is to use the identifier attribute
//    private Map employeesByUnTypedId;
//
//    @OneToMany(targetEntity = EmployeeImpl.class, mappedBy="department")
//    @MapKey(name = "name")
//    private Map employeesByUnTypedName;
//
//    @Override
//    public String getName() {
//        return name;
//    }
//
//    @Override
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    @Override
//    public Collection<IEmployee> getEmployees() {
//        return employees;
//    }
//
//    @Override
//    public void setEmployees(Collection<IEmployee> employees) {
//        this.employees = employees;
//    }
//
//    @Override
//    public List<IEmployee> getEmployeesList() {
//        return employeesList;
//    }
//
//    @Override
//    public void setEmployeesList(List<IEmployee> employeesList) {
//        this.employeesList = employeesList;
//    }
//
//    @Override
//    public Map<String, IEmployee> getEmployeesByCubicle() {
//        return employeesByCubicle;
//    }
//
//    @Override
//    public void setEmployeesByCubicle(Map<String, IEmployee> employeesByCubicle) {
//        this.employeesByCubicle = employeesByCubicle;
//    }
//
//    @Override
//    public Map<Integer, IEmployee> getEmployeesMap() {
//        return employeesMap;
//    }
//
//    @Override
//    public void setEmployeesMap(Map<Integer, IEmployee> employeesMap) {
//        this.employeesMap = employeesMap;
//    }
//
//    @Override
//    public Map<EmployeeName, IEmployee> getEmployeesByName() {
//        return employeesByName;
//    }
//
//    @Override
//    public void setEmployeesByName(Map<EmployeeName, IEmployee> employeesByName) {
//        this.employeesByName = employeesByName;
//    }
//
//    @Override
//    public Map<EmployeeNameX, IEmployee> getEmployeesByNameX() {
//        return employeesByNameX;
//    }
//
//    @Override
//    public void setEmployeesByNameX(Map<EmployeeNameX, IEmployee> employeesByNameX) {
//        this.employeesByNameX = employeesByNameX;
//    }
//
//    @Override
//    public Map getEmployeesByUnTypedId() {
//        return employeesByUnTypedId;
//    }
//
//    @Override
//    public void setEmployeesByUnTypedId(Map employeesByUnTypedId) {
//        this.employeesByUnTypedId = employeesByUnTypedId;
//    }
//
//    @Override
//    public Map getEmployeesByUnTypedName() {
//        return employeesByUnTypedName;
//    }
//
//    @Override
//    public void setEmployeesByUnTypedName(Map employeesByUnTypedName) {
//        this.employeesByUnTypedName = employeesByUnTypedName;
//    }
//}
