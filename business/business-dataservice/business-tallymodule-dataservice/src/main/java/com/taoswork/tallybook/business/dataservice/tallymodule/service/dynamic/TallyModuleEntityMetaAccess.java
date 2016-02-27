package com.taoswork.tallybook.business.dataservice.tallymodule.service.dynamic;

import com.taoswork.tallybook.business.dataservice.tallymodule.TallyModuleJpaDatasourceDefinition;
import com.taoswork.tallybook.dataservice.jpa.core.metaaccess.BaseJpaEntityMetaAccess;
import com.taoswork.tallybook.dataservice.jpa.core.metaaccess.JpaEntityMetaAccess;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Gao Yuan on 2015/6/2.
 */
@Component(JpaEntityMetaAccess.COMPONENT_NAME)
public class TallyModuleEntityMetaAccess extends BaseJpaEntityMetaAccess {
    @PersistenceContext(name = TallyModuleJpaDatasourceDefinition.TMODULE_PU_NAME)
    protected EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

}
