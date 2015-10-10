package com.taoswork.tallybook.general.authority.core.permission.impl;

import com.taoswork.tallybook.general.authority.core.basic.Access;
import com.taoswork.tallybook.general.authority.core.basic.ProtectionMode;
import com.taoswork.tallybook.general.authority.core.permission.IEntityPermission;
import com.taoswork.tallybook.general.authority.core.permission.IEntityPermissionSpecial;
import com.taoswork.tallybook.general.authority.core.permission.wirte.IEntityPermissionWrite;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Defines EntityPermission for a resourceEntity,
 * for details, see IEntityPermission
 * ({@link com.taoswork.tallybook.general.authority.core.permission.IEntityPermission})
 */
public final class EntityPermission implements IEntityPermissionWrite {
    private final String resourceEntity;
    /**
     * Key is IEntityPermissionSpecial.getFilterCode()
     */
    private final ConcurrentHashMap<String, IEntityPermissionSpecial> permissionSpecials = new ConcurrentHashMap<String, IEntityPermissionSpecial>();
    private Access masterAccess = Access.None;

    private Object lock = new Object();
    private transient volatile boolean dirty = false;
    private transient volatile Access quickCheckAccess = Access.None;

    public EntityPermission(String resourceEntity) {
        this.resourceEntity = resourceEntity;
    }

    public EntityPermission(IEntityPermission that) {
        this.resourceEntity = that.getResourceEntity();
        this.merge(that);
    }

    @Override
    public String getResourceEntity() {
        return resourceEntity;
    }

    @Override
    public Access getMasterAccess() {
        synchronized (lock) {
            return masterAccess;
        }
    }

    @Override
    public void setMasterAccess(Access masterAccess) {
        synchronized (lock) {
            this.masterAccess = masterAccess;
            dirty = true;
        }
    }

    @Override
    public Access getQuickCheckAccess() {
        synchronized (lock) {
            if (dirty) {
                Access a = masterAccess;
                for (IEntityPermissionSpecial permissionEntry : permissionSpecials.values()) {
                    a = a.merge(permissionEntry.getAccess());
                }
                quickCheckAccess = a;
                dirty = false;
            }
            return quickCheckAccess;
        }
    }

    @Override
    public Access getAccessByFilters(Collection<String> filterCodes,
                                     boolean masterControlled, ProtectionMode protectionMode) {
        switch (protectionMode) {
            case FitAll:
                return this.fitAllAccessByFilters(masterControlled, filterCodes);
            case FitAny:
                return this.fitAnyAccessByFilters(masterControlled, filterCodes);
            default:
                throw new IllegalStateException();
        }
    }

    @Override
    public Access getAccessByFilter(String filterCode) {
        IEntityPermissionSpecial permSp = permissionSpecials.get(filterCode);
        return permSp != null ? permSp.getAccess() : Access.None;
    }

    /**
     * called by getAccessByFilters()
     * if(masterControlled)
     *      return masterAccess & [ filterAccess1 & filterAccess2 & ...]
     * if(!masterControlled)
     *      if (no filter)
     *          return masterAccess
     *      else
     *          return filterAccess1 & [ filterAccess2 & ...]
     *
     * @param masterControlled, if the resource is master controlled, see IResourceProtection
     * @param filterCodes, the filters selected to be checked.
     * @return the merged Access value
     */
    private Access fitAllAccessByFilters(boolean masterControlled, Collection<String> filterCodes) {
        Map<String, IEntityPermissionSpecial> map = new HashMap<String, IEntityPermissionSpecial>();
        synchronized (lock) {
            for (String code : filterCodes) {
                IEntityPermissionSpecial permSp = permissionSpecials.get(code);
                if (permSp != null) {
                    map.put(code, permSp);
                } else {
                    return Access.None;
                }
            }
        }

        if (masterControlled) {
            Access access = this.masterAccess;
            for (IEntityPermissionSpecial pe : map.values()) {
                access = access.and(pe.getAccess());
            }
            return access;
        } else {
            if (filterCodes.isEmpty()) {
                return this.masterAccess;
            }
            Access access = null;
            for (IEntityPermissionSpecial pe : map.values()) {
                if (access == null) {
                    access = pe.getAccess();
                } else {
                    access = access.and(pe.getAccess());
                }
            }
            return access;
        }
    }

