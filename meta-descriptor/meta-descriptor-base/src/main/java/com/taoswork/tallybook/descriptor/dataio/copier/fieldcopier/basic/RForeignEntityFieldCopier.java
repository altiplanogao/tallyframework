package com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier.basic;

import com.taoswork.tallybook.descriptor.dataio.copier.CopierContext;
import com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier.BaseFieldCopier;
import com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier.IFieldCopierSolution;
import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.basic.ForeignEntityFieldMeta;

import java.lang.reflect.Field;

/**
 * Created by Gao Yuan on 2016/2/23.
 */
public class RForeignEntityFieldCopier extends BaseFieldCopier<ForeignEntityFieldMeta> {
    public RForeignEntityFieldCopier(IFieldCopierSolution solution) {
        super(solution);
    }

    @Override
    public Class<? extends ForeignEntityFieldMeta> targetMeta() {
        return ForeignEntityFieldMeta.class;
    }

    @Override
    protected boolean doCopy(IClassMeta topMeta, ForeignEntityFieldMeta fieldMeta,
                             Object source, Object target,
                             int currentLevel, int levelLimit,
                             final CopierContext copierContext) throws IllegalAccessException, InstantiationException {
        Class entityType = fieldMeta.getTargetType();
        IClassMeta foreignClassMeta = topMeta.getReferencingClassMeta(entityType);
        Field field = fieldMeta.getField();
        Object fo = field.get(source);
        Object fn = solution.walkFieldsAndCopy(topMeta, foreignClassMeta, fo, currentLevel, levelLimit,
                copierContext);
        field.set(target, fn);
        return true;
    }
}
