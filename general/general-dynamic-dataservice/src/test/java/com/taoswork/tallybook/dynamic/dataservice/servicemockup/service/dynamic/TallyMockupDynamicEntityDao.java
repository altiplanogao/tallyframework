package com.taoswork.tallybook.dynamic.dataservice.servicemockup.service.dynamic;

import com.taoswork.tallybook.dynamic.dataservice.core.dao.DynamicEntityDao;
import com.taoswork.tallybook.dynamic.dataservice.core.dao.impl.DynamicEntityDaoImplBase;
import com.taoswork.tallybook.dynamic.dataservice.servicemockup.TallyMockupDataServiceDefinition;
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
public class TallyMockupDynamicEntityDao extends DynamicEntityDaoImplBase {
    @PersistenceContext(name = TallyMockupDataServiceDefinition.TMOCKUP_PU_NAME)
    protected EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}
