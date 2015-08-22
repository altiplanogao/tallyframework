package com.taoswork.tallybook.general.authority.core.permission.impl;

import com.taoswork.tallybook.general.authority.core.basic.Access;
import com.taoswork.tallybook.general.authority.core.basic.ProtectionMode;
import com.taoswork.tallybook.general.authority.core.permission.IEntityPermission;
import com.taoswork.tallybook.general.authority.core.permission.IPermissionEntry;
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
 * Created by Gao Yuan on 2015/8/19.
 */
public final class EntityPermission implements IEntityPermissionWrite {
    private final String resourceEntity;
    /**
     * Key is IPermissionEntry.getFilterCode()
     */
    private final ConcurrentHashMap<String, IPermissionEntry> permissionEntries = new ConcurrentHashMap<String, IPermissionEntry>();
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
                for (IPermissionEntry permissionEntry : permissionEntries.values()) {
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
        IPermissionEntry entry = permissionEntries.get(filterCode);
        return entry != null ? entry.getAccess() : Access.None;
    }

    private Access fitAllAccessByFilters(boolean masterControlled, Collection<String> filterCodes) {
        Map<String, IPermissionEntry> map = new HashMap<String, IPermissionEntry>();
        synchronized (lock) {
            for (String code : filterCodes) {
                IPermissionEntry entry = permissionEntries.get(code);
                if (entry != null) {
                    map.put(code, entry);
                } else {
                    return Access.None;
                }
            }
        }

        if (masterControlled) {
            Access access = this.masterAccess;
            for (IPermissionEntry pe : map.values()) {
                access = access.and(pe.getAccess());
            }
            return access;
        } else {
            if (filterCodes.isEmpty()) {
                return this.masterAccess;
            }
            Access access = null;
            for (IPermissionEntry pe : map.values()) {
                if (access == null) {
                    access = pe.getAccess();
                } else {
                    access = access.and(pe.getAccess());
                }
            }
            return access;
        }
    }

    private Access fitAnyAccessByFilters(boolean masterControlled, Collection<String> filterCodes) {
        Map<String, IPermissionEntry> map = new HashMap<String, IPermissionEntry>();
        synchronized (lock) {
            for (String code : filterCodes) {
                IPermissionEntry entry = permissionEntries.get(code);
                if (entry != null) {
                    map.put(code, entry);
                }
            }
        }

        Access access = Access.None;
        if (filterCodes.isEmpty()) {
            access = masterAccess;
        } else {
            for (IPermissionEntry pe : map.values()) {
                access = access.or(pe.getAccess());
            }
            if (masterControlled) {
                access = access.and(masterAccess);
            }
        }

        return access;
    }

    @Override
    public IEntityPermission addEntries(IPermissionEntry... permEntries) {
        synchronized (lock) {
            for (IPermissionEntry entry : permEntries) {
                if (entry != null) {
                    permissionEntries.put(entry.getFilterCode(), entry);
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
            epthat.permissionEntries.forEach(new BiConsumer<String, IPermissionEntry>() {
                @Override
                public void accept(String s, final IPermissionEntry entryInThat) {

                    epthis.permissionEntries.computeIfPresent(s, new BiFunction<String, IPermissionEntry, IPermissionEntry>() {
                        @Override
                        public IPermissionEntry apply(String s, IPermissionEntry entryInThis) {
                            PermissionEntry thisEntryClone = new PermissionEntry(entryInThis);
                            thisEntryClone.merge(entryInThat);
                            return thisEntryClone;
                        }
                    });
                    epthis.permissionEntries.computeIfAbsent(s, new Function<String, IPermissionEntry>() {
                        @Override
                        public IPermissionEntry apply(String s) {
                            final IPermissionEntry thatEntryClone = entryInThat.clone();
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
            permissionEntries.forEach(new BiConsumer<String, IPermissionEntry>() {
                @Override
                public void accept(String s, IPermissionEntry permissionEntry) {
                    copy.permissionEntries.put(s, permissionEntry.clone());
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
        for (IPermissionEntry entry : permissionEntries.values()) {
            sb.append("\n\t" + entry + "");
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
            .append(permissionEntries, that.permissionEntries)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(resourceEntity)
            .append(masterAccess)
            .append(permissionEntries)
            .append(dirty)
            .toHashCode();
    }
}
