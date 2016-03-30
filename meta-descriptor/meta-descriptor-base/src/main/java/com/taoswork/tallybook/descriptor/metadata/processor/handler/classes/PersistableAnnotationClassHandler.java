package com.taoswork.tallybook.descriptor.metadata.processor.handler.classes;

import com.taoswork.tallybook.datadomain.base.entity.PersistEntity;
import com.taoswork.tallybook.datadomain.base.entity.PersistField;
import com.taoswork.tallybook.datadomain.base.entity.Persistable;
import com.taoswork.tallybook.datadomain.base.entity.validation.IEntityValidator;
import com.taoswork.tallybook.datadomain.base.entity.valuecopier.IEntityCopier;
import com.taoswork.tallybook.datadomain.base.entity.valuegate.IEntityGate;
import com.taoswork.tallybook.descriptor.metadata.classmetadata.MutableClassMeta;
import com.taoswork.tallybook.descriptor.metadata.processor.ProcessResult;
import com.taoswork.tallybook.descriptor.metadata.processor.handler.basic.IClassHandler;
import com.taoswork.tallybook.general.solution.reflect.ClassUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public class PersistableAnnotationClassHandler implements IClassHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersistableAnnotationClassHandler.class);

    @Override
    public ProcessResult process(Class<?> clzz, MutableClassMeta mutableClassMetadata) {
        Set<Class> clzes = new HashSet<Class>();
        clzes.add(clzz);
        ClassUtility.getAllSupers(Persistable.class, clzz, true, true, clzes);
        boolean handled = false;
        for (Class<?> oneClz : clzes) {
            PersistEntity persistEntity = oneClz.getAnnotation(PersistEntity.class);
            if (persistEntity != null) {
                for (Class<? extends IEntityValidator> validator : persistEntity.validators()) {
                    mutableClassMetadata.addValidator(validator);
                }
                for (Class<? extends IEntityGate> valueGate : persistEntity.valueGates()) {
                    mutableClassMetadata.addValueGate(valueGate);
                }
                Class<? extends IEntityCopier> copier = persistEntity.copier();
                mutableClassMetadata.setValueCopier(copier);
                handleAnnotationPersistEntityFieldOverrides(persistEntity, mutableClassMetadata);
                handled = true;
            }
        }
        if (handled)
            return ProcessResult.HANDLED;
        return ProcessResult.INAPPLICABLE;
    }

    private void handleAnnotationPersistEntityFieldOverrides(PersistEntity persistEntity, MutableClassMeta mutableClassMetadata) {
        PersistEntity.FieldOverride fieldOverrides[] = persistEntity.fieldOverrides();
        Map<String, PersistField> foMap = mutableClassMetadata.getPersistFieldOverrides();
        for (PersistEntity.FieldOverride fo : fieldOverrides){
            String fieldName = fo.fieldName();
            PersistField pf = fo.define();
            foMap.put(fieldName, pf);
        }
    }


}