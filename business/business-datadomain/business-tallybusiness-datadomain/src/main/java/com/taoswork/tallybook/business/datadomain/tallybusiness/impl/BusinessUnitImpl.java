package com.taoswork.tallybook.business.datadomain.tallybusiness.impl;

import com.taoswork.tallybook.business.datadomain.tallybusiness.BusinessUnit;
import com.taoswork.tallybook.business.datadomain.tallybusiness.TallyBusinessDataDomain;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationField;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.Visibility;

import javax.persistence.*;

@Entity
@Table(name = "TB_BU")
public class BusinessUnitImpl implements BusinessUnit {

    protected static final String ID_GENERATOR_NAME = "BusinessUnitImpl_IdGen";

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = ID_GENERATOR_NAME)
    @TableGenerator(
        name = ID_GENERATOR_NAME,
        table = TallyBusinessDataDomain.ID_GENERATOR_TABLE_NAME,
        initialValue = 1)
    @Column(name = "ID")
    @PresentationField(order = 1, fieldType = FieldType.ID, visibility = Visibility.HIDDEN_ALL)
    protected Long id;

    @Column(name = "NAME", nullable = false)
    @PresentationField(order = 2, nameField = true, fieldType = FieldType.NAME)
    protected String name;

    @Column(name = "DESCRIP", length = Integer.MAX_VALUE - 1)
    @Lob
    @PresentationField(order = 4, fieldType = FieldType.HTML, visibility = Visibility.GRID_HIDE)
    protected String description;


/*  Hide for prototype
    protected List<Employee> employees;
    protected List<BusinessPartner> businessPartners;
    protected List<Asset> assets;
    protected List<WorkPlan> workPlans;
    protected List<WorkSuite> workSuites;
    protected List<ModuleUsage> modules;
*/

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public BusinessUnitImpl setId(Long id) {
        this.id = id;
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public BusinessUnitImpl setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public BusinessUnitImpl setDescription(String description) {
        this.description = description;
        return this;
    }
}
