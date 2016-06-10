package com.taoswork.tallybook.business.datadomain.tallyuser;

import com.taoswork.tallybook.datadomain.base.entity.PersistField;
import com.taoswork.tallybook.datadomain.base.presentation.PresentationField;
import com.taoswork.tallybook.datadomain.base.presentation.FieldType;
import com.taoswork.tallybook.datadomain.base.presentation.typed.BooleanMode;
import com.taoswork.tallybook.datadomain.base.presentation.typed.DateMode;
import com.taoswork.tallybook.datadomain.base.presentation.typed.PresentationBoolean;
import com.taoswork.tallybook.datadomain.base.presentation.typed.PresentationDate;

import java.util.Date;

/**
 * Created by Gao Yuan on 2015/4/16.
 */
public class AccountStatus {

    @PersistField(fieldType = FieldType.BOOLEAN)
    @PresentationField(order = 1)
    @PresentationBoolean(mode = BooleanMode.YesNo)
    public Boolean enabled = true;

    @PersistField(fieldType = FieldType.BOOLEAN)
    @PresentationField(order = 2)
    @PresentationBoolean(mode = BooleanMode.YesNo)
    public Boolean locked = false;

    @PersistField(fieldType = FieldType.DATE, editable = false)
    @PresentationField(order = 3)
    @PresentationDate(mode = DateMode.DateTime)
    public Date createDate = new Date();

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

    public boolean checkNowIfExpired() {
        if (lastLoginDate == null) {
            return true;
        }
        long lastTime = lastLoginDate.getTime();
        long now = new Date().getTime();
        long _2years = 2 * 365 * 24 * 3600 * 1000L;
        return (now < lastTime + _2years);
    }
}
