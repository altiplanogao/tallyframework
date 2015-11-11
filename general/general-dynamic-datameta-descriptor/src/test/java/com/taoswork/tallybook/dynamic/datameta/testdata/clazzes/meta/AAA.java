package com.taoswork.tallybook.dynamic.datameta.testdata.clazzes.meta;

import com.taoswork.tallybook.dynamic.datameta.testdata.clazzes.meta.validator.AAAValueValidator;
import com.taoswork.tallybook.dynamic.datameta.testdata.clazzes.meta.valuecopier.AAAValueCopier;
import com.taoswork.tallybook.dynamic.datameta.testdata.clazzes.meta.valuegate.AAAValueGate;
import com.taoswork.tallybook.general.datadomain.support.entity.PersistEntity;

/**
 * Created by Gao Yuan on 2015/6/26.
 */
@PersistEntity(
    validators = {AAAValueValidator.class},
    valueGates = {AAAValueGate.class},
    copier = AAAValueCopier.class
)
public class AAA extends AA{
    public String aaa;

    public String getAaa() {
        return aaa;
    }

    public void setAaa(String aaa) {
        this.aaa = aaa;
    }
}
