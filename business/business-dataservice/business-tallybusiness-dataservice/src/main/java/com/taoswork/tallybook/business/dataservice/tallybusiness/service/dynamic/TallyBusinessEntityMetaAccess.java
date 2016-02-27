package com.taoswork.tallybook.business.dataservice.tallybusiness.service.dynamic;

import com.taoswork.tallybook.business.dataservice.tallybusiness.TallyBusinessJpaDatasourceDefinition;
import com.taoswork.tallybook.dataservice.jpa.core.metaaccess.JpaEntityMetaAccess;
import com.taoswork.tallybook.dataservice.jpa.core.metaaccess.BaseJpaEntityMetaAccess;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Gao Yuan on 2015/6/2.
 */
@Component(JpaEntityMetaAccess.COMPONENT_NAME)
public class TallyBusinessEntityMetaAccess extends BaseJpaEntityMetaAccess {
    @PersistenceContext(name = TallyBusinessJpaDatasourceDefinition.TBUSINESS_PU_NAME)
    protected EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

}
