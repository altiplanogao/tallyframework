package com.taoswork.tallybook.testmaterial.general.domain.meta;

import com.taoswork.tallybook.datadomain.base.entity.PersistEntity;

/**
 * Created by Gao Yuan on 2015/6/26.
 */
@PersistEntity
public class AAB extends AA {
    public String aab;

    public String getAab() {
        return aab;
    }

    public void setAab(String aaa) {
        this.aab = aaa;
    }
}
