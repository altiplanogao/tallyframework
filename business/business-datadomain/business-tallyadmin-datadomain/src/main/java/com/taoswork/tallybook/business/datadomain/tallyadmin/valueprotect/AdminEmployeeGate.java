package com.taoswork.tallybook.business.datadomain.tallyadmin.valueprotect;

import com.taoswork.tallybook.business.datadomain.tallyadmin.AdminEmployee;
import com.taoswork.tallybook.business.datadomain.tallyuser.Person;
import com.taoswork.tallybook.general.datadomain.support.entity.valuegate.EntityValueGateBase;
import org.apache.commons.lang3.StringUtils;

public class AdminEmployeeGate extends EntityValueGateBase<AdminEmployee> {
    @Override
    protected void doDeposit(AdminEmployee entity, AdminEmployee oldEntity) {
        Person person = entity.getPerson();
        if(person != null){
            if (StringUtils.isEmpty(entity.getName())){
                entity.setName(person.getName());
            }
            entity.setPersonId(person.getId());
        }
    }

    @Override
    protected void doWithdraw(AdminEmployee entity) {

    }
}
