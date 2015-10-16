package com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.classes;

import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.IFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ProcessResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EndClassMetadataClassHandler implements IClassHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(EndClassMetadataClassHandler.class);

    @Override
    public ProcessResult process(Class clz, ClassMetadata classMetadata) {
        IFieldMetadata rawNameFieldMetadata = null;
        IFieldMetadata nameFieldMetadata = null;
        IFieldMetadata idFieldMetadata = null;
        for (IFieldMetadata fm : classMetadata.getReadonlyFieldMetadataMap().values()) {
            if (fm.getName().toLowerCase().equals("name")) {
                rawNameFieldMetadata = fm;
            }
            if (fm.isNameField()) {
                nameFieldMetadata = fm;
            }
            if (fm.isId()) {
                if (idFieldMetadata != null) {
                    LOGGER.error("ID field Existing, aborting ...");
                    throw new IllegalStateException("ID field Existing, aborting ...");
                }
                idFieldMetadata = fm;
            }
        }
        if ((nameFieldMetadata == null) && (rawNameFieldMetadata != null)) {
            nameFieldMetadata = rawNameFieldMetadata;
            nameFieldMetadata.setNameField(true);
        }

        if (nameFieldMetadata != null) {
            classMetadata.setNameField(nameFieldMetadata.getField());
        }
        if (idFieldMetadata != null) {
            classMetadata.setIdField(idFieldMetadata.getField());
        }

        classMetadata.finishBuilding();

        return ProcessResult.PASSING_THROUGH;
    }
}
