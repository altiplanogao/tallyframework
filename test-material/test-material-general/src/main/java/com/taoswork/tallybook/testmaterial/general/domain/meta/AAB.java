package com.taoswork.tallybook.testmaterial.general.domain.meta;

import com.taoswork.tallybook.datadomain.base.entity.PersistEntity;
import com.taoswork.tallybook.datadomain.base.entity.PersistField;

/**
 * Created by Gao Yuan on 2015/6/26.
 */
@PersistEntity(
        fieldOverrides = {@PersistEntity.FieldOverride(fieldName = "aa2", define = @PersistField(length = 2))})
public class AAB extends AA {
    public String aab;

    public String getAab() {
        return aab;
    }

    public void setAab(String aaa) {
        this.aab = aaa;
    }
}
