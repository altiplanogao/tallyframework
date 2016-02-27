package com.taoswork.tallybook.testmaterial.mongo.domain.business;

import com.taoswork.tallybook.datadomain.onmongo.PersistableDocument;
import com.taoswork.tallybook.testmaterial.mongo.domain.business.enumtype.EmployeeType;
import com.taoswork.tallybook.testmaterial.mongo.domain.common.Address;
import com.taoswork.tallybook.testmaterial.mongo.domain.common.PhoneType;
import com.taoswork.tallybook.testmaterial.mongo.domain.nature.Citizen;

import java.util.*;

public interface IEmployee extends PersistableDocument {
    Citizen getCitizen();

    void setCitizen(Citizen citizen);

    String getName();

    void setName(String name);

    String getFirstName();

    void setFirstName(String firstName);

    String getLastName();

    void setLastName(String lastName);

    String getTranslatedName();

    void setTranslatedName(String translatedName);

    Set<String> getNickNameSet();

    void setNickNameSet(Set<String> nickNameSet);

    Set getNickNameSetNonType();

    void setNickNameSetNonType(Set nickNameSetNonType);

    List<String> getNickNameList();

    void setNickNameList(List<String> nickNameList);

//    String[] getNickNameArray();
//
//    void setNickNameArray(String[] nickNameArray);

    long getSalary();

    void setSalary(long salary);

    EmployeeType getType();

    void setType(EmployeeType type);

    Long getCitizenId();

    void setCitizenId(Long citizenId);

    Calendar getDob();

    void setDob(Calendar dob);

    Date getStartDate();

    void setStartDate(Date startDate);

    String getComments();

    void setComments(String comments);

    byte[] getPicture();

    void setPicture(byte[] picture);

    IParkingSpace getParkingSpace();

    void setParkingSpace(IParkingSpace parkingSpace);

    IDepartment getDepartment();

    void setDepartment(IDepartment department);

    String getCube();

    void setCube(String cube);

    Collection<IProject> getProjects();

    void setProjects(Collection<IProject> projects);

    Collection getVacationBookings();

    void setVacationBookings(Collection vacationBookings);

    Set<String> getNickNames();

    void setNickNames(Set<String> nickNames);

    Address getAddress();

    void setAddress(Address address);

    Map<PhoneType, String> getPhoneNumbers();

    void setPhoneNumbers(Map<PhoneType, String> phoneNumbers);
}
