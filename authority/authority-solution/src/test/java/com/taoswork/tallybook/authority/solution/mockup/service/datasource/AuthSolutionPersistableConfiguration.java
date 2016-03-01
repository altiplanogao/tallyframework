package com.taoswork.tallybook.authority.solution.mockup.service.datasource;

import com.taoswork.tallybook.authority.solution.AuthoritySolutionDomain;
import com.taoswork.tallybook.authority.solution.mockup.domain.auth.TGroupAuthority;
import com.taoswork.tallybook.authority.solution.mockup.domain.auth.TUserAuthority;
import com.taoswork.tallybook.authority.solution.mockup.domain.resource.*;
import com.taoswork.tallybook.dataservice.mongo.config.MongoPersistableConfiguration;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gao Yuan on 2016/2/16.
 */
@Configuration
public class AuthSolutionPersistableConfiguration
        extends MongoPersistableConfiguration {

    @Override
    protected Class<?>[] createPersistableEntities() {
        List<Class> classes = new ArrayList<Class>();
        CollectionUtils.addAll(classes, AuthoritySolutionDomain.domainEntities());
        classes.add(TGroupAuthority.class);
        classes.add(TUserAuthority.class);
        classes.add(XFile.class);
        classes.add(CS0File.class);
        classes.add(CS1File.class);
        classes.add(CM0File.class);
        classes.add(CM1File.class);
        classes.add(CCFile.class);
        return classes.toArray(new Class[]{});
    }
}
