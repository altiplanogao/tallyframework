package com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier.map;

import com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier.IFieldCopierSolution;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.map.AdornedLookupMapFieldMeta;

/**
 * Created by Gao Yuan on 2016/2/23.
 */
public class RAdornedLookupMapFieldCopier extends BaseRMapFieldCopier<AdornedLookupMapFieldMeta> {
    public RAdornedLookupMapFieldCopier(IFieldCopierSolution solution) {
        super(solution);
    }

    @Override
    public Class<? extends AdornedLookupMapFieldMeta> targetMeta() {
        return AdornedLookupMapFieldMeta.class;
    }
}
