package com.taoswork.tallybook.business.datadomain.tallybusiness.impl;

import com.taoswork.tallybook.business.datadomain.tallybusiness.*;
import com.taoswork.tallybook.dynamic.datadomain.presentation.PresentationField;
import com.taoswork.tallybook.dynamic.datadomain.presentation.client.Visibility;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/4/16.
 */
@Entity
@Table(name = "TB_ORG")
public class OrganizationImpl implements Organization {
    @Id
    @Column(name = "ID")
    @PresentationField(order = 1)
    protected Long id;

    @Column(name = "NAME", nullable = false)
    @PresentationField(order = 2, nameField = true)
    protected String name;

    @Column(name = "DESC")
    @PresentationField(order = 4, visibility = Visibility.GRID_HIDE)
    protected String description;


/*  Hide for prototype
    protected List<Employee> employees;
    protected List<BusinessPartner> businessPartners;
    protected List<Asset> assets;
    protected List<WorkPlan> workPlans;
    protected List<WorkSuite> workSuites;
    protected List<ModuleUsage> modules;
*/

}
