package com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier.list;

import com.taoswork.tallybook.descriptor.dataio.copier.CopierContext;
import com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier.BaseFieldCopier;
import com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier.IFieldCopierSolution;
import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
import com.taoswork.tallybook.descriptor.metadata.IFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.list.ListFieldMeta;
import com.taoswork.tallybook.general.solution.exception.UnImplementedException;

import java.lang.reflect.Field;
import java.util.Collection;

/**
 * Created by Gao Yuan on 2016/2/23.
 */
abstract class BaseVListFieldCopier<Lfm extends ListFieldMeta> extends BaseFieldCopier<Lfm> {
    public BaseVListFieldCopier(IFieldCopierSolution solution) {
        super(solution);
    }

    @Override
    public boolean copy(IClassMeta topMeta, IFieldMeta fieldMeta, Object source, Object target, int currentLevel, int levelLimit, CopierContext copierContext) throws IllegalAccessException, InstantiationException {
//        Field field = fieldMeta.getField();
//        Map fo = (Map) field.get(source);
//        Map fn = null;
//        boolean setAndReturn = false;
//        if (source == null)
//            fn = null;
//        final int nextLevel = currentLevel + 1;
//        if (nextLevel >= levelLimit)
//            fn = null;
//        field.set(target, fn);

        return super.copy(topMeta, fieldMeta, source, target, currentLevel, levelLimit, copierContext);
    }

    @Override
    protected boolean doCopy(IClassMeta topMeta, Lfm fieldMeta, Object source, Object target, int currentLevel, int levelLimit, CopierContext copierContext) throws IllegalAccessException, InstantiationException {
        Field field = fieldMeta.getField();
        Collection<?> sourceList = (Collection) field.get(source);
        if (null == sourceList) {
            return true;
        }
        final int nextLevel = currentLevel + 1;
        if (nextLevel >= levelLimit) {
            return true;
        }
        for (Object entry : sourceList) {
            Object entryCpy = makeListEntryCopy(topMeta, fieldMeta, entry, nextLevel, levelLimit);
        }

        return true;
    }

    protected Object makeListEntryCopy(IClassMeta topMeta, Lfm fieldMeta, Object element, int currentLevel, int levelLimit) {
        throw new UnImplementedException("makeListEntryCopy" + this.getClass().getName());
    }

}
