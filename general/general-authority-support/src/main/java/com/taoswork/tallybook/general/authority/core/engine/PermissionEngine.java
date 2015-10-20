package com.taoswork.tallybook.general.authority.core.engine;

import com.taoswork.tallybook.general.authority.core.basic.ProtectionMode;
import com.taoswork.tallybook.general.authority.domain.resource.SecuredResource;
import com.taoswork.tallybook.general.authority.domain.resource.SecuredResourceFilter;
import com.taoswork.tallybook.general.authority.core.resource.impl.ResourceProtectionManager;
import com.taoswork.tallybook.general.authority.core.resource.impl.ResourceProtection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.*;

/**
 * Created by Gao Yuan on 2015/6/5.
 */
public class PermissionEngine {
    private static final Logger LOGGER = LoggerFactory.getLogger(PermissionEngine.class);
    private static final class ResourceProtectionMaker{
        public static ResourceProtection make(SecuredResource resource){
            ResourceProtection resourceProtection = new ResourceProtection(resource.getResourceEntity(), resource.getVersion());
            resourceProtection
//                .setFriendlyName(resource.getFriendlyName())
//                .setCategory(resource.getCategory())
                .setMasterControlled(resource.isMasterControlled());
            switch (resource.getProtectionMode()){
                case PassAll:
                    resourceProtection.setProtectionMode(ProtectionMode.FitAll);
                    break;
                case PassAny:
                    resourceProtection.setProtectionMode(ProtectionMode.FitAny);
                    break;
                default:
                    throw new IllegalStateException();
            }
            //TODO : ResourceProtection.filterNamespace need tobe handled
            //TODO: Filters need to be set

            return resourceProtection;
        }
    }

    protected EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    private void buildResources(Long organizationId) {
        TypedQuery<SecuredResource> query = em.createNamedQuery("SecuredResource.findByOrg", SecuredResource.class);
        query.setParameter("organization", organizationId);
        List<SecuredResource> resources = query.getResultList();

        ResourceProtectionManager resourceProtectionManager = new ResourceProtectionManager();
        for (SecuredResource resource : resources) {
            LOGGER.error("Handle resources");
//            if (resource.isMainLine()) {
//                resourceProtectionManager.setVersion(resource.getVersion());
//                continue;
//            } else {
//                ResourceProtection resourceProtection = ResourceProtectionMaker.make(resource);
//                resourceProtectionManager.registerResourceProtection(resourceProtection);
//            }
        }
        // em.createQuery()
    }

    private final Map<String, Set<SecuredResourceFilter>> resourceTypesMapping = new HashMap<String, Set<SecuredResourceFilter>>();
//    private final Map<Long, >

    private Set<SecuredResourceFilter> getResourceCriteriaSet(String resouceType){
        Set<SecuredResourceFilter> resourceCriteriaSet = resourceTypesMapping.get(resouceType);
        if(resourceCriteriaSet == null){
            resourceCriteriaSet = new HashSet<SecuredResourceFilter>();
//            resourceCriteriaSet.add(new ResourceCriteria().setType(resouceType).setName("General"));
            resourceTypesMapping.put(resouceType, resourceCriteriaSet);
        }
        return resourceCriteriaSet;
    }


}
