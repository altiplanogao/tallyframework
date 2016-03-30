package com.taoswork.tallybook.testmaterial.general.domain.meta;

import com.taoswork.tallybook.datadomain.base.entity.PersistEntity;
import com.taoswork.tallybook.datadomain.base.entity.PersistField;
import com.taoswork.tallybook.testmaterial.general.domain.meta.validator.AAValueValidator;
import com.taoswork.tallybook.testmaterial.general.domain.meta.valuecopier.AACopier;
import com.taoswork.tallybook.testmaterial.general.domain.meta.valuegate.AAGate;

/**
 * Created by Gao Yuan on 2015/6/26.
 */
@PersistEntity(
        validators = {AAValueValidator.class},
        valueGates = {AAGate.class},
        copier = AACopier.class
)
public class AA extends A {
    public String aa;

    @PersistField(length = 1)
    public String aa2;

    public String getAa() {
        return aa;
    }

    public void setAa(String aa) {
        this.aa = aa;
    }

    public String getAa2() {
        return aa2;
    }

    public void setAa2(String aa2) {
        this.aa2 = aa2;
    }
}
