package com.taoswork.tallybook.authority.core.permission.authorities;

import com.taoswork.tallybook.authority.core.permission.IKPermission;
import com.taoswork.tallybook.authority.core.permission.impl.KPermission;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by Gao Yuan on 2015/8/18.
 */
public class SimpleKAuthority implements ISimpleKAuthority {
    protected ConcurrentHashMap<String, IKPermission> permissionMap = new ConcurrentHashMap<String, IKPermission>();

    @Override
    public IKPermission getPermission(String resource) {
        return permissionMap.get(resource);
    }

    @Override
    public ISimpleKAuthority addPermission(IKPermission perm) {
        permissionMap.put(perm.getResource(), perm);
        return this;
    }

    @Override
    public ISimpleKAuthority addPermissions(IKPermission... permissions) {
        for (IKPermission perm : permissions) {
            permissionMap.put(perm.getResource(), perm);
        }
        return this;
    }

    @Override
    public ISimpleKAuthority merge(ISimpleKAuthority that) {
        SimpleKAuthority pathat = (SimpleKAuthority) that;
        final SimpleKAuthority pathis = this;
        if (pathat == null) {
            throw new IllegalArgumentException("Need to implement");
        }
        pathat.permissionMap.forEach(new BiConsumer<String, IKPermission>() {
            @Override
            public void accept(String s, final IKPermission entityInThat) {

                pathis.permissionMap.computeIfPresent(s, new BiFunction<String, IKPermission, IKPermission>() {
                    @Override
                    public IKPermission apply(String s, IKPermission entityInThis) {
                        KPermission thisEntityClone = new KPermission(entityInThis);
                        thisEntityClone.merge(entityInThat);
                        return thisEntityClone;
                    }
                });
                pathis.permissionMap.computeIfAbsent(s, new Function<String, IKPermission>() {
                    @Override
                    public IKPermission apply(String s) {
                        final IKPermission epThatClone = entityInThat.clone();
                        return epThatClone;
                    }
                });
            }
        });
        return this;
    }

    @Override
    public ISimpleKAuthority clone() {
        final SimpleKAuthority that = new SimpleKAuthority();
        permissionMap.forEach(new BiConsumer<String, IKPermission>() {
            @Override
            public void accept(String s, IKPermission entityPermission) {
                that.permissionMap.put(s, entityPermission.clone());
            }
        });
        return that;
    }

    @Override
    public String toString() {
        return "PermissionOwner{" +
                "permissionMap=" + permissionMap +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof SimpleKAuthority)) return false;

        SimpleKAuthority that = (SimpleKAuthority) o;

        return new EqualsBuilder()
                .append(permissionMap, that.permissionMap)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(permissionMap)
                .toHashCode();
    }
}
