package com.taoswork.tallybook.dataservice.jpa.servicemockup.service.dynamic;

import com.taoswork.tallybook.dataservice.jpa.core.dao.EntityDao;
import com.taoswork.tallybook.dataservice.jpa.core.dao.impl.EntityDaoImplBase;
import com.taoswork.tallybook.dataservice.jpa.servicemockup.datasource.TallyMockupJpaDatasourceDefinition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Gao Yuan on 2015/6/2.
 */
@Component(EntityDao.COMPONENT_NAME)
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TallyMockupEntityDao extends EntityDaoImplBase {
    @PersistenceContext(name = TallyMockupJpaDatasourceDefinition.TMOCKUP_PU_NAME)
    protected EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}
