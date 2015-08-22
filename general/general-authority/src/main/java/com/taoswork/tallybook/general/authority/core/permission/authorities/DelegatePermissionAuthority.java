package com.taoswork.tallybook.general.authority.core.permission.authorities;

import com.taoswork.tallybook.general.authority.core.permission.IEntityPermission;
import com.taoswork.tallybook.general.authority.core.permission.IPermissionAuthority;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Created by Gao Yuan on 2015/8/21.
 */
public class DelegatePermissionAuthority implements IPermissionAuthority {
    private Set<IPermissionAuthority> authorities = new HashSet<IPermissionAuthority>();

    private static class Holder{
        public IEntityPermission permission = null;
    }

    public DelegatePermissionAuthority addAuthority(IPermissionAuthority authority){
        authorities.add(authority);
        return this;
    }

    @Override
    public IEntityPermission getEntityPermission(final String resourceEntity) {
        final Holder holder = new Holder();
        authorities.forEach(new Consumer<IPermissionAuthority>() {
            @Override
            public void accept(IPermissionAuthority iPermissionAuthority) {
                IEntityPermission got = iPermissionAuthority.getEntityPermission(resourceEntity);
                if(holder.permission == null){
                    holder.permission = got.clone();
                }else {
                    holder.permission.merge(got);
                }
            }
        });
        return holder.permission;
    }
}