    /**
     * called by getAccessByFilters()
     *
     * if(masterControlled)
     *      return masterControlled & [filterAccess1 + filterAccess2 + ...]
     * else
     *      if (no filter)
     *          return masterAccess
     *      else
     *          return [filterAccess1 + filterAccess2 + ...]
     *
     * @param masterControlled, if the resource is master controlled, see IResourceProtection
     * @param filterCodes, the filters selected to be checked.
     * @return
     */
    private Access fitAnyAccessByFilters(boolean masterControlled, Collection<String> filterCodes) {
        Map<String, IEntityPermissionSpecial> map = new HashMap<String, IEntityPermissionSpecial>();
        synchronized (lock) {
            for (String code : filterCodes) {
                IEntityPermissionSpecial permSp = permissionSpecials.get(code);
                if (permSp != null) {
                    map.put(code, permSp);
                }
            }
        }

        Access access = Access.None;
        if (filterCodes.isEmpty()) {
            access = masterAccess;
        } else {
            for (IEntityPermissionSpecial pe : map.values()) {
                access = access.or(pe.getAccess());
            }
            if (masterControlled) {
                access = access.and(masterAccess);
            }
        }

        return access;
    }

    @Override
    public IEntityPermission addSpecials(IEntityPermissionSpecial... permSpecials) {
        synchronized (lock) {
            for (IEntityPermissionSpecial permSp : permSpecials) {
                if (permSp != null) {
                    permissionSpecials.put(permSp.getFilterCode(), permSp);
                }
            }
            dirty = true;
            return this;
        }
    }

    @Override
    public IEntityPermissionWrite merge(IEntityPermission that) {
        if(that == null)
            return this;
        if (!resourceEntity.equals(that.getResourceEntity())) {
            throw new IllegalArgumentException();
        }
        synchronized (lock) {
            EntityPermission epthat = (EntityPermission) that;
            final  EntityPermission epthis = this;
            if (epthat == null) {
                throw new IllegalStateException("Need to implement !!");
            }

            masterAccess = masterAccess.merge(epthat.masterAccess);
            quickCheckAccess = Access.None;

            //copy that, into this
            epthat.permissionSpecials.forEach(new BiConsumer<String, IEntityPermissionSpecial>() {
                @Override
                public void accept(String s, final IEntityPermissionSpecial permSpInThat) {

                    epthis.permissionSpecials.computeIfPresent(s, new BiFunction<String, IEntityPermissionSpecial, IEntityPermissionSpecial>() {
                        @Override
                        public IEntityPermissionSpecial apply(String s, IEntityPermissionSpecial permSpInThis) {
                            EntityPermissionSpecial thisEntryClone = new EntityPermissionSpecial(permSpInThis);
                            thisEntryClone.merge(permSpInThat);
                            return thisEntryClone;
                        }
                    });
                    epthis.permissionSpecials.computeIfAbsent(s, new Function<String, IEntityPermissionSpecial>() {
                        @Override
                        public IEntityPermissionSpecial apply(String s) {
                            final IEntityPermissionSpecial thatEntryClone = permSpInThat.clone();
                            return thatEntryClone;
                        }
                    });
                }
            });
            dirty = true;
        }
        return this;
    }

    @Override
    public IEntityPermission clone() {
        synchronized (lock) {
            final EntityPermission copy = new EntityPermission(resourceEntity);
            copy.masterAccess = masterAccess;
            permissionSpecials.forEach(new BiConsumer<String, IEntityPermissionSpecial>() {
                @Override
                public void accept(String s, IEntityPermissionSpecial permissionEntry) {
                    copy.permissionSpecials.put(s, permissionEntry.clone());
                }
            });
            copy.dirty = true;
            return copy;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("EntityPermission{'" + resourceEntity + "'");
        sb.append(", master=" + masterAccess)
            .append(", merged=" + getQuickCheckAccess())
            .append(", [");
        for (IEntityPermissionSpecial permSp : permissionSpecials.values()) {
            sb.append("\n\t" + permSp + "");
        }
        sb.append("]}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof EntityPermission)) return false;

        EntityPermission that = (EntityPermission) o;

        return new EqualsBuilder()
            .append(dirty, that.dirty)
            .append(resourceEntity, that.resourceEntity)
            .append(masterAccess, that.masterAccess)
            .append(permissionSpecials, that.permissionSpecials)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(resourceEntity)
            .append(masterAccess)
            .append(permissionSpecials)
            .append(dirty)
            .toHashCode();
    }
}
