package com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.classes;

import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ProcessResult;
import com.taoswork.tallybook.general.datadomain.support.entity.PersistFriendly;
import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;
import com.taoswork.tallybook.general.datadomain.support.entity.validation.IEntityValidator;
import com.taoswork.tallybook.general.solution.reflect.ClassUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public class PersistableAnnotationClassHandler implements IClassHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersistableAnnotationClassHandler.class);

    @Override
    public ProcessResult process(Class<?> clzz, ClassMetadata classMetadata) {
        Set<Class> clzes = new HashSet<Class>();
        clzes.add(clzz);
        ClassUtility.getAllSupers(Persistable.class, clzz, true, true, clzes);
        boolean handled = false;
        for(Class<?> oneClz : clzes){
            PersistFriendly presentationClass = oneClz.getAnnotation(PersistFriendly.class);
            if (presentationClass != null) {
                for(Class<? extends IEntityValidator> validator : presentationClass.validators()){
                    classMetadata.addValidator(validator);
                }
                handled = true;
            }
        }
        if(handled)
            return ProcessResult.HANDLED;
        return ProcessResult.INAPPLICABLE;
    }


}