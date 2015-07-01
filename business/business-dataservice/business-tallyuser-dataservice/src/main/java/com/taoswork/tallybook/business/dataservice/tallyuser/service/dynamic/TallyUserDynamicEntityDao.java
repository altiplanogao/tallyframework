package com.taoswork.tallybook.business.dataservice.tallyuser.service.dynamic;

import com.taoswork.tallybook.business.dataservice.tallyuser.TallyUserDataServiceDefinition;
import com.taoswork.tallybook.dynamic.dataservice.dao.DynamicEntityDao;
import com.taoswork.tallybook.dynamic.dataservice.dao.impl.DynamicEntityDaoImplBase;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Gao Yuan on 2015/6/2.
 */
@Component(DynamicEntityDao.COMPONENT_NAME)
public class TallyUserDynamicEntityDao extends DynamicEntityDaoImplBase {
    @PersistenceContext(name = TallyUserDataServiceDefinition.TUSER_PU_NAME)
    protected EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}
