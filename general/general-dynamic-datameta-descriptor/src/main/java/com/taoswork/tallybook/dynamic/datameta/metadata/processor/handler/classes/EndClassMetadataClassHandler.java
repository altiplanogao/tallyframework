package com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.classes;

import com.taoswork.tallybook.dynamic.datameta.metadata.IFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.classmetadata.MutableClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ProcessResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EndClassMetadataClassHandler implements IClassHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(EndClassMetadataClassHandler.class);

    @Override
    public ProcessResult process(Class clz, MutableClassMetadata mutableClassMetadata) {
        IFieldMetadata rawNameFieldMetadata = null;
        IFieldMetadata nameFieldMetadata = null;
        IFieldMetadata idFieldMetadata = null;
        for (IFieldMetadata fm : mutableClassMetadata.getReadonlyFieldMetadataMap().values()) {
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
            mutableClassMetadata.setNameField(nameFieldMetadata.getField());
        }
        if (idFieldMetadata != null) {
            mutableClassMetadata.setIdField(idFieldMetadata.getField());
        }

        mutableClassMetadata.finishBuilding();

        return ProcessResult.PASSING_THROUGH;
    }
}
