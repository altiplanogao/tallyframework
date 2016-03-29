package com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier;

import com.taoswork.tallybook.datadomain.base.entity.valuecopier.EntityCopierPool;
import com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier.basic.RExternalForeignEntityFieldCopier;
import com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier.basic.RForeignEntityFieldCopier;
import com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier.list.*;
import com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier.map.RAdornedLookupMapFieldCopier;
import com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier.map.RBasicMapFieldCopier;
import com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier.map.REntityMapFieldCopier;
import com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier.map.RLookupMapFieldCopier;

/**
 * Created by Gao Yuan on 2016/2/23.
 */
public class RFieldCopierSolution extends BaseFieldCopierSolution {

    public RFieldCopierSolution(EntityCopierPool entityCopierPool) {
        super(entityCopierPool);
    }

    @Override
    protected void init() {
        super.init();

        addFieldCopier(new RForeignEntityFieldCopier(this));
        addFieldCopier(new RExternalForeignEntityFieldCopier(this));

        addFieldCopier(new RPrimitiveListFieldCopier(this));
        addFieldCopier(new RBasicListFieldCopier(this));
        addFieldCopier(new REntityListFieldCopier(this));
        addFieldCopier(new RLookupListFieldCopier(this));
        addFieldCopier(new RAdornedLookupListFieldCopier(this));

        addFieldCopier(new RBasicMapFieldCopier(this));
        addFieldCopier(new REntityMapFieldCopier(this));
        addFieldCopier(new RLookupMapFieldCopier(this));
        addFieldCopier(new RAdornedLookupMapFieldCopier(this));
    }

    @Override
    protected <T> T makeSameTypeInstance(T source) throws IllegalAccessException, InstantiationException {
        T target = (T) source.getClass().newInstance();
        return target;
    }


}
