package com.taoswork.tallybook.dataservice.mongo.servicemockup.datasource;

import com.taoswork.tallybook.dataservice.mongo.config.MongoPersistableConfiguration;
import com.taoswork.tallybook.testmaterial.mongo.domain.business.impl.CompanyImpl;
import com.taoswork.tallybook.testmaterial.mongo.domain.nature.Citizen;
import com.taoswork.tallybook.testmaterial.mongo.domain.zoo.impl.ZooKeeperImpl;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Gao Yuan on 2016/2/16.
 */
@Configuration
public class TallyMockupPersistableConfiguration
        extends MongoPersistableConfiguration {

    @Override
    protected Class<?>[] createPersistableEntities() {
        return new Class<?>[]{
                ZooKeeperImpl.class,
                CompanyImpl.class,
                Citizen.class
        };
    }
}
