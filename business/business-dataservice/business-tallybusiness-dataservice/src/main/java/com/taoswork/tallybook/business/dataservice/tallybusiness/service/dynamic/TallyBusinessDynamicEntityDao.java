package com.taoswork.tallybook.business.dataservice.tallybusiness.service.dynamic;

import com.taoswork.tallybook.business.dataservice.tallybusiness.TallyBusinessDataServiceDefinition;
import com.taoswork.tallybook.dynamic.dataservice.core.dao.DynamicEntityDao;
import com.taoswork.tallybook.dynamic.dataservice.core.dao.impl.DynamicEntityDaoImplBase;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Gao Yuan on 2015/6/2.
 */
@Component(DynamicEntityDao.COMPONENT_NAME)
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TallyBusinessDynamicEntityDao extends DynamicEntityDaoImplBase {
    @PersistenceContext(name = TallyBusinessDataServiceDefinition.TBUSINESS_PU_NAME)
    protected EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}
