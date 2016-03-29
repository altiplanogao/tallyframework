package com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier.map;

import com.taoswork.tallybook.descriptor.dataio.copier.CopierContext;
import com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier.BaseFieldCopier;
import com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier.IFieldCopierSolution;
import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
import com.taoswork.tallybook.descriptor.metadata.IFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.map.MapFieldMeta;
import com.taoswork.tallybook.general.solution.exception.UnImplementedException;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by Gao Yuan on 2016/2/23.
 */
abstract class BaseRMapFieldCopier<Lfm extends MapFieldMeta> extends BaseFieldCopier<Lfm> {
    public BaseRMapFieldCopier(IFieldCopierSolution solution) {
        super(solution);
    }

    @Override
    public boolean copy(IClassMeta topMeta, IFieldMeta fieldMeta, Object source, Object target, int currentLevel, int levelLimit, CopierContext copierContext) throws IllegalAccessException, InstantiationException {
        return super.copy(topMeta, fieldMeta, source, target, currentLevel, levelLimit, copierContext);
    }

    @Override
    protected final boolean doCopy(IClassMeta topMeta, Lfm fieldMeta, Object source, Object target, int currentLevel, int levelLimit, CopierContext copierContext) throws IllegalAccessException, InstantiationException {
        Field field = fieldMeta.getField();
        Map<?, ?> sourceMap = (Map) field.get(source);
        if (null == sourceMap) {
            field.set(target, null);
            return true;
        }
        final int nextLevel = currentLevel + 1;
        if (nextLevel >= levelLimit) {
            field.set(target, null);
            return true;
        }
        Map targetMap = (Map) fieldMeta.getMapImplementClass().newInstance();
        for (Map.Entry entry : sourceMap.entrySet()) {
            MapEntry entryCpy = makeMapEntryCopy(topMeta, fieldMeta, new MapEntry(entry), nextLevel, levelLimit);
            if (entryCpy != null) {
                targetMap.put(entryCpy.key, entryCpy.val);
            }
        }
        field.set(target, targetMap);

        return true;
    }

    protected MapEntry makeMapEntryCopy(IClassMeta topMeta, Lfm fieldMeta, MapEntry element, int currentLevel, int levelLimit) {
        throw new UnImplementedException("makeMapEntryCopy" + this.getClass().getName());
    }
}
