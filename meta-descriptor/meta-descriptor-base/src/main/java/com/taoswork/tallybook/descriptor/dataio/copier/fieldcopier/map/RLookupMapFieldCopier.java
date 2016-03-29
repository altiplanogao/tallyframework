package com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier.map;

import com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier.IFieldCopierSolution;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.map.LookupMapFieldMeta;

/**
 * Created by Gao Yuan on 2016/2/23.
 */
public class RLookupMapFieldCopier extends BaseRMapFieldCopier<LookupMapFieldMeta> {
    public RLookupMapFieldCopier(IFieldCopierSolution solution) {
        super(solution);
    }

    @Override
    public Class<? extends LookupMapFieldMeta> targetMeta() {
        return LookupMapFieldMeta.class;
    }

}
