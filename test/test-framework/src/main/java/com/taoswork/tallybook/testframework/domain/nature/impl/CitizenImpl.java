package com.taoswork.tallybook.testframework.domain.nature.impl;

import com.taoswork.tallybook.testframework.domain.business.IDepartment;
import com.taoswork.tallybook.testframework.domain.business.IEmployee;
import com.taoswork.tallybook.testframework.domain.business.IParkingSpace;
import com.taoswork.tallybook.testframework.domain.business.IProject;
import com.taoswork.tallybook.testframework.domain.business.embed.EmployeeNameX;
import com.taoswork.tallybook.testframework.domain.business.embed.VacationEntry;
import com.taoswork.tallybook.testframework.domain.business.enumtype.EmployeeType;
import com.taoswork.tallybook.testframework.domain.business.impl.DepartmentImpl;
import com.taoswork.tallybook.testframework.domain.business.impl.ParkingSpaceImpl;
import com.taoswork.tallybook.testframework.domain.business.impl.ProjectImpl;
import com.taoswork.tallybook.testframework.domain.common.Address;
import com.taoswork.tallybook.testframework.domain.common.PhoneType;
import com.taoswork.tallybook.testframework.domain.nature.ICitizen;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="CITIZEN")
public class CitizenImpl implements ICitizen {
    @Id
    private int id;

    @Column(name="F_NAME")
    private String firstName;
    @Column(name="L_NAME")
    private String lastName;

    @Temporal(TemporalType.DATE)
    private Calendar birth;

    @Column(name = "ID_CARD_NO")
    private String idCardNo;

    @ElementCollection
    @CollectionTable(name="EMP_PHONE")
    @MapKeyColumn(name="PHONE_TYPE")
    @Column(name="PHONE_NUM")
    private Map<PhoneType, String> phoneNumbers;
// ...
}