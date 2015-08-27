package com.taoswork.tallybook.business.datadomain.tallybusiness.impl;

import com.taoswork.tallybook.business.datadomain.tallybusiness.BusinessUnit;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationField;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.Visibility;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Gao Yuan on 2015/4/16.
 */
@Entity
@Table(name = "TB_ORG")
public class BusinessUnitImpl implements BusinessUnit {
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
