package com.taoswork.tallybook.authority.solution.engine;

import com.taoswork.tallybook.authority.core.permission.IKAuthority;
import com.taoswork.tallybook.authority.core.resource.IKProtectionCenter;
import com.taoswork.tallybook.authority.core.resource.link.IKProtectionMapping;
import com.taoswork.tallybook.authority.core.verifier.IKAccessVerifier;
import com.taoswork.tallybook.authority.core.verifier.impl.KAccessVerifier;
import com.taoswork.tallybook.authority.solution.domain.ProtectionSpace;
import com.taoswork.tallybook.authority.solution.domain.resource.Protection;
import com.taoswork.tallybook.authority.solution.domain.user.PersonAuthority;
import com.taoswork.tallybook.authority.solution.engine.resource.ProtectionCenterTenant;
import com.taoswork.tallybook.dataservice.mongo.core.entityservice.MongoEntityService;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by Gao Yuan on 2015/6/5.
 */
public class PermissionEngine implements IPermissionEngine {
    private static final Logger LOGGER = LoggerFactory.getLogger(PermissionEngine.class);

    private final MongoEntityService entityService;
    private final Datastore datastore;

    public PermissionEngine(MongoEntityService entityService) {
        this.entityService = entityService;
        this.datastore = entityService.getDatastore();
    }

    @Override
    public IKProtectionMapping getProtectionMapping(String protectionSpace) {
        Query<ProtectionSpace> q = datastore.createQuery(ProtectionSpace.class);
        q.filter(ProtectionSpace.FN_SPACE_NAME, protectionSpace);
        ProtectionSpace ps = q.get();
        if(ps != null)
            return ps.convert();
        return null;
    }

    @Override
    public IKProtectionCenter getProtectionCenter(String protectionSpace, String tenantId){
        IKProtectionMapping mapping = getProtectionMapping(protectionSpace);

        Query<Protection> q = datastore.createQuery(Protection.class);
        q.filter(Protection.FN_PROTECTION_SPACE, protectionSpace)
        .filter(Protection.FN_TENANT_ID, tenantId);
        List<Protection> protections = q.asList();
        if(protections != null){
            ProtectionCenterTenant pct =
                    new ProtectionCenterTenant(protectionSpace, tenantId, datastore, mapping);
            return pct;
        }
        return null;
    }

    @Override
    public IKAuthority getAuthority(String protectionSpace, String tenantId, String userId) {
        Datastore datastore = entityService.getDatastore();
        Query<PersonAuthority> q = datastore.createQuery(PersonAuthority.class);
        q.filter(PersonAuthority.FN_PROTECTION_SPACE, protectionSpace)
                .filter(PersonAuthority.FN_TENANT_ID, tenantId)
                .filter(PersonAuthority.FN_OWNER_ID, userId);
        PersonAuthority pa = q.get();
        if(pa != null){
            return PermissionObjectMaker.makeAuthority(pa);
        }
        return null;
    }

    @Override
    public IKAccessVerifier getAccessVerifier(String protectionSpace, String tenantId) {
        IKAccessVerifier accessVerifier = new KAccessVerifier(getProtectionCenter(protectionSpace, tenantId), getProtectionMapping(protectionSpace));
        return accessVerifier;
    }
}
