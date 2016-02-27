package com.taoswork.tallybook.descriptor.metadata.processor.handler.classes;

import com.taoswork.tallybook.descriptor.metadata.IFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.classmetadata.MutableClassMeta;
import com.taoswork.tallybook.descriptor.metadata.exception.MetadataException;
import com.taoswork.tallybook.descriptor.metadata.processor.ProcessResult;
import com.taoswork.tallybook.descriptor.metadata.processor.handler.basic.IClassHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EndClassMetaClassHandler implements IClassHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(EndClassMetaClassHandler.class);


    @Override
    public ProcessResult process(Class clz, MutableClassMeta classmeta) {
        IFieldMeta rawNameFieldMeta = null;
        IFieldMeta nameFieldMeta = null;
        IFieldMeta idFieldMeta = null;
        for (IFieldMeta fm : classmeta.getReadonlyFieldMetaMap().values()) {
            if (fm.getName().toLowerCase().equals("name")) {
                rawNameFieldMeta = fm;
            }
            if (fm.isNameField()) {
                nameFieldMeta = fm;
            }
            if (fm.isId()) {
                if (idFieldMeta != null) {
                    LOGGER.error("ID field Exist, aborting ...");
                    throw new MetadataException("ID field Exist, aborting ...");
                }
                idFieldMeta = fm;
            }
        }
        if ((nameFieldMeta == null) && (rawNameFieldMeta != null)) {
            nameFieldMeta = rawNameFieldMeta;
            nameFieldMeta.setNameField(true);
        }

        if (nameFieldMeta != null) {
            classmeta.setNameField(nameFieldMeta.getField());
        }
        if (idFieldMeta != null) {
            classmeta.setIdField(idFieldMeta.getField());
        }

        classmeta.finishBuilding();

        return ProcessResult.PASSING_THROUGH;
    }
}
