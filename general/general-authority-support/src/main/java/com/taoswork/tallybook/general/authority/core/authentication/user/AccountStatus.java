package com.taoswork.tallybook.general.authority.core.authentication.user;

import com.taoswork.tallybook.general.datadomain.support.entity.PersistField;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationField;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;
import com.taoswork.tallybook.general.datadomain.support.presentation.typed.BooleanMode;
import com.taoswork.tallybook.general.datadomain.support.presentation.typed.DateMode;
import com.taoswork.tallybook.general.datadomain.support.presentation.typed.PresentationBoolean;
import com.taoswork.tallybook.general.datadomain.support.presentation.typed.PresentationDate;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Gao Yuan on 2015/4/16.
 */
@Embeddable
@Access(AccessType.FIELD)
public class AccountStatus {

    @Column(name = "ACCOUNT_ENABLED")
    @PersistField(fieldType = FieldType.BOOLEAN)
    @PresentationField(order = 1)
    @PresentationBoolean(mode = BooleanMode.YesNo)
    public Boolean enabled = true;

    @Column(name = "ACCOUNT_LOCKED")
    @PersistField(fieldType = FieldType.BOOLEAN)
    @PresentationField(order = 2)
    @PresentationBoolean(mode = BooleanMode.YesNo)
    public Boolean locked = false;

    @Column(name = "ACCOUNT_CREATE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    @PersistField(fieldType = FieldType.DATE, editable = false)
    @PresentationField(order = 3)
    @PresentationDate(mode = DateMode.DateTime)
    public Date createDate = new Date();

    @Column(name = "ACCOUNT_LAST_LOGIN_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    @PersistField(fieldType = FieldType.DATE, editable = false)
    @PresentationField(order = 4)
    public Date lastLoginDate;

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean isLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public boolean checkNowIfExpired(){
        if(lastLoginDate == null){
            return true;
        }
        long lastTime = lastLoginDate.getTime();
        long now = new Date().getTime();
        long _2years = 2 * 365 * 24 * 3600 * 1000L;
        return (now < lastTime + _2years);
    }
}
