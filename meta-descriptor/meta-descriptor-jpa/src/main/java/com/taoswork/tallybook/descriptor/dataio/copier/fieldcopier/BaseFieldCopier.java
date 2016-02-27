package com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier;

import com.taoswork.tallybook.descriptor.dataio.copier.CopierContext;
import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
import com.taoswork.tallybook.descriptor.metadata.IFieldMeta;

/**
 * Created by Gao Yuan on 2016/2/23.
 */
public abstract class BaseFieldCopier<Fm extends IFieldMeta> implements IFieldCopier {
    protected final IFieldCopierSolution solution;

    public BaseFieldCopier(IFieldCopierSolution solution) {
        this.solution = solution;
    }

    public abstract Class<? extends Fm> targetMeta();

    @Override
    public boolean copy(final IClassMeta topMeta, final IFieldMeta fieldMeta,
                        final Object source, final Object target,
                        final int currentLevel, final int levelLimit,
                        final CopierContext copierContext)
            throws IllegalAccessException, InstantiationException {
        return doCopy(topMeta, (Fm) fieldMeta,
                source, target,
                currentLevel, levelLimit, copierContext);
    }

    protected abstract boolean doCopy(final IClassMeta topMeta, final Fm fieldMeta,
                                      final Object source, final Object target,
                                      final int currentLevel, final int levelLimit,
                                      final CopierContext copierContext)
            throws IllegalAccessException, InstantiationException;
}
