package com.taoswork.tallybook.business.datadomain.tallyuser.gate;

import com.taoswork.tallybook.business.datadomain.tallyuser.Person;
import com.taoswork.tallybook.general.datadomain.support.entity.valuegate.EntityValueGateBase;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

public class PersonValueGate extends EntityValueGateBase<Person> {
    @Override
    protected void doDeposit(Person entity, Person oldEntity) {
        if(oldEntity != null){
            entity.setUuid(oldEntity.getUuid());
        }
        if(StringUtils.isEmpty(entity.getUuid())){
            entity.setUuid(UUID.randomUUID().toString());
        }
    }

    @Override
    protected void doWithdraw(Person entity) {
        entity.setUuid("");
    }
}
