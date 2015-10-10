package com.taoswork.tallybook.general.authority.core.permission.authorities;

import com.taoswork.tallybook.general.authority.core.permission.IEntityPermission;
import com.taoswork.tallybook.general.authority.core.permission.IPermissionAuthority;
import com.taoswork.tallybook.general.authority.core.permission.impl.EntityPermission;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class DelegatePermissionAuthority implements IPermissionAuthority {
    private Set<IPermissionAuthority> authorities = new HashSet<IPermissionAuthority>();

    private static class CalcAndMergeEntityPermission implements Consumer<IPermissionAuthority>{
        private final String resourceEntity;
        private volatile int hitted = 0;
        public IEntityPermission result = null;

        public CalcAndMergeEntityPermission(String resourceEntity) {
            this.resourceEntity = resourceEntity;
        }

        @Override
        public void accept(IPermissionAuthority auth) {
            IEntityPermission got = auth.getEntityPermission(resourceEntity);
            //if there is only one: return directly
            //if there is more that one: clone 1st one, merge others, and return
            if(got != null){
                switch (hitted){
                    case 0:
                        result = got; break;
                    case 1:
                        result = new EntityPermission(result).merge(got);
                        break;
                    default:
                        ((EntityPermission)result).merge(got);
                }
                hitted++;
            }
        }
    }

    public DelegatePermissionAuthority addAuthority(IPermissionAuthority authority){
        authorities.add(authority);
        return this;
    }

    @Override
    public IEntityPermission getEntityPermission(final String resourceEntity) {
        CalcAndMergeEntityPermission valCalc = new CalcAndMergeEntityPermission(resourceEntity);
        authorities.forEach(valCalc);
        return valCalc.result;
    }
}
