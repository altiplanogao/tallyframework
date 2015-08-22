package com.taoswork.tallybook.general.authority.core.engine;

import com.taoswork.tallybook.general.authority.core.authority.resource.SecuredResourceFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Gao Yuan on 2015/6/5.
 */
public class PermissionEngine {
    private static final Logger LOGGER = LoggerFactory.getLogger(PermissionEngine.class);

    private final Map<String, Set<SecuredResourceFilter>> resourceTypesMapping = new HashMap<String, Set<SecuredResourceFilter>>();
//    private final Map<Long, >

    private Set<SecuredResourceFilter> getResourceCriteriaSet(String resouceType){
        Set<SecuredResourceFilter> resourceCriteriaSet = resourceTypesMapping.getOrDefault(resouceType, null);
        if(resourceCriteriaSet == null){
            resourceCriteriaSet = new HashSet<SecuredResourceFilter>();
//            resourceCriteriaSet.add(new ResourceCriteria().setType(resouceType).setName("General"));
            resourceTypesMapping.put(resouceType, resourceCriteriaSet);
        }
        return resourceCriteriaSet;
    }

    public void registerResourceCriteria(SecuredResourceFilter resourceCriteria){
        Set<SecuredResourceFilter> resourceCriteriaSet = getResourceCriteriaSet(resourceCriteria.getSecuredResource().getResourceEntity());
        resourceCriteriaSet.add(resourceCriteria);
    }
//
//    public List<ResourceCriteria> findMatchingCriteria(String resourceType, Object entity){
//        List<ResourceCriteria> result = new ArrayList<ResourceCriteria>();
//        Set<ResourceCriteria> resourceCriteriaSet = getResourceCriteriaSet(resourceType);
//        for (ResourceCriteria securedResourceFilter : resourceCriteriaSet){
//            if(securedResourceFilter.isRootTypeCriteria()){
//                result.add(securedResourceFilter);
//            } else {
//                IResourceFilter filter = FilterHelper.createFilter(resourceType, securedResourceFilter.filter, securedResourceFilter.filterParameter);
//                if(filter == null){
//                    LOGGER.error("[IMPORTANT] Failed to initialize resourceFilter '{}'. But we still treat the resource as a match, in order to avoid permission leak");
//                    result.add(securedResourceFilter);
//                }else {
//                    if(filter.isMatch(entity)){
//                        result.add(securedResourceFilter);
//                    }
//                }
//            }
//        }
//
//        return result;
//    }
//

}
