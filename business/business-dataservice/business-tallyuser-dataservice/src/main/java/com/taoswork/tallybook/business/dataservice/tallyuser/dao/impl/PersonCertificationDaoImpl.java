package com.taoswork.tallybook.business.dataservice.tallyuser.dao.impl;

import com.taoswork.tallybook.business.datadomain.tallyuser.PersonCertification;
import com.taoswork.tallybook.business.datadomain.tallyuser.impl.PersonCertificationImpl;
import com.taoswork.tallybook.business.dataservice.tallyuser.dao.PersonCertificationDao;
import com.taoswork.tallybook.dataservice.annotations.Dao;
import com.taoswork.tallybook.dataservice.core.entity.DaoBase;
import com.taoswork.tallybook.dataservice.mongo.MongoDatasourceDefinition;
import com.taoswork.tallybook.general.extension.collections.ListUtility;
import org.bson.types.ObjectId;
import org.mongodb.morphia.AdvancedDatastore;
import org.mongodb.morphia.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/5/3.
 */
@Dao(PersonCertificationDao.COMPONENT_NAME)
@Repository(PersonCertificationDao.COMPONENT_NAME)
public class PersonCertificationDaoImpl
        extends DaoBase
        implements PersonCertificationDao {

    @Resource(name = MongoDatasourceDefinition.DATASTORE_BEAN_NAME)
    private AdvancedDatastore datastore;

    @Override
    public PersonCertification readPersonCertificationById(String id) {
        return datastore.get(PersonCertificationImpl.class, new ObjectId(id));
    }

    @Override
    public PersonCertification readPersonCertificationByPersonCode(String userCode) {
        Query<PersonCertificationImpl> query = datastore.find(PersonCertificationImpl.class, "userCode", userCode);
        query.limit(2);
        List<PersonCertificationImpl> users = query.asList();
        return ListUtility.getTheSingleElement(users);
    }

}
