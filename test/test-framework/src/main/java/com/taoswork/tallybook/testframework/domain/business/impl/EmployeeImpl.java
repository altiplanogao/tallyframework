package com.taoswork.tallybook.testframework.domain.business.impl;

import com.taoswork.tallybook.testframework.domain.business.IDepartment;
import com.taoswork.tallybook.testframework.domain.business.IEmployee;
import com.taoswork.tallybook.testframework.domain.business.IParkingSpace;
import com.taoswork.tallybook.testframework.domain.business.IProject;
import com.taoswork.tallybook.testframework.domain.business.embed.EmployeeNameX;
import com.taoswork.tallybook.testframework.domain.business.embed.VacationEntry;
import com.taoswork.tallybook.testframework.domain.business.enumtype.EmployeeType;
import com.taoswork.tallybook.testframework.domain.common.Address;
import com.taoswork.tallybook.testframework.domain.common.PhoneType;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="EMPLOYEE")
public class EmployeeImpl implements IEmployee{
    @Id
    private int id;
    private String name;

    @Column(name="F_NAME")
    private String firstName;
    @Column(name="L_NAME")
    private String lastName;

    @Embedded
    private EmployeeNameX nameX;

    transient private String translatedName;

    @ElementCollection
    protected Set<String> nickNameSet;

    @ElementCollection(targetClass = String.class)
    protected Set nickNameSetNonType;

    @ElementCollection
    protected List<String> nickNameList;

    //in blob
    @Column(name = "NICKNAME_ARRAY")
    @Lob
    protected String[] nickNameArray;

    private long salary;

    @Enumerated(EnumType.STRING)
    private EmployeeType type;

    @Temporal(TemporalType.DATE)
    private Calendar dob;

    @Temporal(TemporalType.DATE)
    @Column(name="S_DATE")
    private Date startDate;

    @Column(name="COMM")
    @Basic(fetch=FetchType.LAZY)
    private String comments;

    @Basic(fetch=FetchType.LAZY)
    @Lob @Column(name="PIC")
    private byte[] picture;

    @OneToOne(targetEntity = ParkingSpaceImpl.class)
    @JoinColumn(name="PSPACE_ID")
    private IParkingSpace parkingSpace;

    @ManyToOne(targetEntity = DepartmentImpl.class)
    @JoinColumn(name="DEPT_ID")
    private IDepartment department;

    @Column(name = "CUB_ID")
    private String cube;

    @ManyToMany(targetEntity = ProjectImpl.class)
    private Collection<IProject> projects;

    // ...
    @ElementCollection(targetClass=VacationEntry.class)
    private Collection vacationBookings;

    @ElementCollection
    private Set<String> nickNames;

    @Embedded private Address address;

    @ElementCollection
    @CollectionTable(name="EMP_PHONE")
    @MapKeyColumn(name="PHONE_TYPE")
    @Column(name="PHONE_NUM")
    private Map<PhoneType, String> phoneNumbers;
// ...
}