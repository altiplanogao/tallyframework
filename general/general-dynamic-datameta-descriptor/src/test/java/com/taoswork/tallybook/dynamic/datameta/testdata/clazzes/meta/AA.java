package com.taoswork.tallybook.dynamic.datameta.testdata.clazzes.meta;

import com.taoswork.tallybook.dynamic.datameta.testdata.clazzes.meta.validator.AAValueValidator;
import com.taoswork.tallybook.dynamic.datameta.testdata.clazzes.meta.valuecopier.AAValueCopier;
import com.taoswork.tallybook.dynamic.datameta.testdata.clazzes.meta.valuegate.AAValueGate;
import com.taoswork.tallybook.general.datadomain.support.entity.PersistEntity;

/**
 * Created by Gao Yuan on 2015/6/26.
 */
@PersistEntity(
    validators = {AAValueValidator.class},
    valueGates = {AAValueGate.class},
    copier = AAValueCopier.class
)
public class AA extends A {
    public String aa;

    public String getAa() {
        return aa;
    }

    public void setAa(String aa) {
        this.aa = aa;
    }
}
