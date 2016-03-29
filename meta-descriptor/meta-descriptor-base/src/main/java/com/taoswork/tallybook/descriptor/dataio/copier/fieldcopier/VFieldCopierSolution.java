package com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier;

import com.taoswork.tallybook.datadomain.base.entity.valuecopier.EntityCopierPool;
import com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier.basic.VExternalForeignEntityFieldCopier;
import com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier.basic.VForeignEntityFieldCopier;
import com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier.list.*;
import com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier.map.VAdornedLookupMapFieldCopier;
import com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier.map.VBasicMapFieldCopier;
import com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier.map.VEntityMapFieldCopier;
import com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier.map.VLookupMapFieldCopier;
import com.taoswork.tallybook.descriptor.metadata.IFieldMeta;

/**
 * Created by Gao Yuan on 2016/3/29.
 */
public class VFieldCopierSolution extends BaseFieldCopierSolution {
    public VFieldCopierSolution(EntityCopierPool entityCopierPool) {
        super(entityCopierPool);
    }

    @Override
    protected void init() {
        super.init();

        addFieldCopier(new VForeignEntityFieldCopier(this));
        addFieldCopier(new VExternalForeignEntityFieldCopier(this));

        addFieldCopier(new VPrimitiveListFieldCopier(this));
        addFieldCopier(new VBasicListFieldCopier(this));
        addFieldCopier(new VEntityListFieldCopier(this));
        addFieldCopier(new VLookupListFieldCopier(this));
        addFieldCopier(new VAdornedLookupListFieldCopier(this));

        addFieldCopier(new VBasicMapFieldCopier(this));
        addFieldCopier(new VEntityMapFieldCopier(this));
        addFieldCopier(new VLookupMapFieldCopier(this));
        addFieldCopier(new VAdornedLookupMapFieldCopier(this));
    }

    @Override
    protected <T> void doPrimitiveFieldCopy(IFieldMeta fieldMeta, T source, T target) throws IllegalAccessException {
        //do nothing since source is target
    }

    @Override
    protected <T> T makeSameTypeInstance(T source) throws IllegalAccessException, InstantiationException {
        return source;
    }
}
