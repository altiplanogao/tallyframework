package com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.classes;

import com.taoswork.tallybook.dynamic.datameta.metadata.classmetadata.MutableClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ProcessResult;
import com.taoswork.tallybook.general.datadomain.support.entity.PersistEntity;
import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;
import com.taoswork.tallybook.general.datadomain.support.entity.validation.IEntityValidator;
import com.taoswork.tallybook.general.datadomain.support.entity.valuegate.IEntityValueGate;
import com.taoswork.tallybook.general.solution.reflect.ClassUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public class PersistableAnnotationClassHandler implements IClassHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersistableAnnotationClassHandler.class);

    @Override
    public ProcessResult process(Class<?> clzz, MutableClassMetadata mutableClassMetadata) {
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
                for (Class<? extends IEntityValueGate> valueGate : persistEntity.valueGates()) {
                    mutableClassMetadata.addValueGate(valueGate);
                }
                handled = true;
            }
        }
        if (handled)
            return ProcessResult.HANDLED;
        return ProcessResult.INAPPLICABLE;
    }


}