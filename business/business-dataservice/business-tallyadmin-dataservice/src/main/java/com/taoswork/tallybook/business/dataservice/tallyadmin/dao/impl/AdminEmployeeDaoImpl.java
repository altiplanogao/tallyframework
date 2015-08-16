package com.taoswork.tallybook.business.dataservice.tallyadmin.dao.impl;

import com.taoswork.tallybook.business.datadomain.tallyadmin.AdminEmployee;
import com.taoswork.tallybook.business.dataservice.tallyadmin.TallyAdminDataServiceDefinition;
import com.taoswork.tallybook.business.dataservice.tallyadmin.dao.AdminEmployeeDao;
import com.taoswork.tallybook.dynamic.dataservice.entity.DaoBase;
import com.taoswork.tallybook.general.dataservice.support.annotations.Dao;
import com.taoswork.tallybook.general.extension.collections.ListUtility;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/5/10.
 */
@Dao(AdminEmployeeDao.COMPONENT_NAME)
@Repository(AdminEmployeeDao.COMPONENT_NAME)
public class AdminEmployeeDaoImpl
        extends DaoBase
        implements AdminEmployeeDao {

    @PersistenceContext(unitName = TallyAdminDataServiceDefinition.TADMIN_PU_NAME)
    protected EntityManager em;

    @Override
    public AdminEmployee readAdminEmployeeByPersonId(Long id) {
        TypedQuery<AdminEmployee> query = em.createNamedQuery("AdminEmployee.ReadEmployeeByPersonId", AdminEmployee.class);
        query.setParameter("personId", id);
        query.setMaxResults(2);
        List<AdminEmployee> employees = query.getResultList();
        return ListUtility.getTheSingleElement(employees);
    }

    @Override
    public AdminEmployee save(AdminEmployee employee) {
        if(em.contains(employee) || employee.getId() != null){
            return em.merge(employee);
        }else {
            em.persist(employee);
            return employee;
        }
    }
}
