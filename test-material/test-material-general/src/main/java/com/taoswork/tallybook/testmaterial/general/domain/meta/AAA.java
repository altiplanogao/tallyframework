package com.taoswork.tallybook.testmaterial.general.domain.meta;

import com.taoswork.tallybook.datadomain.base.entity.PersistEntity;
import com.taoswork.tallybook.datadomain.base.entity.PersistField;
import com.taoswork.tallybook.testmaterial.general.domain.meta.validator.AAAValueValidator;
import com.taoswork.tallybook.testmaterial.general.domain.meta.valuecopier.AAACopier;
import com.taoswork.tallybook.testmaterial.general.domain.meta.valuegate.AAAGate;

/**
 * Created by Gao Yuan on 2015/6/26.
 */
@PersistEntity(
        validators = {AAAValueValidator.class},
        valueGates = {AAAGate.class},
        copier = AAACopier.class
)
public class AAA extends AA {
    public String aaa;

    public String getAaa() {
        return aaa;
    }

    public void setAaa(String aaa) {
        this.aaa = aaa;
    }
}
