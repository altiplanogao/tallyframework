package com.taoswork.tallybook.dynamic.datameta.testdata.clazzes.meta;

import com.taoswork.tallybook.dynamic.datameta.testdata.clazzes.meta.validator.AAAValueValidator;
import com.taoswork.tallybook.dynamic.datameta.testdata.clazzes.meta.valuecopier.AAAValueCopier;
import com.taoswork.tallybook.dynamic.datameta.testdata.clazzes.meta.valuegate.AAAValueGate;
import com.taoswork.tallybook.general.datadomain.support.entity.PersistEntity;

/**
 * Created by Gao Yuan on 2015/6/26.
 */
@PersistEntity
public class AAB extends AA{
    public String aab;

    public String getAab() {
        return aab;
    }

    public void setAab(String aaa) {
        this.aab = aaa;
    }
}
