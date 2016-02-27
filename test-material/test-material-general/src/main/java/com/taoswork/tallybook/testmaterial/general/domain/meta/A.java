package com.taoswork.tallybook.testmaterial.general.domain.meta;

import com.taoswork.tallybook.datadomain.base.entity.PersistEntity;
import com.taoswork.tallybook.testmaterial.general.domain.meta.validator.AValueValidator;
import com.taoswork.tallybook.testmaterial.general.domain.meta.valuecopier.ACopier;
import com.taoswork.tallybook.testmaterial.general.domain.meta.valuegate.AGate;

/**
 * Created by Gao Yuan on 2015/6/26.
 */

@PersistEntity(
        validators = {AValueValidator.class},
        valueGates = {AGate.class},
        copier = ACopier.class
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
