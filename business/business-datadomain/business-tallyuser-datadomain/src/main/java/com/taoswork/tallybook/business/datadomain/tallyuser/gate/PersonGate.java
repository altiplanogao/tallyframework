package com.taoswork.tallybook.business.datadomain.tallyuser.gate;

import com.taoswork.tallybook.business.datadomain.tallyuser.Person;
import com.taoswork.tallybook.datadomain.base.entity.valuegate.BaseEntityGate;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

public class PersonGate extends BaseEntityGate<Person> {
    @Override
    protected void doStore(Person entity, Person oldEntity) {
        if (oldEntity != null) {
            entity.setUuid(oldEntity.getUuid());
        }
        if (StringUtils.isEmpty(entity.getUuid())) {
            entity.setUuid(UUID.randomUUID().toString());
        }
    }

    @Override
    protected void doFetch(Person entity) {
        entity.setUuid("");
    }
}
