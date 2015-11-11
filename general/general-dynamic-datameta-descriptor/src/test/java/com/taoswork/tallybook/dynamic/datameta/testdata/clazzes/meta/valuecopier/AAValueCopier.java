package com.taoswork.tallybook.dynamic.datameta.testdata.clazzes.meta.valuecopier;

import com.taoswork.tallybook.general.datadomain.support.entity.valuecopier.IEntityValueCopier;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2015/11/11.
 */
public class AAValueCopier implements IEntityValueCopier {
    @Override
    public boolean allHandled() {
        return false;
    }

    @Override
    public Collection<String> handledFields() {
        return null;
    }
    @Override
    public void copy(Object src, Object target) {

    }
}
