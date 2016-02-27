package com.taoswork.tallybook.authority.core.permission.authorities;

import com.taoswork.tallybook.authority.core.permission.IKAuthority;
import com.taoswork.tallybook.authority.core.permission.IKPermission;
import com.taoswork.tallybook.authority.core.permission.impl.KPermission;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class DelegateKAuthority implements IKAuthority {
    private Set<IKAuthority> authorities = new HashSet<IKAuthority>();

    private static class PermissionMerger implements Consumer<IKAuthority> {
        private final String resource;
        private volatile int hitted = 0;
        public IKPermission result = null;

        public PermissionMerger(String resource) {
            this.resource = resource;
        }

        @Override
        public void accept(IKAuthority auth) {
            IKPermission got = auth.getPermission(resource);
            //if there is only one: return directly
            //if there is more that one: clone 1st one, merge others, and return
            if (got != null) {
                switch (hitted) {
                    case 0:
                        result = got;
                        break;
                    case 1:
                        result = new KPermission(result).merge(got);
                        break;
                    default:
                        ((KPermission) result).merge(got);
                }
                hitted++;
            }
        }
    }

    public DelegateKAuthority addAuthority(IKAuthority authority) {
        authorities.add(authority);
        return this;
    }

    @Override
    public IKPermission getPermission(final String resource) {
        PermissionMerger merger = new PermissionMerger(resource);
        authorities.forEach(merger);
        return merger.result;
    }
}
