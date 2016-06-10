package com.taoswork.tallybook.business.dataservice.tallyadmin.dao.impl;

import com.taoswork.tallybook.business.datadomain.tallyadmin.AdminEmployee;
import com.taoswork.tallybook.business.dataservice.tallyadmin.dao.AdminEmployeeDao;
import com.taoswork.tallybook.dataservice.annotations.Dao;
import com.taoswork.tallybook.dataservice.mongo.dao.DocumentDaoBase;
import com.taoswork.tallybook.general.extension.collections.ListUtility;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Gao Yuan on 2015/5/10.
 */
@Dao(AdminEmployeeDao.COMPONENT_NAME)
@Repository(AdminEmployeeDao.COMPONENT_NAME)
public class AdminEmployeeDaoImpl
        extends DocumentDaoBase
        implements AdminEmployeeDao {

    @Override
    public AdminEmployee readAdminEmployeeByPersonId(String id) {
        Query<AdminEmployee> q = datastore.createQuery(AdminEmployee.class);
        q.field("personId").equal(id);
        q.limit(2);
        List<AdminEmployee> employees = q.asList();
        return ListUtility.getTheSingleElement(employees);
    }

    @Override
    public AdminEmployee save(AdminEmployee employee) {
        String personId = employee.getPersonId();
        if (personId == null)
            throw new IllegalArgumentException();
        Query<AdminEmployee> q = datastore.createQuery(AdminEmployee.class);
        q.field("personId").equal(personId);

        Key<AdminEmployee> key = datastore.save(employee);
        employee.setId((ObjectId) key.getId());
        UpdateOperations<AdminEmployee> u = datastore.createUpdateOperations(AdminEmployee.class);
        return employee;
//        return datastore.findAndModify(q, u, false, true );

    }
}
