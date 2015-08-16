package com.taoswork.tallybook.business.dataservice.tallymodule.service.dynamic;

import com.taoswork.tallybook.business.dataservice.tallymodule.TallyModuleDataServiceDefinition;
import com.taoswork.tallybook.dynamic.dataservice.core.metaaccess.DynamicEntityMetadataAccess;
import com.taoswork.tallybook.dynamic.dataservice.core.metaaccess.impl.DynamicEntityMetadataAccessImplBase;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Gao Yuan on 2015/6/2.
 */
@Component(DynamicEntityMetadataAccess.COMPONENT_NAME)
public class TallyModuleDynamicEntityMetadataAccess extends DynamicEntityMetadataAccessImplBase {
    @PersistenceContext(name = TallyModuleDataServiceDefinition.TMODULE_PU_NAME)
    protected EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

}
