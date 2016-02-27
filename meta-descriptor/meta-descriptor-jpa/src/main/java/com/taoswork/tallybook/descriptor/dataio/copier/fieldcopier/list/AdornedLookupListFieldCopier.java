package com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier.list;

import com.taoswork.tallybook.descriptor.dataio.copier.CopierContext;
import com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier.IFieldCopierSolution;
import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.list.AdornedLookupListFieldMeta;

/**
 * Created by Gao Yuan on 2016/2/23.
 */
public class AdornedLookupListFieldCopier extends BaseListFieldCopier<AdornedLookupListFieldMeta> {
    public AdornedLookupListFieldCopier(IFieldCopierSolution solution) {
        super(solution);
    }

    @Override
    public Class<? extends AdornedLookupListFieldMeta> targetMeta() {
        return AdornedLookupListFieldMeta.class;
    }

}
