package com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.classes;

import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.FieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ProcessResult;
import com.taoswork.tallybook.dynamic.datameta.metadata.utils.FriendlyNameHelper;

public class EndClassMetadataClassHandler implements IClassHandler {
    @Override
    public ProcessResult process(Class clz, ClassMetadata classMetadata) {
        FieldMetadata rawNameFieldMetadata = null;
        FieldMetadata nameFieldMetadata = null;
        for(FieldMetadata fm : classMetadata.getReadonlyFieldMetadataMap().values()){
            if(fm.getName().toLowerCase().equals("name")){
                rawNameFieldMetadata = fm;
            }
            if(fm.isNameField()){
                nameFieldMetadata = fm;
            }
        }
        if((nameFieldMetadata == null) && (rawNameFieldMetadata != null)){
            nameFieldMetadata = rawNameFieldMetadata;
            nameFieldMetadata.setNameField(true);
        }

        if(nameFieldMetadata != null){
            classMetadata.setNameField(nameFieldMetadata.getField());
        }

        classMetadata.finishBuilding();

        return ProcessResult.PASSING_THROUGH;
    }
}
