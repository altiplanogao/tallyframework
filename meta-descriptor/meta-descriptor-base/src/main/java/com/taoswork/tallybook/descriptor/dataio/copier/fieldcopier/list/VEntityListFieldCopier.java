package com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier.list;

import com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier.IFieldCopierSolution;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.list.EntityListFieldMeta;

/**
 * Created by Gao Yuan on 2016/2/23.
 */
public class VEntityListFieldCopier extends BaseVListFieldCopier<EntityListFieldMeta> {
    public VEntityListFieldCopier(IFieldCopierSolution solution) {
        super(solution);
    }

    @Override
    public Class<? extends EntityListFieldMeta> targetMeta() {
        return EntityListFieldMeta.class;
    }
}
