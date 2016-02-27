package com.taoswork.tallybook.testmaterial.jpa.domain.business.impl;

import com.taoswork.tallybook.datadomain.base.entity.CollectionField;
import com.taoswork.tallybook.datadomain.base.entity.CollectionMode;
import com.taoswork.tallybook.testmaterial.jpa.domain.TallyMockupDataDomain;
import com.taoswork.tallybook.testmaterial.jpa.domain.business.IEmployee;
import com.taoswork.tallybook.testmaterial.jpa.domain.business.IProject;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "PROJECT")
public class ProjectImpl implements IProject {
    protected static final String ID_GENERATOR_NAME = "ProjectImpl_IdGen";

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = ID_GENERATOR_NAME)
    @TableGenerator(
            name = ID_GENERATOR_NAME,
            table = TallyMockupDataDomain.ID_GENERATOR_TABLE_NAME,
            initialValue = 0)
    @Column(name = "ID")
    private int id;
    private String name;

    @ManyToMany(targetEntity = EmployeeImpl.class, mappedBy = "projects")
    @CollectionField(targetClass = EmployeeImpl.class, mode = CollectionMode.Lookup)
    private Collection<IEmployee> employees;


    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Collection<IEmployee> getEmployees() {
        return employees;
    }

    @Override
    public void setEmployees(Collection<IEmployee> employees) {
        this.employees = employees;
    }
}