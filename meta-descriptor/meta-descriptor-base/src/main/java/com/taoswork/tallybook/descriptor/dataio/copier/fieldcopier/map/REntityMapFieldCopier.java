package com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier.map;

import com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier.IFieldCopierSolution;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.map.EntityMapFieldMeta;

/**
 * Created by Gao Yuan on 2016/2/23.
 */
public class REntityMapFieldCopier extends BaseRMapFieldCopier<EntityMapFieldMeta> {
    public REntityMapFieldCopier(IFieldCopierSolution solution) {
        super(solution);
    }

    @Override
    public Class<? extends EntityMapFieldMeta> targetMeta() {
        return EntityMapFieldMeta.class;
    }


}
