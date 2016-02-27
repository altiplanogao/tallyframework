package com.taoswork.tallybook.authority.core.permission.impl;

import com.taoswork.tallybook.authority.core.Access;
import com.taoswork.tallybook.authority.core.IllegalCodePathException;
import com.taoswork.tallybook.authority.core.ProtectionMode;
import com.taoswork.tallybook.authority.core.permission.IKPermission;
import com.taoswork.tallybook.authority.core.permission.IKPermissionCase;
import com.taoswork.tallybook.authority.core.permission.wirte.IKPermissionW;
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
 * Defines KPermission for a resource,
 * for details, see IKPermission
 * ({@link IKPermission})
 */
public final class KPermission implements IKPermissionW {
    private final String resource;
    /**
     * Key is IKPermissionCase.getCode()
     */
    private final ConcurrentHashMap<String, IKPermissionCase> cases = new ConcurrentHashMap<String, IKPermissionCase>();
    private Access masterAccess = Access.None;

    private Object lock = new Object();
    private transient volatile boolean dirty = false;
    private transient volatile Access quickCheckAccess = Access.None;

    public KPermission(String resource) {
        this.resource = resource;
    }

    public KPermission(IKPermission that) {
        this.resource = that.getResource();
        this.merge(that);
    }

    @Override
    public String getResource() {
        return resource;
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
                for (IKPermissionCase _case : cases.values()) {
                    a = a.merge(_case.getAccess());
                }
                quickCheckAccess = a;
                dirty = false;
            }
            return quickCheckAccess;
        }
    }

    @Override
    public Access getAccessByCases(Collection<String> caseCodes,
                                   boolean masterControlled, ProtectionMode protectionMode) {
        switch (protectionMode) {
            case FitAll:
                return this.fitAllAccessByCases(masterControlled, caseCodes);
            case FitAny:
                return this.fitAnyAccessByCases(masterControlled, caseCodes);
            default:
                throw new IllegalCodePathException();
        }
    }

    @Override
    public Access getCaseAccess(String caseCode) {
        IKPermissionCase permCase = cases.get(caseCode);
        return permCase != null ? permCase.getAccess() : Access.None;
    }

    /**
     * called by getAccessByCases()
     * if(masterControlled)
     * return masterAccess & [ caseAccess1 & caseAccess2 & ...]
     * if(!masterControlled)
     * if (no 'case')
     * return masterAccess
     * else
     * return caseAccess1 & [ caseAccess2 & ...]
     *
     * @param masterControlled, if the resource is master controlled, see IKProtection
     * @param caseCodes,        the cases selected to be checked.
     * @return the merged Access value
     */
    private Access fitAllAccessByCases(boolean masterControlled, Collection<String> caseCodes) {
        Map<String, IKPermissionCase> map = new HashMap<String, IKPermissionCase>();
        synchronized (lock) {
            for (String code : caseCodes) {
                IKPermissionCase permCase = cases.get(code);
                if (permCase != null) {
                    map.put(code, permCase);
                } else {
                    return Access.None;
                }
            }
        }

        if (masterControlled) {
            Access access = this.masterAccess;
            for (IKPermissionCase pc : map.values()) {
                access = access.and(pc.getAccess());
            }
            return access;
        } else {
            if (caseCodes.isEmpty()) {
                return this.masterAccess;
            }
            Access access = null;
            for (IKPermissionCase pe : map.values()) {
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
     * called by getAccessByCases()
     * <p>
     * if(masterControlled)
     * return masterControlled & [caseAccess1 + caseAccess2 + ...]
     * else
     * if (no 'case')
     * return masterAccess
     * else
     * return [caseAccess1 + caseAccess2 + ...]
     *
     * @param masterControlled, if the resource is master controlled, see IKProtection
     * @param caseCodes,        the cases selected to be checked.
     * @return
     */
    private Access fitAnyAccessByCases(boolean masterControlled, Collection<String> caseCodes) {
        Map<String, IKPermissionCase> map = new HashMap<String, IKPermissionCase>();
        synchronized (lock) {
            for (String code : caseCodes) {
                IKPermissionCase permSp = cases.get(code);
                if (permSp != null) {
                    map.put(code, permSp);
                }
            }
        }

        Access access = Access.None;
        if (caseCodes.isEmpty()) {
            access = masterAccess;
        } else {
            for (IKPermissionCase pe : map.values()) {
                access = access.or(pe.getAccess());
            }
            if (masterControlled) {
                access = access.and(masterAccess);
            }
        }

        return access;
    }

    @Override
    public IKPermission addCase(IKPermissionCase _case) {
        if (_case != null) {
            synchronized (lock) {
                this.cases.put(_case.getCode(), _case);
                dirty = true;
            }
        }
        return this;
    }

    @Override
    public IKPermission addCases(IKPermissionCase... cases) {
        synchronized (lock) {
            for (IKPermissionCase _case : cases) {
                if (_case != null) {
                    this.cases.put(_case.getCode(), _case);
                }
            }
            dirty = true;
            return this;
        }
    }

    @Override
    public IKPermissionW merge(IKPermission that) {
        if (that == null)
            return this;
        if (!resource.equals(that.getResource())) {
            throw new IllegalArgumentException();
        }
        synchronized (lock) {
            KPermission epthat = (KPermission) that;
            final KPermission epthis = this;
            if (epthat == null) {
                throw new IllegalCodePathException("Need to implement !!");
            }

            masterAccess = masterAccess.merge(epthat.masterAccess);
            quickCheckAccess = Access.None;

            //copy that, into this
            epthat.cases.forEach(new BiConsumer<String, IKPermissionCase>() {
                @Override
                public void accept(String s, final IKPermissionCase permSpInThat) {

                    epthis.cases.computeIfPresent(s, new BiFunction<String, IKPermissionCase, IKPermissionCase>() {
                        @Override
                        public IKPermissionCase apply(String s, IKPermissionCase permSpInThis) {
                            KPermissionCase thisEntryClone = new KPermissionCase(permSpInThis);
                            thisEntryClone.merge(permSpInThat);
                            return thisEntryClone;
                        }
                    });
                    epthis.cases.computeIfAbsent(s, new Function<String, IKPermissionCase>() {
                        @Override
                        public IKPermissionCase apply(String s) {
                            final IKPermissionCase thatEntryClone = permSpInThat.clone();
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
    public IKPermission clone() {
        synchronized (lock) {
            final KPermission copy = new KPermission(resource);
            copy.masterAccess = masterAccess;
            cases.forEach(new BiConsumer<String, IKPermissionCase>() {
                @Override
                public void accept(String s, IKPermissionCase permissionEntry) {
                    copy.cases.put(s, permissionEntry.clone());
                }
            });
            copy.dirty = true;
            return copy;
        }
    }

    @Override
    public String toString() {
        String shortRes = resource.substring(resource.lastIndexOf("-") + 1);
        StringBuilder sb = new StringBuilder("KPermission{'" + shortRes + "'");
        sb.append(", master=" + masterAccess)
                .append(", merged=" + getQuickCheckAccess())
                .append(", [");
        for (IKPermissionCase permSp : cases.values()) {
            sb.append("\n\t" + permSp + "");
        }
        sb.append("]}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof KPermission)) return false;

        KPermission that = (KPermission) o;

        return new EqualsBuilder()
                .append(dirty, that.dirty)
                .append(resource, that.resource)
                .append(masterAccess, that.masterAccess)
                .append(cases, that.cases)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(resource)
                .append(masterAccess)
                .append(cases)
                .append(dirty)
                .toHashCode();
    }
}
