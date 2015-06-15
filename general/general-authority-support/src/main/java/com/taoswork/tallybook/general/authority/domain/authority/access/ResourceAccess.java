package com.taoswork.tallybook.general.authority.domain.authority.access;

import javax.persistence.Embeddable;

/**
 * Created by Gao Yuan on 2015/6/4.
 */
@Embeddable
public class ResourceAccess {
    public final static int NONE_EXTENDED = 0x00;

    public final static int CRUD_FULL_MASK =
                    AccessType.NONE |
                    AccessType.CREATE |
                    AccessType.READ |
                    AccessType.UPDATE |
                    AccessType.DELETE |
                    AccessType.QUERY;

    private boolean canCreate = false;
    private boolean canRead = false;
    private boolean canUpdate = false;
    private boolean canDelete = false;
    private boolean canQuery = false;

    private transient int crude = 0x00; // CRUD + Extended

    private int extended = NONE_EXTENDED;

    public boolean canCreate() {
        return isCanCreate();
    }

    public boolean canRead() {
        return isCanRead();
    }

    public boolean canUpdate() {
        return isCanUpdate();
    }

    public boolean canDelete() {
        return isCanDelete();
    }

    public boolean canQuery() {
        return isCanQuery();
    }

    public boolean isCanCreate() {
        return canCreate;
    }

    public boolean isCanRead() {
        return canRead;
    }

    public boolean isCanUpdate() {
        return canUpdate;
    }

    public boolean isCanDelete() {
        return canDelete;
    }

    public boolean isCanQuery() {
        return canQuery;
    }

    public ResourceAccess setCanCreate(boolean canCreate) {
        this.canCreate = canCreate;
        setCrudMask(AccessType.CREATE, canCreate);
        return this;
    }

    public ResourceAccess setCanRead(boolean canRead) {
        this.canRead = canRead;
        setCrudMask(AccessType.READ, canRead);
        return this;
    }

    public ResourceAccess setCanUpdate(boolean update) {
        this.canUpdate = update;
        setCrudMask(AccessType.UPDATE, update);
        return this;
    }

    public ResourceAccess setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
        setCrudMask(AccessType.DELETE, canDelete);
        return this;
    }

    public ResourceAccess setCanQuery(boolean canQuery) {
        this.canQuery = canQuery;
        setCrudMask(AccessType.QUERY, canQuery);
        return this;
    }

    public ResourceAccess setCrudAll(boolean canAccess){
        setCanCreate(canAccess);
        setCanRead(canAccess);
        setCanUpdate(canAccess);
        setCanDelete(canAccess);
        setCanQuery(canAccess);
        return this;
    }

    public ResourceAccess setCrudAccess(AccessType accessType) {
        return setCrudAccess(accessType, true);
    }

    public ResourceAccess setCrudAccess(AccessType accessType, boolean value) {
        switch (accessType.getAccess()) {
            case AccessType.CREATE:
                setCanCreate(value);
                break;
            case AccessType.READ:
                setCanRead(value);
                break;
            case AccessType.UPDATE:
                setCanUpdate(value);
                break;
            case AccessType.DELETE:
                setCanDelete(value);
                break;
            case AccessType.QUERY:
                setCanQuery(value);
                break;
            default:
                break;
        }
        return this;
    }

//    public int getCrudAccess(){
//        int crudMask = (crude & CRUD_FULL_MASK);
//        return crudMask;
//    }

    private void setCrudMask(int mask, boolean value){
        crude = value ? crude | mask : crude & (~mask);
    }

    public int getExtended() {
        return extended;
    }

    public ResourceAccess setExtended(int extended) {
        this.extended = extended;
        return this;
    }

    public ResourceAccess setExtendedAccess(int accessMask) {
        return setExtendedAccess(accessMask, true);
    }

    public ResourceAccess setExtendedAccess(int accessMask, boolean value) {
        if (value) {
            extended |= accessMask;
        } else {
            extended &= (~accessMask);
        }
        setCrudMask(AccessType.EXTENDED, (extended != NONE_EXTENDED));
        return this;
    }

    public boolean isCrudAccessCoversAll(int accessMask) {
        return (crude & accessMask) == accessMask;
    }

    public boolean isCrudAccessCoversAny(int accessMask) {
        return (crude & accessMask) != 0;
    }

    public boolean isExtendedAccessCoversAll(int accessMask) {
        return (extended & accessMask) == accessMask;
    }

    public boolean isExtendedAccessCoversAny(int accessMask) {
        return (extended & accessMask) != 0;
    }

    public ResourceAccess merge(ResourceAccess access){
        canCreate |= access.canCreate;
        canRead |= access.canRead;
        canUpdate |= access.canUpdate;
        canDelete |= access.canDelete;
        crude |= access.crude;
        extended |= access.extended;
        return this;
    }

    public boolean contains(AccessType accessType){
        if(accessType.isCrud()){
            return (crude & accessType.getAccess()) != 0x00;
        }
        return (extended & accessType.getExtended()) != 0x00;
    }
}
