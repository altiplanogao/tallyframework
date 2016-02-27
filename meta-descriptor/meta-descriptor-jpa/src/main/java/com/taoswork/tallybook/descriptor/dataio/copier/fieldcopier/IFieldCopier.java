package com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier;

import com.taoswork.tallybook.descriptor.dataio.copier.CopierContext;
import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
import com.taoswork.tallybook.descriptor.metadata.IFieldMeta;

/**
 * Created by Gao Yuan on 2016/2/23.
 */
public interface IFieldCopier {
    Class<? extends IFieldMeta> targetMeta();

    boolean copy(final IClassMeta topMeta, final IFieldMeta fieldMeta,
                 final Object source, final Object target,
                 final int currentLevel, final int levelLimit,
                 final CopierContext copierContext)
            throws IllegalAccessException, InstantiationException;
}
