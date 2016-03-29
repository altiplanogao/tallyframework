package com.taoswork.tallybook.business.dataservice.tallyuser.dao.impl;

import com.taoswork.tallybook.business.datadomain.tallyuser.PersonCertification;
import com.taoswork.tallybook.business.datadomain.tallyuser.impl.PersonCertificationImpl;
import com.taoswork.tallybook.business.dataservice.tallyuser.dao.PersonCertificationDao;
import com.taoswork.tallybook.business.dataservice.tallyuser.conf.TallyUserJpaDatasourceDefinition;
import com.taoswork.tallybook.dataservice.annotations.Dao;
import com.taoswork.tallybook.dataservice.core.entity.DaoBase;
import com.taoswork.tallybook.general.extension.collections.ListUtility;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/5/3.
 */
@Dao(PersonCertificationDao.COMPONENT_NAME)
@Repository(PersonCertificationDao.COMPONENT_NAME)
public class PersonCertificationDaoImpl
        extends DaoBase
        implements PersonCertificationDao {

    @PersistenceContext(unitName = TallyUserJpaDatasourceDefinition.TUSER_PU_NAME)
    protected EntityManager em;

    @Override
    public PersonCertification readPersonCertificationById(Long id) {
        return em.find(PersonCertificationImpl.class, id);
    }

    @Override
    public PersonCertification readPersonCertificationByPersonCode(String userCode) {
        TypedQuery<PersonCertification> query = em.createNamedQuery("PersonCertification.ReadByPersonCode", PersonCertification.class);
        query.setParameter("userCode", userCode);
        query.setMaxResults(2);
        List<PersonCertification> users = query.getResultList();
        return ListUtility.getTheSingleElement(users);
    }

}
