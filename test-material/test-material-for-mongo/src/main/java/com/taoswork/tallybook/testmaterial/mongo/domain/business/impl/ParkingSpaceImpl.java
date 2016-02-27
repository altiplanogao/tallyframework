package com.taoswork.tallybook.testmaterial.mongo.domain.business.impl;

import com.taoswork.tallybook.datadomain.onmongo.AbstractDocument;
import org.mongodb.morphia.annotations.Entity;

@Entity
public class ParkingSpaceImpl extends AbstractDocument {
}

//implements IParkingSpace{
//    protected static final String ID_GENERATOR_NAME = "ParkingSpaceImpl_IdGen";
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.TABLE, generator = ID_GENERATOR_NAME)
//    @TableGenerator(
//        name = ID_GENERATOR_NAME,
//        table = TallyMockupDataDomain.ID_GENERATOR_TABLE_NAME,
//        initialValue = 0)
//    @Column(name = "ID")
//    private int id;
//    private int lot;
//    private String location;
//
//    @OneToOne(targetEntity = EmployeeImpl.class, mappedBy="parkingSpace")
//    private IEmployee employee;
//
//    @OneToOne(targetEntity = EmployeeImpl.class, mappedBy="parkingSpace")
//    private Object employeeObj;
//
//    @OneToOne(mappedBy="parkingSpace")
//    private EmployeeImpl employeeImpl;
//
//    @Override
//    public int getId() {
//        return id;
//    }
//
//    @Override
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    @Override
//    public int getLot() {
//        return lot;
//    }
//
//    @Override
//    public void setLot(int lot) {
//        this.lot = lot;
//    }
//
//    @Override
//    public String getLocation() {
//        return location;
//    }
//
//    @Override
//    public void setLocation(String location) {
//        this.location = location;
//    }
//
//    @Override
//    public IEmployee getEmployee() {
//        return employee;
//    }
//
//    @Override
//    public void setEmployee(IEmployee employee) {
//        this.employee = employee;
//    }
//
//    @Override
//    public Object getEmployeeObj() {
//        return employeeObj;
//    }
//
//    @Override
//    public void setEmployeeObj(Object employeeObj) {
//        this.employeeObj = employeeObj;
//    }
//
//    @Override
//    public EmployeeImpl getEmployeeImpl() {
//        return employeeImpl;
//    }
//
//    @Override
//    public void setEmployeeImpl(EmployeeImpl employeeImpl) {
//        this.employeeImpl = employeeImpl;
//    }
//}