package com.taoswork.tallybook.dynamic.dataservice.servicemockup.service.dynamic;

import com.taoswork.tallybook.dynamic.dataservice.core.metaaccess.DynamicEntityMetadataAccess;
import com.taoswork.tallybook.dynamic.dataservice.core.metaaccess.impl.DynamicEntityMetadataAccessImplBase;
import com.taoswork.tallybook.dynamic.dataservice.servicemockup.TallyMockupDataServiceDefinition;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Gao Yuan on 2015/6/2.
 */
@Component(DynamicEntityMetadataAccess.COMPONENT_NAME)
public class TallyMockupDynamicEntityMetadataAccess extends DynamicEntityMetadataAccessImplBase {
    @PersistenceContext(name = TallyMockupDataServiceDefinition.TMOCKUP_PU_NAME)
    protected EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

}
