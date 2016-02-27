package com.taoswork.tallybook.testmaterial.mongo.domain.business.impl;

import com.taoswork.tallybook.datadomain.onmongo.AbstractDocument;
import org.mongodb.morphia.annotations.Entity;

@Entity
public class EmployeeImpl extends AbstractDocument {
}
//        implements IEmployee {
//
//    private String name;
//
//    private String firstName;
//    private String lastName;
//
//    transient private String translatedName;
//
//    protected Set<String> nickNameSet;
//
//    @PresentationCollection(primitiveDelegate = NicknameEntry.class)
//    protected Set nickNameSetNonType;
//
//    protected List<String> nickNameList;
//
//    //in blob
////    @Column(name = "NICKNAME_ARRAY")
////    @Lob
////    @PresentationCollection(primitiveDelegate = NicknameEntry.class)
////    protected String[] nickNameArray;
//
//    private long salary;
//
//    private EmployeeType type;
//
//
//    @PersistField(fieldType = FieldType.EXTERNAL_FOREIGN_KEY)
//    @PresentationField()
//    @PresentationExternalForeignKey(targetType = Citizen.class, dataField = "citizen")
//    private Long citizenId;
//
//    @Transient
//    private transient Citizen citizen;
//
//    private Calendar dob;
//
//    private Date startDate;
//
//    private String comments;
//
//    private byte[] picture;
//
//    @Reference
//    private IParkingSpace parkingSpace;
//
//    @ManyToOne(targetEntity = DepartmentImpl.class)
//    @JoinColumn(name="DEPT_ID")
//    private IDepartment department;
//
//    @Column(name = "CUB_ID")
//    private String cube;
//
//    @ManyToMany(targetEntity = ProjectImpl.class)
//    @PresentationCollection(collectionMode = CollectionMode.Lookup)
//    private Collection<IProject> projects;
//
//    // ...
//    @ElementCollection(targetClass=VacationEntry.class)
//    private Collection vacationBookings;
//
//    @ElementCollection
//    private Set<String> nickNames;
//
//    @Embedded private Address address;
//
//    @ElementCollection
//    private List<Address> addresses;
//
//    @ElementCollection
//    @CollectionTable(name="EMP_PHONE")
//    @MapKeyColumn(name="PHONE_TYPE")
//    @Column(name="PHONE_NUM")
//    @PresentationMap(simpleKeyEntryDelegate = PhoneTypeEntry.class)
//    private Map<PhoneType, String> phoneNumbers;
//// ...
//
//    @Override
//    public ICitizen getCitizen() {
//        return citizen;
//    }
//
//    @Override
//    public void setCitizen(ICitizen citizen) {
//        this.citizen = citizen;
//    }
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
//    public String getFirstName() {
//        return firstName;
//    }
//
//    @Override
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    @Override
//    public String getLastName() {
//        return lastName;
//    }
//
//    @Override
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    @Override
//    public EmployeeNameX getNameX() {
//        return nameX;
//    }
//
//    @Override
//    public void setNameX(EmployeeNameX nameX) {
//        this.nameX = nameX;
//    }
//
//    @Override
//    public String getTranslatedName() {
//        return translatedName;
//    }
//
//    @Override
//    public void setTranslatedName(String translatedName) {
//        this.translatedName = translatedName;
//    }
//
//    @Override
//    public Set<String> getNickNameSet() {
//        return nickNameSet;
//    }
//
//    @Override
//    public void setNickNameSet(Set<String> nickNameSet) {
//        this.nickNameSet = nickNameSet;
//    }
//
//    @Override
//    public Set getNickNameSetNonType() {
//        return nickNameSetNonType;
//    }
//
//    @Override
//    public void setNickNameSetNonType(Set nickNameSetNonType) {
//        this.nickNameSetNonType = nickNameSetNonType;
//    }
//
//    @Override
//    public List<String> getNickNameList() {
//        return nickNameList;
//    }
//
//    @Override
//    public void setNickNameList(List<String> nickNameList) {
//        this.nickNameList = nickNameList;
//    }
//
////    @Override
////    public String[] getNickNameArray() {
////        return nickNameArray;
////    }
////
////    @Override
////    public void setNickNameArray(String[] nickNameArray) {
////        this.nickNameArray = nickNameArray;
////    }
////
//    @Override
//    public long getSalary() {
//        return salary;
//    }
//
//    @Override
//    public void setSalary(long salary) {
//        this.salary = salary;
//    }
//
//    @Override
//    public EmployeeType getType() {
//        return type;
//    }
//
//    @Override
//    public void setType(EmployeeType type) {
//        this.type = type;
//    }
//
//    @Override
//    public Long getCitizenId() {
//        return citizenId;
//    }
//
//    @Override
//    public void setCitizenId(Long citizenId) {
//        this.citizenId = citizenId;
//    }
//
//    @Override
//    public Calendar getDob() {
//        return dob;
//    }
//
//    @Override
//    public void setDob(Calendar dob) {
//        this.dob = dob;
//    }
//
//    @Override
//    public Date getStartDate() {
//        return startDate;
//    }
//
//    @Override
//    public void setStartDate(Date startDate) {
//        this.startDate = startDate;
//    }
//
//    @Override
//    public String getComments() {
//        return comments;
//    }
//
//    @Override
//    public void setComments(String comments) {
//        this.comments = comments;
//    }
//
//    @Override
//    public byte[] getPicture() {
//        return picture;
//    }
//
//    @Override
//    public void setPicture(byte[] picture) {
//        this.picture = picture;
//    }
//
//    @Override
//    public IParkingSpace getParkingSpace() {
//        return parkingSpace;
//    }
//
//    @Override
//    public void setParkingSpace(IParkingSpace parkingSpace) {
//        this.parkingSpace = parkingSpace;
//    }
//
//    @Override
//    public IDepartment getDepartment() {
//        return department;
//    }
//
//    @Override
//    public void setDepartment(IDepartment department) {
//        this.department = department;
//    }
//
//    @Override
//    public String getCube() {
//        return cube;
//    }
//
//    @Override
//    public void setCube(String cube) {
//        this.cube = cube;
//    }
//
//    @Override
//    public Collection<IProject> getProjects() {
//        return projects;
//    }
//
//    @Override
//    public void setProjects(Collection<IProject> projects) {
//        this.projects = projects;
//    }
//
//    @Override
//    public Collection getVacationBookings() {
//        return vacationBookings;
//    }
//
//    @Override
//    public void setVacationBookings(Collection vacationBookings) {
//        this.vacationBookings = vacationBookings;
//    }
//
//    @Override
//    public Set<String> getNickNames() {
//        return nickNames;
//    }
//
//    @Override
//    public void setNickNames(Set<String> nickNames) {
//        this.nickNames = nickNames;
//    }
//
//    @Override
//    public Address getAddress() {
//        return address;
//    }
//
//    @Override
//    public void setAddress(Address address) {
//        this.address = address;
//    }
//
//    @Override
//    public Map<PhoneType, String> getPhoneNumbers() {
//        return phoneNumbers;
//    }
//
//    @Override
//    public void setPhoneNumbers(Map<PhoneType, String> phoneNumbers) {
//        this.phoneNumbers = phoneNumbers;
//    }
//}