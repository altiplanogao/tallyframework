package com.taoswork.tallybook.business.datadomain.tallyadmin.valueprotect;

import com.taoswork.tallybook.business.datadomain.tallyadmin.AdminEmployee;
import com.taoswork.tallybook.business.datadomain.tallyuser.Person;
import com.taoswork.tallybook.general.authority.core.authentication.user.AccountStatus;
import com.taoswork.tallybook.general.datadomain.support.entity.valuegate.EntityValueGateBase;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

public class AdminEmployeeGate extends EntityValueGateBase<AdminEmployee> {
    @Override
    protected void doStore(AdminEmployee entity, AdminEmployee oldEntity) {
        Person person = entity.getPerson();
        if(person != null){
            if (StringUtils.isEmpty(entity.getName())){
                entity.setName(person.getName());
            }
            entity.setPersonId(person.getId());
        }
        AccountStatus as =entity.getStatus();
        if(as== null){
            as = new AccountStatus();
            entity.setStatus(as);
        }
        if(as.getLastLoginDate() == null){
            as.setLastLoginDate(new Date());
        }
        if(as.getCreateDate() == null){
            as.setCreateDate(new Date());
        }
    }

    @Override
    protected void doFetch(AdminEmployee entity) {
        AccountStatus as =entity.getStatus();
        if(as== null){
            as = new AccountStatus();
            entity.setStatus(as);
        }
        if(as.getLastLoginDate() == null){
            as.setLastLoginDate(new Date());
        }
        if(as.getCreateDate() == null){
            as.setCreateDate(new Date());
        }
    }
}
