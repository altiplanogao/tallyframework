package com.taoswork.tallybook.general.authority.engine;

import com.taoswork.tallybook.general.authority.domain.authority.resource.ResourceCriteria;
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

    private final Map<String, Set<ResourceCriteria>> resourceTypesMapping = new HashMap<String, Set<ResourceCriteria>>();
//    private final Map<Long, >

    private Set<ResourceCriteria> getResourceCriteriaSet(String resouceType){
        Set<ResourceCriteria> resourceCriteriaSet = resourceTypesMapping.getOrDefault(resouceType, null);
        if(resourceCriteriaSet == null){
            resourceCriteriaSet = new HashSet<ResourceCriteria>();
//            resourceCriteriaSet.add(new ResourceCriteria().setType(resouceType).setName("General"));
            resourceTypesMapping.put(resouceType, resourceCriteriaSet);
        }
        return resourceCriteriaSet;
    }

    public void registerResourceCriteria(ResourceCriteria resourceCriteria){
        Set<ResourceCriteria> resourceCriteriaSet = getResourceCriteriaSet(resourceCriteria.getResourceType().getType());
        resourceCriteriaSet.add(resourceCriteria);
    }
//
//    public List<ResourceCriteria> findMatchingCriteria(String resourceType, Object entity){
//        List<ResourceCriteria> result = new ArrayList<ResourceCriteria>();
//        Set<ResourceCriteria> resourceCriteriaSet = getResourceCriteriaSet(resourceType);
//        for (ResourceCriteria resourceCriteria : resourceCriteriaSet){
//            if(resourceCriteria.isRootTypeCriteria()){
//                result.add(resourceCriteria);
//            } else {
//                IResourceFilter filter = FilterHelper.createFilter(resourceType, resourceCriteria.filter, resourceCriteria.filterParameter);
//                if(filter == null){
//                    LOGGER.error("[IMPORTANT] Failed to initialize resourceFilter '{}'. But we still treat the resource as a match, in order to avoid permission leak");
//                    result.add(resourceCriteria);
//                }else {
//                    if(filter.isMatch(entity)){
//                        result.add(resourceCriteria);
//                    }
//                }
//            }
//        }
//
//        return result;
//    }
//

}
