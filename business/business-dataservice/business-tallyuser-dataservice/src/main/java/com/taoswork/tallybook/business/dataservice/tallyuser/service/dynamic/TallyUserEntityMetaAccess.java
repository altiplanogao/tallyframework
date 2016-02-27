package com.taoswork.tallybook.business.dataservice.tallyuser.service.dynamic;

import com.taoswork.tallybook.business.dataservice.tallyuser.conf.TallyUserJpaDatasourceDefinition;
import com.taoswork.tallybook.dataservice.jpa.core.metaaccess.BaseJpaEntityMetaAccess;
import com.taoswork.tallybook.dataservice.jpa.core.metaaccess.JpaEntityMetaAccess;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Gao Yuan on 2015/6/2.
 */
@Component(JpaEntityMetaAccess.COMPONENT_NAME)
public class TallyUserEntityMetaAccess extends BaseJpaEntityMetaAccess {
    @PersistenceContext(name = TallyUserJpaDatasourceDefinition.TUSER_PU_NAME)
    protected EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

}
