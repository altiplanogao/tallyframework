package com.taoswork.tallybook.business.dataservice.tallymodule.service.dynamic;

import com.taoswork.tallybook.business.dataservice.tallymodule.TallyModuleDataServiceDefinition;
import com.taoswork.tallybook.dynamic.dataservice.core.dao.DynamicEntityDao;
import com.taoswork.tallybook.dynamic.dataservice.core.dao.impl.DynamicEntityDaoImplBase;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Gao Yuan on 2015/6/2.
 */
@Component(DynamicEntityDao.COMPONENT_NAME)
public class TallyModuleDynamicEntityDao extends DynamicEntityDaoImplBase {
    @PersistenceContext(name = TallyModuleDataServiceDefinition.TMODULE_PU_NAME)
    protected EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}
