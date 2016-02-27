package com.taoswork.tallybook.authority.solution.mockup.domain.resource;

import com.taoswork.tallybook.authority.core.resource.IKProtection;
import com.taoswork.tallybook.authority.core.resource.KAccessibleScope;
import com.taoswork.tallybook.authority.solution.engine.filter.query.MorphiaQInterrupter;
import com.taoswork.tallybook.datadomain.base.entity.Persistable;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Criteria;
import org.mongodb.morphia.query.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gao Yuan on 2016/2/26.
 */
public class XFileRepo<T extends Persistable> implements ICoreAccessSensitiveRepo {
    private final Class<T> entityClz;
    private final Datastore datastore;

    public XFileRepo(Datastore datastore, Class<T> entityClz) {
        this.datastore = datastore;
        this.entityClz = entityClz;
    }

    @Override
    public List query(KAccessibleScope accessibleScope, IKProtection resourceProtection) {
        if (accessibleScope == null) {
            return new ArrayList();
        }
        Query<T> q = datastore.createQuery(entityClz);
        Query<T> query = datastore.createQuery(entityClz);
        Criteria c = MorphiaQInterrupter.buildCriteria(query, accessibleScope, resourceProtection);
        if (c != null) {
            q.and(c);
        }
        return q.asList();
    }
}
