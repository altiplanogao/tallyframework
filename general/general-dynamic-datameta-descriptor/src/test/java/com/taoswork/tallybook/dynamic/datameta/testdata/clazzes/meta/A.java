package com.taoswork.tallybook.dynamic.datameta.testdata.clazzes.meta;

import com.taoswork.tallybook.dynamic.datameta.testdata.clazzes.meta.validator.AValueValidator;
import com.taoswork.tallybook.dynamic.datameta.testdata.clazzes.meta.valuecopier.AValueCopier;
import com.taoswork.tallybook.dynamic.datameta.testdata.clazzes.meta.valuegate.AValueGate;
import com.taoswork.tallybook.general.datadomain.support.entity.PersistEntity;

/**
 * Created by Gao Yuan on 2015/6/26.
 */

@PersistEntity(
    validators = {AValueValidator.class},
    valueGates = {AValueGate.class},
    copier = AValueCopier.class
)
public class A {
    public String a;

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }
}
