package com.taoswork.tallybook.authority.solution.engine;

import com.taoswork.tallybook.authority.core.permission.IKAuthority;
import com.taoswork.tallybook.authority.core.permission.authorities.ISimpleKAuthority;
import com.taoswork.tallybook.authority.core.resource.IKProtectionCenter;
import com.taoswork.tallybook.authority.core.resource.link.IKProtectionMapping;
import com.taoswork.tallybook.authority.core.verifier.IKAccessVerifier;
import com.taoswork.tallybook.authority.core.verifier.impl.KAccessVerifier;
import com.taoswork.tallybook.authority.solution.domain.ProtectionSpace;
import com.taoswork.tallybook.authority.solution.domain.resource.Protection;
import com.taoswork.tallybook.authority.solution.domain.user.GroupAuthority;
import com.taoswork.tallybook.authority.solution.domain.user.UserAuthority;
import com.taoswork.tallybook.authority.solution.engine.resource.ProtectionCenterTenant;
import com.taoswork.tallybook.dataservice.mongo.core.entityservice.MongoEntityService;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/6/5.
 */
public class PermissionEngine
        implements IPermissionEngine {
    private static final Logger LOGGER = LoggerFactory.getLogger(PermissionEngine.class);

    private final MongoEntityService entityService;
    private final Datastore datastore;

    private final Class<? extends ProtectionSpace> psClz;
    private final Class<? extends Protection> pClz;
    private final Class<? extends UserAuthority> userClz;
    private final Class<? extends GroupAuthority> groupClz;

    public PermissionEngine(MongoEntityService entityService,
                            Class<? extends UserAuthority> userClz,
                            Class<? extends GroupAuthority> groupClz) {
        this(entityService, ProtectionSpace.class, Protection.class, userClz, groupClz);
    }

    public PermissionEngine(MongoEntityService entityService,
                            Class<? extends ProtectionSpace> protectionSpaceClz,
                            Class<? extends Protection> protectionClz,
                            Class<? extends UserAuthority> userClz,
                            Class<? extends GroupAuthority> groupClz) {
        this.entityService = entityService;
        this.datastore = entityService.getDatastore();
        this.psClz = protectionSpaceClz;
        this.pClz = protectionClz;
        this.userClz = userClz;
        this.groupClz = groupClz;
    }

    @Override
    public IKProtectionMapping getProtectionMapping(String protectionSpace) {
        Query<? extends ProtectionSpace> q = datastore.createQuery(psClz);
        q.filter(ProtectionSpace.FN_SPACE_NAME, protectionSpace);
        ProtectionSpace ps = q.get();
        if (ps != null)
            return ps.convert();
        return null;
    }

    @Override
    public IKProtectionCenter getProtectionCenter(String protectionSpace, String tenantId) {
        IKProtectionMapping mapping = getProtectionMapping(protectionSpace);

        Query<? extends Protection> q = datastore.createQuery(pClz);
        q.filter(Protection.FN_PROTECTION_SPACE, protectionSpace)
                .filter(Protection.FN_NAMESPACE, tenantId);
        List<? extends Protection> protections = q.asList();
        if (protections != null) {
            ProtectionCenterTenant pct =
                    new ProtectionCenterTenant(protectionSpace, tenantId, datastore, mapping);
            return pct;
        }
        return null;
    }

    @Override
    public IKAccessVerifier getAccessVerifier(String protectionSpace, String tenantId) {
        IKAccessVerifier accessVerifier = new KAccessVerifier(getProtectionCenter(protectionSpace, tenantId), getProtectionMapping(protectionSpace));
        return accessVerifier;
    }

    @Override
    public IKAuthority getAuthority(String protectionSpace, String tenantId, String userId) {
        Datastore datastore = entityService.getDatastore();
        Query<? extends UserAuthority> q = datastore.createQuery(userClz);
        q.filter(UserAuthority.FN_PROTECTION_SPACE, protectionSpace)
                .filter(UserAuthority.FN_NAMESPACE, tenantId)
                .filter(UserAuthority.FN_OWNER_ID, userId);
        UserAuthority ua = q.get();
        if(ua != null){
            ISimpleKAuthority kua = PermissionObjectMaker.makeAuthority(ua);
            Collection<? extends GroupAuthority> gas = ua.theGroups();
            for(GroupAuthority ga : gas){
                if(ga != null) {
                    ISimpleKAuthority kga = PermissionObjectMaker.makeAuthority(ga);
                    kua.merge(kga);
                }
            }
            return kua;
        }
        return null;
    }

    private ISimpleKAuthority getRawGroupAuthority(String protectionSpace, String tenantId, String groupId) {
        Datastore datastore = entityService.getDatastore();
        Query<? extends GroupAuthority> q = datastore.createQuery(groupClz);
        q.filter(UserAuthority.FN_PROTECTION_SPACE, protectionSpace)
                .filter(UserAuthority.FN_NAMESPACE, tenantId)
                .filter(UserAuthority.FN_OWNER_ID, groupId);
        GroupAuthority ga = q.get();
        if(ga != null){
            return PermissionObjectMaker.makeAuthority(ga);
        }
        return null;
    }

}
