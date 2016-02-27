package com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier.list;

import com.taoswork.tallybook.descriptor.dataio.copier.CopierContext;
import com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier.IFieldCopierSolution;
import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.list.PrimitiveListFieldMeta;

/**
 * Created by Gao Yuan on 2016/2/23.
 */
public class PrimitiveListFieldCopier extends BaseListFieldCopier<PrimitiveListFieldMeta> {
    public PrimitiveListFieldCopier(IFieldCopierSolution solution) {
        super(solution);
    }

    @Override
    public Class<? extends PrimitiveListFieldMeta> targetMeta() {
        return PrimitiveListFieldMeta.class;
    }

    @Override
    protected Object makeListEntryCopy(IClassMeta topMeta, PrimitiveListFieldMeta fieldMeta, Object element, int currentLevel, int levelLimit) {
        return element;
    }
}
