package com.taoswork.tallybook.business.dataservice.tallyuser.dao;

import com.taoswork.tallybook.business.datadomain.tallyuser.Person;
import com.taoswork.tallybook.dataservice.core.entity.IDao;

/**
 * Created by Gao Yuan on 2015/4/21.
 */
public interface PersonDao extends IDao {
    public static final String COMPONENT_NAME = PREFIX + "PersonDao";

    Person readPersonByKey(PersonKeyType keyType, String keyValue, boolean containsInternalKey);

    Person readPersonById(Long id);

    Person readPersonByUUID(String uuid);

    Person readPersonByName(String name);

    Person readPersonByEmail(String email);

    Person readPersonByMobile(String mobile);

    Person readPersonByAnyIdentity(String mobile_Email_PersonName);

    Person save(Person person);

    Person create();

    void delete(Person user);

    boolean isThereAnyData();
}
