package com.taoswork.tallybook.testframework.domain.business.impl;

import com.taoswork.tallybook.testframework.domain.business.IEmployee;
import com.taoswork.tallybook.testframework.domain.business.IParkingSpace;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PARK_SPACE")
public class ParkingSpaceImpl implements IParkingSpace{
    @Id
    private int id;
    private int lot;
    private String location;

    @OneToOne(targetEntity = EmployeeImpl.class, mappedBy="parkingSpace")
    private IEmployee employee;

    @OneToOne(targetEntity = EmployeeImpl.class, mappedBy="parkingSpace")
    private Object employeeObj;

    @OneToOne(mappedBy="parkingSpace")
    private EmployeeImpl employeeImpl;
// ...
}