package com.taoswork.tallybook.business.dataservice.tallymodule.service.dynamic;

import com.taoswork.tallybook.business.dataservice.tallymodule.TallyModuleJpaDatasourceDefinition;
import com.taoswork.tallybook.dataservice.jpa.core.dao.EntityDao;
import com.taoswork.tallybook.dataservice.jpa.core.dao.impl.EntityDaoImplBase;
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
public class TallyModuleEntityDao extends EntityDaoImplBase {
    @PersistenceContext(name = TallyModuleJpaDatasourceDefinition.TMODULE_PU_NAME)
    protected EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}
