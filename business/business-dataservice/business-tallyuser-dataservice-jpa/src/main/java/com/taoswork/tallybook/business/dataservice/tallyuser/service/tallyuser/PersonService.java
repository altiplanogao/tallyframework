package com.taoswork.tallybook.business.dataservice.tallyuser.service.tallyuser;

import com.taoswork.tallybook.business.datadomain.tallyuser.Person;
import com.taoswork.tallybook.business.datadomain.tallyuser.PersonCertification;
import com.taoswork.tallybook.dataservice.core.entity.IService;

/**
 * Created by Gao Yuan on 2015/5/8.
 */
public interface PersonService extends IService {
    public static final String SERVICE_NAME = PREFIX + "PersonService";

    Person readPersonByUUID(String userUuid);

    Person savePerson(Person person);

    Person readPersonByID(Long id);

    Person readPersonByAnyIdentity(String s);

    PersonCertification readPersonCertificationByUUID(String personUUID);
}
