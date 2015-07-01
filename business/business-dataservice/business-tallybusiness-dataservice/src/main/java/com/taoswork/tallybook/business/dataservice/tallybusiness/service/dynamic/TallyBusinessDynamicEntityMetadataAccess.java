package com.taoswork.tallybook.business.dataservice.tallybusiness.service.dynamic;

import com.taoswork.tallybook.business.dataservice.tallybusiness.TallyBusinessDataServiceDefinition;
import com.taoswork.tallybook.dynamic.dataservice.metaaccess.DynamicEntityMetadataAccess;
import com.taoswork.tallybook.dynamic.dataservice.metaaccess.impl.DynamicEntityMetadataAccessImplBase;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Gao Yuan on 2015/6/2.
 */
@Component(DynamicEntityMetadataAccess.COMPONENT_NAME)
public class TallyBusinessDynamicEntityMetadataAccess extends DynamicEntityMetadataAccessImplBase {
    @PersistenceContext(name = TallyBusinessDataServiceDefinition.TBUSINESS_PU_NAME)
    protected EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

}
