package com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier.list;

import com.taoswork.tallybook.descriptor.dataio.copier.CopierContext;
import com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier.IFieldCopierSolution;
import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.list.BasicListFieldMeta;

/**
 * Created by Gao Yuan on 2016/2/23.
 */
public class BasicListFieldCopier extends BaseListFieldCopier<BasicListFieldMeta> {
    public BasicListFieldCopier(IFieldCopierSolution solution) {
        super(solution);
    }

    @Override
    public Class<? extends BasicListFieldMeta> targetMeta() {
        return BasicListFieldMeta.class;
    }
}
