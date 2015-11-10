package com.taoswork.tallybook.business.datadomain.tallybusiness.impl;


import com.taoswork.tallybook.business.datadomain.tallybusiness.Employee;
import com.taoswork.tallybook.business.datadomain.tallybusiness.EmployeeOwnedSetting;
import com.taoswork.tallybook.business.datadomain.tallybusiness.TallyBusinessDataDomain;
import com.taoswork.tallybook.general.datadomain.support.entity.PersistField;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationField;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.Visibility;

import javax.persistence.*;

/**
 * Created by Gao Yuan on 2015/4/16.
 */
@Entity
@Table(name = "TB_EMPLOYEE_SETTING")
public class EmployeeOwnedSettingImpl implements EmployeeOwnedSetting {
    protected static final String ID_GENERATOR_NAME = "EmployeeOwnedSettingImpl_IdGen";

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = ID_GENERATOR_NAME)
    @TableGenerator(
        name = ID_GENERATOR_NAME,
        table = TallyBusinessDataDomain.ID_GENERATOR_TABLE_NAME,
        initialValue = 1)
    @Column(name = "ID")
    @PersistField(fieldType = FieldType.ID)
    @PresentationField(order = 1, visibility = Visibility.HIDDEN_ALL)
    protected Long id;

    @OneToOne(targetEntity = EmployeeImpl.class, mappedBy = "setting")
    protected Employee employee;

    @Override
    public Employee getEmployee() {
        return employee;
    }

    @Override
    public EmployeeOwnedSetting setEmployee(Employee employee) {
        this.employee = employee;
        return this;
    }
}
