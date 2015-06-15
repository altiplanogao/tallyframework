package com.taoswork.tallybook.general.authority.engine.utils;

import com.taoswork.tallybook.general.authority.domain.authority.permission.*;
import com.taoswork.tallybook.general.authority.domain.authority.access.ResourceAccess;
import com.taoswork.tallybook.general.authority.domain.authority.resource.ResourceCriteria;

import java.util.*;

/**
 * Created by Gao Yuan on 2015/4/16.
 */
public class PermissionUtility{
    public static PermissionUtility instance(){
        return new PermissionUtility();
    }
    /**
     *
     * @param container a container holds the result, could be null
     * @param roles
     * @return the container passed in, method will create one if the passed in value == null
     */
    public Set<Permission> appendAllRoles(Set<Permission> container, Iterable<Role> roles) {
        Set<Permission> result = container;
        if(null == result){
            result = new HashSet<Permission>();
        }
        for(Role r : roles){
            result.addAll(r.getAllPermissions());
        }
        return  result;
    }

    public Set<Permission> appendAllPermissions(Set<Permission> container, Iterable<Permission> permissions) {
        Set<Permission> result = container;
        if(null == result){
            result = new HashSet<Permission>();
        }
        for(Permission p : permissions){
            result.add(p);
        }
        return  result;
    }

    public Set<Permission> expandPermission(Permission permission, Set<Permission> container, boolean includeBranch, boolean onlyUserFriendly){
        if(null == container){
            container = new HashSet<Permission>();
        }
        boolean isBranch = true;
        List<Permission> childrenPermissions = permission.getChildrenPermission();
        if(null == childrenPermissions || childrenPermissions.size() == 0){
            isBranch = false;
            if((!onlyUserFriendly) || (onlyUserFriendly && permission.isUserFriendly())){
                container.add(permission);
            }
        } else{
            if(includeBranch){
                if((!onlyUserFriendly) || (onlyUserFriendly && permission.isUserFriendly())){
                    container.add(permission);
                }
            }
            for(Permission childPermission : permission.getChildrenPermission()){
                container = expandPermission(childPermission, container, includeBranch, onlyUserFriendly);
            }
            isBranch = true;
        }
        return container;
    }

    public Set<Permission> expandPermissions(Iterable<Permission> permissions, Set<Permission> container, boolean includeBranch, boolean onlyUserFriendly){
        if(null == container){
            container = new HashSet<Permission>();
        }
        for(Permission permission : permissions){
            container = expandPermission(permission, container, includeBranch, onlyUserFriendly);
        }

        return container;
    }

    public Set<PermissionEntry> expandPermissionQualifiedEntities(Iterable<Permission> permissions, Set<PermissionEntry> container){
        if(null == container){
            container = new HashSet<PermissionEntry>();
        }
        for(Permission permission : permissions){
            container.addAll(permission.getAllEntries());
        }
        return container;
    }

    public Map<ResourceCriteria, ResourceAccess> getAccessMapFromPermissionQualifiedEntities(Iterable<PermissionEntry> permissionEntities, Map<ResourceCriteria, ResourceAccess> accessMap){
        if(null == accessMap ){
            accessMap = new HashMap<ResourceCriteria, ResourceAccess>();
        }

        for(PermissionEntry qualifiedEntity : permissionEntities){
            ResourceAccess access = qualifiedEntity.getAccess();
            ResourceCriteria entityName = qualifiedEntity.getResourceCriteria();
            ResourceAccess oldAccess = accessMap.getOrDefault(entityName, new ResourceAccess());
            access.merge(oldAccess);
            accessMap.put(entityName, access);
        }
        return accessMap;
    }

    public Map<ResourceCriteria, ResourceAccess> getAccessMap(Iterable<Permission> permissions, Map<ResourceCriteria, ResourceAccess> accessMap){
        return getAccessMapFromPermissionQualifiedEntities(expandPermissionQualifiedEntities(permissions, null), accessMap);
    }
}
