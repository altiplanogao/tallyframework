package com.taoswork.tallybook.business.dataservice.tallyuser.service.tallyuser.impl;

import com.taoswork.tallybook.business.datadomain.tallyuser.Person;
import com.taoswork.tallybook.business.datadomain.tallyuser.PersonCertification;
import com.taoswork.tallybook.business.dataservice.tallyuser.dao.PersonCertificationDao;
import com.taoswork.tallybook.business.dataservice.tallyuser.dao.PersonDao;
import com.taoswork.tallybook.business.dataservice.tallyuser.dao.PersonKeyType;
import com.taoswork.tallybook.business.dataservice.tallyuser.service.tallyuser.PersonService;
import com.taoswork.tallybook.dataservice.annotations.EntityService;
import com.taoswork.tallybook.dataservice.core.entity.EntityServiceBase;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Gao Yuan on 2015/5/8.
 */
@Service(PersonService.SERVICE_NAME)
@EntityService(PersonService.SERVICE_NAME)
public class PersonServiceImpl
        extends EntityServiceBase
        implements PersonService {

    @Resource(name = PersonDao.COMPONENT_NAME)
    protected PersonDao userDao;

    @Resource(name = PersonCertificationDao.COMPONENT_NAME)
    protected PersonCertificationDao personCertificationDao;

    @Override
    public Person readPersonByUUID(String userUuid) {
        return userDao.readPersonByKey(PersonKeyType.UUID, userUuid, false);
    }

    @Override
    public Person readPersonByID(String id) {
        return userDao.readPersonById(id);
    }

    @Override
    public Person savePerson(Person person) {
        return userDao.save(person);
    }

    @Override
    public Person readPersonByAnyIdentity(String s) {
        return userDao.readPersonByAnyIdentity(s);
    }

    @Override
    public PersonCertification readPersonCertificationByUUID(String personUUID) {
        return personCertificationDao.readPersonCertificationByPersonCode(personUUID);
    }
}
