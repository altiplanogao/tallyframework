package com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.basics;

import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.FieldMetadataIntermediate;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ProcessResult;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.IFieldHandler;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.Visibility;

import javax.persistence.Version;
import java.lang.reflect.Field;

/**
 * Created by Gao Yuan on 2015/10/31.
 */
public class _VersionFieldHandler implements IFieldHandler {
    @Override
    public ProcessResult process(Field a, FieldMetadataIntermediate fieldMetadata) {
        if(a.isAnnotationPresent(Version.class)){
            fieldMetadata.getBasicFieldMetadataObject().setVisibility(Visibility.HIDDEN_ALL);
            return ProcessResult.HANDLED;
        }
        return ProcessResult.PASSING_THROUGH;
    }
}
