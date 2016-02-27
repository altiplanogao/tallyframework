package com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier;

import com.taoswork.tallybook.descriptor.dataio.copier.CopierContext;
import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.embedded.EmbeddedFieldMeta;

import java.lang.reflect.Field;

/**
 * Created by Gao Yuan on 2016/2/23.
 */
public class EmbeddedFieldCopier extends BaseFieldCopier<EmbeddedFieldMeta> {
    public EmbeddedFieldCopier(IFieldCopierSolution solution) {
        super(solution);
    }

    @Override
    public Class<? extends EmbeddedFieldMeta> targetMeta() {
        return EmbeddedFieldMeta.class;
    }

    @Override
    protected boolean doCopy(IClassMeta topMeta, EmbeddedFieldMeta fieldMeta,
                             Object source, Object target,
                             int currentLevel, int levelLimit,
                             CopierContext copierContext) throws IllegalAccessException, InstantiationException {
        Field field = fieldMeta.getField();
        IClassMeta embeddedClassMeta = fieldMeta.getClassMetadata();
        Object fo = field.get(source);
        Object fn = this.makeCopyForEmbeddable(topMeta, fo, embeddedClassMeta, currentLevel, levelLimit, copierContext);
        field.set(target, fn);
        return true;
    }

    private <T> T makeCopyForEmbeddable(final IClassMeta topMeta, T embeddable, IClassMeta embedCm,
                                        final int currentLevel, final int levelLimit,
                                        CopierContext copierContext)
            throws IllegalAccessException, InstantiationException {
        T emptyCopy = solution.walkFieldsAndCopy(topMeta, embedCm, embeddable, currentLevel, levelLimit, copierContext);
        return emptyCopy;
    }

}
