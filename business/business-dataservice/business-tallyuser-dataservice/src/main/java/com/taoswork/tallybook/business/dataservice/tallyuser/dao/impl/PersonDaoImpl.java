package com.taoswork.tallybook.business.dataservice.tallyuser.dao.impl;

import com.mongodb.DBRef;
import com.mongodb.WriteResult;
import com.taoswork.tallybook.business.datadomain.tallyuser.Person;
import com.taoswork.tallybook.business.datadomain.tallyuser.impl.PersonImpl;
import com.taoswork.tallybook.business.dataservice.tallyuser.dao.PersonDao;
import com.taoswork.tallybook.business.dataservice.tallyuser.dao.PersonKeyType;
import com.taoswork.tallybook.dataservice.annotations.Dao;
import com.taoswork.tallybook.dataservice.core.entity.DaoBase;
import com.taoswork.tallybook.dataservice.mongo.MongoDatasourceDefinition;
import com.taoswork.tallybook.general.extension.collections.CollectionUtility;
import com.taoswork.tallybook.general.extension.collections.ListUtility;
import com.taoswork.tallybook.general.extension.utils.AccountUtility;
import org.bson.types.ObjectId;
import org.mongodb.morphia.AdvancedDatastore;
import org.mongodb.morphia.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/4/21.
 */
@Dao(PersonDao.COMPONENT_NAME)
@Repository(PersonDao.COMPONENT_NAME)
public class PersonDaoImpl
        extends DaoBase
        implements PersonDao {

    @Resource(name = MongoDatasourceDefinition.DATASTORE_BEAN_NAME)
    private AdvancedDatastore datastore;

    @Override
    public Person readPersonByKey(PersonKeyType keyType, String keyValue, boolean eraseInternalKey) {
        Person tempPerson = null;
        switch (keyType) {
            case ID:
                tempPerson = this.readPersonById(keyValue);
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
    public Person readPersonById(String id) {
        return datastore.get(PersonImpl.class, new ObjectId(id));
    }

    @Override
    public Person readPersonByUUID(String uuid) {
        Query<PersonImpl> q = datastore.find(PersonImpl.class, "uuid", uuid);
        q.limit(2);
        List<PersonImpl> users = q.asList();
        return ListUtility.getTheSingleElement(users);
    }

    @Override
    public Person readPersonByName(String name) {
        Query<PersonImpl> q = datastore.find(PersonImpl.class, "name", name);
        q.limit(2);
        List<PersonImpl> users = q.asList();
        return ListUtility.getTheSingleElement(users);
    }

    @Override
    public Person readPersonByEmail(String email) {
        Query<PersonImpl> q = datastore.find(PersonImpl.class, "email", email);
        q.limit(2);
        List<PersonImpl> users = q.asList();
        return ListUtility.getTheSingleElement(users);
    }

    @Override
    public Person readPersonByMobile(String mobile) {
        Query<PersonImpl> q = datastore.find(PersonImpl.class, "mobile", mobile);
        q.limit(2);
        List<PersonImpl> users = q.asList();
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
        datastore.save(person);
        return person;
    }

    @Override
    public Person create() {
        Person user = new PersonImpl();
        return user;
    }

    @Override
    public boolean delete(Person user) {
        WriteResult wr = datastore.delete(PersonImpl.class, user.getId());
        return wr.getN() == 1;
    }

}
