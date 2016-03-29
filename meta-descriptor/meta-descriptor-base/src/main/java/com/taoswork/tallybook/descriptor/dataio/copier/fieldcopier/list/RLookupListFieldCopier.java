package com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier.list;

import com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier.IFieldCopierSolution;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.list.LookupListFieldMeta;

/**
 * Created by Gao Yuan on 2016/2/23.
 */
public class RLookupListFieldCopier extends BaseRListFieldCopier<LookupListFieldMeta> {
    public RLookupListFieldCopier(IFieldCopierSolution solution) {
        super(solution);
    }

    @Override
    public Class<? extends LookupListFieldMeta> targetMeta() {
        return LookupListFieldMeta.class;
    }

}
