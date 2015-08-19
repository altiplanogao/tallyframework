package com.taoswork.tallybook.general.authority.core.authority.access;

/**
 * Created by Gao Yuan on 2015/4/15.
 */
public final class AccessType {
    /**
     * CRUDQ access type is s
     */
    private int access;
    private int extendedAccess = 0x00;

    public final static int NONE = 0x00;
    public final static int CREATE = 0x01;
    public final static int READ = 0x02;
    public final static int UPDATE = 0x04;
    public final static int DELETE = 0x08;
    public final static int QUERY = 0x10;

    public final static int EXTENDED = 0x1000;

    private final static int CRUD_FULL_MASK = CREATE | READ | UPDATE | DELETE | QUERY;

    public final static AccessType None = new AccessType(NONE);
    public final static AccessType Create = new AccessType(CREATE);
    public final static AccessType Read = new AccessType(READ);
    public final static AccessType Update = new AccessType(UPDATE);
    public final static AccessType Delete = new AccessType(DELETE);
    public final static AccessType Query = new AccessType(QUERY);

    public final static AccessType extended(int accessBitMask){
        AccessType accessType = new AccessType(EXTENDED);
        accessType.extendedAccess = accessBitMask;
        return accessType;
    }

    private AccessType(int access) {
        this(access, 0x00);
    }

    private AccessType(int access, int extendedAccess) {
        this.access = (extendedAccess == 0x00 ? access : (access &= EXTENDED));
        this.extendedAccess = extendedAccess;
    }

    public int getAccess() {
        return access;
    }

    public boolean isCrud(){
        return (access & CRUD_FULL_MASK ) != NONE;
    }

    public boolean isExtended(){
        if(isCrud()){
            return false;
        }
        return extendedAccess != NONE;
    }

    public int getExtended(){
        return extendedAccess;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccessType that = (AccessType) o;

        if (access != that.access) return false;
        return extendedAccess == that.extendedAccess;

    }

    @Override
    public int hashCode() {
        int result = access;
        result = 31 * result + extendedAccess;
        return result;
    }
}
