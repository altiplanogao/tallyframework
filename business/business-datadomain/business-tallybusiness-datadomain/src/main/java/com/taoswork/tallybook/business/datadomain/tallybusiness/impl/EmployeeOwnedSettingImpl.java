package com.taoswork.tallybook.business.datadomain.tallybusiness.impl;


import com.taoswork.tallybook.business.datadomain.tallybusiness.Employee;
import com.taoswork.tallybook.business.datadomain.tallybusiness.EmployeeOwnedSetting;

/**
 * Created by Gao Yuan on 2015/4/16.
 */
public class EmployeeOwnedSettingImpl implements EmployeeOwnedSetting {
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
