package com.taoswork.tallybook.testframework.domain.business.impl;

import com.taoswork.tallybook.testframework.domain.business.IEmployee;
import com.taoswork.tallybook.testframework.domain.business.IProject;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Collection;

@Entity
@Table(name = "PROJECT")
public class ProjectImpl implements IProject{
    @Id
    private int id;
    private String name;

    @ManyToMany(targetEntity = EmployeeImpl.class, mappedBy="projects")
    private Collection<IEmployee> employees;
// ...
}