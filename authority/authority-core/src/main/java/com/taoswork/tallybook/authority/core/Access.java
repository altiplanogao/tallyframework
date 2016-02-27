package com.taoswork.tallybook.authority.core;

/**
 * Resource access method
 */
public final class Access {
    public final static int NONE = 0x00;
    public final static int CREATE = 0x01;
    public final static int READ = 0x02;
    public final static int UPDATE = 0x04;
    public final static int DELETE = 0x08;
    public final static int QUERY = 0x10;
    public final static int CRUDQ_ALL = CREATE | READ | UPDATE | DELETE | QUERY;

    public final static Access None = new Access(NONE);
    public final static Access Create = new Access(CREATE);
    public final static Access Read = new Access(READ);
    public final static Access Update = new Access(UPDATE);
    public final static Access Delete = new Access(DELETE);
    public final static Access Query = new Access(QUERY);
    public final static Access Crudq = new Access(CRUDQ_ALL);

    public final static int EXTENDED_NONE = 0x00;

    private final int general;
    private final int extended;

    public Access(int general) {
        this(general, EXTENDED_NONE);
    }

    public Access(int general, int extended) {
        this.general = (general & CRUDQ_ALL);
        this.extended = extended;
    }

    public Access(Access that) {
        this(that.general, that.extended);
    }

    public static Access makeGeneralAccess(int... accesses) {
        int accAll = NONE;
        for (int acc : accesses) {
            accAll |= acc;
        }
        return new Access(accAll);
    }

    public static Access makeExtendedAccess(int... accesses) {
        int accAll = EXTENDED_NONE;
        for (int acc : accesses) {
            accAll |= acc;
        }
        return new Access(NONE, accAll);
    }

    /**
     * if value == true, return original + mask
     * if value == false, return original - mask
     */
    public static int bitSet(int original, int mask, boolean value) {
        return value ? original | mask : original & (~mask);
    }

    /**
     * check if any bit in the mask contained
     */
    public static boolean bitCoversAny(int value, int mask) {
        return (value & mask) != NONE;
    }

    /**
     * check if all bit in the mask contained
     */
    public static boolean bitCoversAll(int value, int mask) {
        return (value & mask) == mask;
    }

    public Access clone() {
        return new Access(this.general, this.extended);
    }

    public int getGeneral() {
        return general;
    }

    public int getExtended() {
        return extended;
    }

    public int getExtended(int mask) {
        return extended & mask;
    }

    /**
     * returns a + b
     */
    public Access or(Access access) {
        return new Access(
            this.general | access.general,
            this.extended | access.extended);
    }

    /**
     * returns a + b
     */
    public Access merge(Access access) {
        return or(access);
    }

    /**
     * returns a & b
     */
    public Access and(Access access) {
        return new Access(
            this.general & access.general,
            this.extended & access.extended);
    }

    /**
     * returns a - b
     */
    public Access exclude(Access access){
        return and(access.not());
    }

    /**
     * return a xor b,
     */
    public Access xor(Access access) {
        return new Access(
            this.general ^ access.general,
            //this.general &= CRUDQ_ALL;
            this.extended ^ access.extended);
    }

    /**
     * return -a
     */
    public Access not() {
        return not(0xFFFFFFFF);
    }

    /**
     * calc -this using @param extendedMask as mask for extended part
     */
    public Access not(int extendedMask) {
        return new Access(
            ((~this.general) & CRUDQ_ALL),
            ((~this.extended) & extendedMask));
    }

    public Access generalPart() {
        return new Access(this.general);
    }

    public Access extendedPart() {
        return new Access(NONE, this.extended);
    }

    public boolean hasGeneral() {
        return (general & CRUDQ_ALL) != NONE;
    }

    public boolean hasGeneral(int acc) {
        return (general & acc) == acc;
    }

    public boolean hasAnyGeneral(int acc) {
        return (general & acc) != NONE;
    }

    public boolean hasExtended() {
        return extended != EXTENDED_NONE;
    }

    public boolean hasExtended(int acc) {
        return (extended & acc) == acc;
    }

    public boolean hasAnyExtended(int acc) {
        return (extended & acc) != EXTENDED_NONE;
    }

    /**
     * Check if the access fully contained
     * @param acc
     * @return
     */
    public boolean hasAccess(Access acc) {
        boolean general = ((this.general & acc.general) == acc.general);
        boolean extend = ((this.extended & acc.extended) == acc.extended);
        return (extend && general);
    }

    /**
     * Check if any of the access contained
     * @param acc
     * @return
     */
    public boolean hasAnyAccess(Access acc) {
        return ((this.general & acc.general) != NONE) ||
            ((this.extended & acc.extended) != EXTENDED_NONE);
    }

    /**
     * Set the general part by mask, using + or -
     */
    public Access generalSet(int mask, boolean value) {
        int general = bitSet(this.general, mask, value);
        return new Access(general, this.extended);
    }

    /**
     * Set the extended part by mask, using + or -
     */
    public Access extendedSet(int mask, boolean value) {
        int extended = bitSet(this.extended, mask, value);
        return new Access(this.general, extended);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Access that = (Access) o;

        if (general != that.general) return false;
        return extended == that.extended;

    }

    @Override
    public int hashCode() {
        int result = general;
        result = 31 * result + extended;
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[Access: ");
        if (hasGeneral(CREATE)) sb.append("C");
        if (hasGeneral(READ)) sb.append("R");
        if (hasGeneral(UPDATE)) sb.append("U");
        if (hasGeneral(DELETE)) sb.append("D");
        if (hasGeneral(QUERY)) sb.append("Q");

        if (hasExtended()) {
            sb.append(" ex:" + this.extended);
        }
        return sb.append("]").toString();
    }
}
