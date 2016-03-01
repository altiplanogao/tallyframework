package com.taoswork.tallybook.authority.solution.engine.resource;

import com.taoswork.tallybook.authority.core.resource.IKProtection;
import com.taoswork.tallybook.authority.core.resource.IKProtectionCenter;
import com.taoswork.tallybook.authority.core.resource.impl.BaseKProtectionCenter;
import com.taoswork.tallybook.authority.core.resource.link.IKProtectionMapping;
import com.taoswork.tallybook.authority.solution.domain.resource.Protection;
import com.taoswork.tallybook.authority.solution.engine.PermissionObjectMaker;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Gao Yuan on 2016/2/24.
 */
public class ProtectionCenterTenant
        extends BaseKProtectionCenter
        implements IKProtectionCenter {

    private final Datastore datastore;
    private final IKProtectionMapping mapping;
    private final String protectionSpace;
    private final String tenantId;

    private final Map<String, IKProtection> resourceProtections = new ConcurrentHashMap<String, IKProtection>();

    public ProtectionCenterTenant(String protectionSpace, String tenantId,
                                  Datastore datastore,
                                  IKProtectionMapping mapping) {
        this.protectionSpace = protectionSpace;
        this.tenantId = tenantId;
        this.datastore = datastore;
        this.mapping = mapping;
    }

    public void buildAllRcProtection(){
        Query<Protection> q = datastore.createQuery(Protection.class);
        q.filter(Protection.FN_PROTECTION_SPACE, protectionSpace)
                .filter(Protection.FN_NAMESPACE, tenantId);
        List<Protection> protections = q.asList();
        if(protections != null){
            for (Protection sr : protections){
                String resource = sr.getResource();
                IKProtection protection = PermissionObjectMaker.convert(sr);
                resourceProtections.put(resource, protection);
            }
        }
    }

    public IKProtection getProtection(String resource){
        return getDirectResourceProtection(resource);
    }

    @Override
    protected IKProtectionMapping getMapping() {
        return mapping;
    }

    @Override
    protected IKProtection getDirectResourceProtection(String resource) {
        IKProtection protection = resourceProtections.get(resource);
        if(protection != null){
            return protection;
        }
        Query<Protection> q = datastore.createQuery(Protection.class);
        q.filter(Protection.FN_PROTECTION_SPACE, protectionSpace)
                .filter(Protection.FN_NAMESPACE, tenantId)
                .filter(Protection.FN_RESOURCE_ENTITY, resource);
        Protection sr = q.get();
        if(sr != null){
            protection = PermissionObjectMaker.convert(sr);
            resourceProtections.put(resource, protection);
            return protection;
        }
        return null;
    }
}
