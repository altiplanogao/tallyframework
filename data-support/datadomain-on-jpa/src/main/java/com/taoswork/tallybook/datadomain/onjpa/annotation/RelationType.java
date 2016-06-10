package com.taoswork.tallybook.datadomain.onjpa.annotation;

/**
 * Created by Gao Yuan on 2015/8/22.
 */
public enum RelationType {
    //One - One
    /**
     * Relation Owner
     * Has: @JoinColumn
     * Example:
     *
     * @Entity
     * public class Employee {
     *
     * @OneToOne
     * @JoinColumn(name="PSPACE_ID")
     * private ParkingSpace parkingSpace;
     *
     * }
     */
    OneWay_OneToOne,
    TwoWay_OneToOneOwner,

    /**
     * Relation Belonging
     * Has: mappedBy
     * Example:
     *
     * @Entity
     * public class ParkingSpace {
     *
     * @OneToOne(mappedBy="parkingSpace")
     * private Employee employee;
     *
     * }
     */
    TwoWay_OneToOneBelonging,

    //Many - One
    /**
     * Relation Owner
     * Has: @JoinColumn
     * Example:
     *
     * @Entity
     * public class Employee {
     *  ...
     *  @ManyToOne
     *  @JoinColumn(name="DEPT_ID")
     *  private Department department;
     *   ...
     * }
     */
    OneWay_ManyToOne,
    TwoWay_ManyToOneOwner,

    /**
     * Has "mappedBy"
     * @Entity
     * public class Department {
     *
     * @OneToMany(mappedBy="department")
     * private Collection<Employee> employees;
     *  ...
     *  }
     */
    TwoWay_ManyToOneBelonging,
    TwoWay_OneToManyBelonged,

    //One - Many
    /**
     * Has @JoinTable
     *
     * @Entity
     * public class Employee {
     *
     * @OneToMany
     * @JoinTable(name="EMP_PHONE",
     *              joinColumns=@JoinColumn(name="EMP_ID"),
     *              inverseJoinColumns=@JoinColumn(name="PHONE_ID"))
     * private Collection<Phone> phones;
     *  ...
     * }
     */
    OneWay_OneToMany,

    //Many - Many
    /**
     * @Entity
     * public class Employee {
     *
     * @ManyToMany
     * private Collection<Project> projects;
     *
     * }
     */
    TwoWay_ManyToManyOwner,
    OneWay_ManyToMany,

    /**
     *
     * @Entity
     * public class Project {
     *
     * @ManyToMany(mappedBy="projects")
     * private Collection<Employee> employees;
     *  ...
     * }
     *
     */
    TwoWay_ManyToManyBelonging,

    //None
    None;

}
