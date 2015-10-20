package com.taoswork.tallybook.general.authority.core.permission.authorities;

import com.taoswork.tallybook.general.authority.core.permission.IEntityPermission;
import com.taoswork.tallybook.general.authority.core.permission.impl.EntityPermission;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by Gao Yuan on 2015/8/18.
 */
public class SimplePermissionAuthority implements ISimplePermissionAuthority {
    private ConcurrentHashMap<String, IEntityPermission> entityPermissionMap = new ConcurrentHashMap<String, IEntityPermission>();

    @Override
    public IEntityPermission getEntityPermission(String resourceEntity) {
        return entityPermissionMap.get(resourceEntity);
    }

    @Override
    public ISimplePermissionAuthority addEntityPermission(IEntityPermission... entityPermissions) {
        for (IEntityPermission perm : entityPermissions) {
            entityPermissionMap.put(perm.getResourceEntity(), perm);
        }
        return this;
    }

    @Override
    public ISimplePermissionAuthority merge(ISimplePermissionAuthority that) {
        SimplePermissionAuthority pathat = (SimplePermissionAuthority) that;
        final SimplePermissionAuthority pathis = this;
        if (pathat == null) {
            throw new IllegalArgumentException("Need to implement");
        }
        pathat.entityPermissionMap.forEach(new BiConsumer<String, IEntityPermission>() {
            @Override
            public void accept(String s, final IEntityPermission entityInThat) {

                pathis.entityPermissionMap.computeIfPresent(s, new BiFunction<String, IEntityPermission, IEntityPermission>() {
                    @Override
                    public IEntityPermission apply(String s, IEntityPermission entityInThis) {
                        EntityPermission thisEntityClone = new EntityPermission(entityInThis);
                        thisEntityClone.merge(entityInThat);
                        return thisEntityClone;
                    }
                });
                pathis.entityPermissionMap.computeIfAbsent(s, new Function<String, IEntityPermission>() {
                    @Override
                    public IEntityPermission apply(String s) {
                        final IEntityPermission epThatClone = entityInThat.clone();
                        return epThatClone;
                    }
                });
            }
        });
        return this;
    }

    @Override
    public ISimplePermissionAuthority clone() {
        final SimplePermissionAuthority that = new SimplePermissionAuthority();
        entityPermissionMap.forEach(new BiConsumer<String, IEntityPermission>() {
            @Override
            public void accept(String s, IEntityPermission entityPermission) {
                that.entityPermissionMap.put(s, entityPermission.clone());
            }
        });
        return that;
    }

    @Override
    public String toString() {
        return "PermissionOwner{" +
            "entityPermissionMap=" + entityPermissionMap +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof SimplePermissionAuthority)) return false;

        SimplePermissionAuthority that = (SimplePermissionAuthority) o;

        return new EqualsBuilder()
            .append(entityPermissionMap, that.entityPermissionMap)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(entityPermissionMap)
            .toHashCode();
    }
}
