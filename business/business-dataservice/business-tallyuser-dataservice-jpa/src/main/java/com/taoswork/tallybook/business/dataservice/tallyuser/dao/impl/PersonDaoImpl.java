package com.taoswork.tallybook.business.dataservice.tallyuser.dao.impl;

import com.taoswork.tallybook.business.datadomain.tallyuser.Person;
import com.taoswork.tallybook.business.datadomain.tallyuser.impl.PersonImpl;
import com.taoswork.tallybook.business.dataservice.tallyuser.dao.PersonDao;
import com.taoswork.tallybook.business.dataservice.tallyuser.dao.PersonKeyType;
import com.taoswork.tallybook.business.dataservice.tallyuser.conf.TallyUserJpaDatasourceDefinition;
import com.taoswork.tallybook.dataservice.annotations.Dao;
import com.taoswork.tallybook.dataservice.core.entity.DaoBase;
import com.taoswork.tallybook.general.extension.collections.CollectionUtility;
import com.taoswork.tallybook.general.extension.collections.ListUtility;
import com.taoswork.tallybook.general.extension.utils.AccountUtility;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/4/21.
 */
@Dao(PersonDao.COMPONENT_NAME)
@Repository(PersonDao.COMPONENT_NAME)
public class PersonDaoImpl
        extends DaoBase
        implements PersonDao {

    @PersistenceContext(unitName = TallyUserJpaDatasourceDefinition.TUSER_PU_NAME)
    protected EntityManager em;

    @Override
    public Person readPersonByKey(PersonKeyType keyType, String keyValue, boolean eraseInternalKey) {
        Person tempPerson = null;
        switch (keyType) {
            case ID:
                tempPerson = this.readPersonById(Long.getLong(keyValue));
                break;
            case UUID:
                tempPerson = readPersonByUUID(keyValue);
                break;
            case NAME:
                tempPerson = readPersonByName(keyValue);
                break;
            case EMAIL:
                tempPerson = readPersonByEmail(keyValue);
                break;
            case MOBILE:
                tempPerson = readPersonByMobile(keyValue);
                break;
            case NOT_SURE:
                tempPerson = null;
                break;
        }
        if (eraseInternalKey) {
            tempPerson.setUuid(null);
        }
        return tempPerson;
    }

    @Override
    public Person readPersonById(Long id) {
        return em.find(PersonImpl.class, id);
    }

    @Override
    public Person readPersonByUUID(String uuid) {
        TypedQuery<Person> query = em.createNamedQuery("Person.ReadPersonByUUID", Person.class);
        query.setParameter("uuid", uuid);
        query.setMaxResults(2);
        List<Person> users = query.getResultList();
        return ListUtility.getTheSingleElement(users);
    }

    @Override
    public Person readPersonByName(String name) {
        TypedQuery<Person> query = em.createNamedQuery("Person.ReadPersonByName", Person.class);
        query.setParameter("name", name);
        query.setMaxResults(2);
        List<Person> users = query.getResultList();
        return ListUtility.getTheSingleElement(users);
    }

    @Override
    public Person readPersonByEmail(String email) {
        TypedQuery<Person> query = em.createNamedQuery("Person.ReadPersonByEmail", Person.class);
        query.setParameter("email", email);
        query.setMaxResults(2);
        List<Person> users = query.getResultList();
        return ListUtility.getTheSingleElement(users);
    }

    @Override
    public Person readPersonByMobile(String mobile) {
        TypedQuery<Person> query = em.createNamedQuery("Person.ReadPersonByMobile", Person.class);
        query.setParameter("mobile", mobile);
        query.setMaxResults(2);
        List<Person> users = query.getResultList();
        return ListUtility.getTheSingleElement(users);
    }

    @Override
    public Person readPersonByAnyIdentity(String mobile_Email_PersonName) {
        if (AccountUtility.isEmail(mobile_Email_PersonName)) {
            return readPersonByEmail(mobile_Email_PersonName);
        } else if (AccountUtility.isPhone(mobile_Email_PersonName)) {
            return readPersonByMobile(mobile_Email_PersonName);
        } else {
            return readPersonByName(mobile_Email_PersonName);
        }
    }

    @Override
    public Person save(Person person) {
        if (em.contains(person) || person.getId() != null) {
            return em.merge(person);
        } else {
            em.persist(person);
            return person;
        }
    }

    @Override
    public Person create() {
        Person user = new PersonImpl();
        return user;
    }

    @Override
    public void delete(Person user) {
        if (!em.contains(user)) {
            user = readPersonById(user.getId());
        }
        em.remove(user);
    }

    @Override
    public boolean isThereAnyData() {
        TypedQuery<Person> query = em.createNamedQuery("Person.ListPerson", Person.class);
        query.setMaxResults(1);
        List<Person> users = query.getResultList();
        return !CollectionUtility.isEmpty(users);
    }
}
