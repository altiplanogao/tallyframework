package com.taoswork.tallybook.authority.solution.engine;

import com.taoswork.tallybook.authority.core.Access;
import com.taoswork.tallybook.authority.core.permission.IKAuthority;
import com.taoswork.tallybook.authority.core.permission.IKPermission;
import com.taoswork.tallybook.authority.core.permission.impl.KPermission;
import com.taoswork.tallybook.authority.core.permission.impl.KPermissionCase;
import com.taoswork.tallybook.authority.core.resource.IKProtection;
import com.taoswork.tallybook.authority.core.resource.IKProtectionCase;
import com.taoswork.tallybook.authority.core.resource.impl.KProtection;
import com.taoswork.tallybook.authority.solution.domain.ResourceAccess;
import com.taoswork.tallybook.authority.solution.domain.permission.Permission;
import com.taoswork.tallybook.authority.solution.domain.permission.PermissionCase;
import com.taoswork.tallybook.authority.solution.domain.resource.ProtectionCase;
import com.taoswork.tallybook.authority.solution.domain.resource.Protection;
import com.taoswork.tallybook.authority.solution.domain.user.PersonAuthority;
import com.taoswork.tallybook.authority.solution.engine.authority.PersonKAuthority;
import com.taoswork.tallybook.authority.solution.engine.filter.FilterManager;
import com.taoswork.tallybook.authority.solution.engine.filter.IFilter;
import com.taoswork.tallybook.authority.solution.engine.resource.ResourceProtectionCase;

import java.util.Map;

/**
 * Created by Gao Yuan on 2016/2/24.
 */
public class PermissionObjectMaker {
    public static IKProtection convert(Protection sr){
        String resource = sr.getResource();
        KProtection protection = new KProtection(resource);
        protection.setMasterControlled(sr.isMasterControlled());
        protection.setProtectionMode(sr.getProtectionMode());
        Map<String, ProtectionCase> cases = sr.getCases();
        if(cases != null){
            for(ProtectionCase _case : cases.values()){
                IKProtectionCase icase = makeRcProtectionCase(resource, _case);
                if(icase != null){
                    protection.addCase(icase);
                }
            }
        }
        return protection;
    }

    public static IKProtectionCase makeRcProtectionCase(String resource, ProtectionCase _case){
        ResourceProtectionCase rpc = new ResourceProtectionCase(_case.getUuid());
        IFilter filter = FilterManager.getFilter(resource, _case.filter, _case.filterParameter);
        rpc.setFilter(filter);
        return rpc;
    }

    public static IKAuthority makeAuthority(PersonAuthority personPermissions){
        PersonKAuthority personAuthority = new PersonKAuthority(personPermissions.getTenantId(), personPermissions.getOwnerId());
        Map<String, Permission> permissions = personPermissions.getPermissions();
        if(permissions != null){
            for (Permission permission : permissions.values()){
                IKPermission rcp = convert(permission);
                personAuthority.addPermission(rcp);
            }
        }
        return personAuthority;
    }

    private static KPermission convert(Permission permission) {
        KPermission rcPermission = new KPermission(permission.getResource());
        rcPermission.setMasterAccess(ResourceAccess.getAsAccess(permission.getAccess()));

        Map<String, PermissionCase> cases = permission.getPermissionCases();
        if(cases != null) {
            for (PermissionCase _case : permission.getPermissionCases().values()) {
                String code = _case.getCode();
                Access access = ResourceAccess.getAsAccess(_case.getAccess());
                KPermissionCase pcs = new KPermissionCase(code, access);
                rcPermission.addCase(pcs);
            }
        }
        return rcPermission;
    }
}
