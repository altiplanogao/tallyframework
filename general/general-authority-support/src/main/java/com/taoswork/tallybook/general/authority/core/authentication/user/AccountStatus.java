package com.taoswork.tallybook.general.authority.core.authentication.user;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Date;

/**
 * Created by Gao Yuan on 2015/4/16.
 */
@Embeddable
@Access(AccessType.FIELD)
public class AccountStatus {

    @Column(name = "ACCOUNT_ENABLED")
    public boolean enabled = true;

    @Column(name = "ACCOUNT_LOCKED")
    public boolean locked = false;

    @Column(name = "ACCOUNT_CREATE_DATE")
    public long createDate = new Date().getTime();

    @Column(name = "ACCOUNT_LAST_LOGIN_DATE")
    public long lastLoginDate = 0L;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public long getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(long lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public boolean isNonExpired(){
        if(lastLoginDate == 0L){
            return true;
        }
        long now = new Date().getTime();
        long _2years = 2 * 365 * 24 * 3600 * 1000L;
        return (now < lastLoginDate + _2years);
    }
}
