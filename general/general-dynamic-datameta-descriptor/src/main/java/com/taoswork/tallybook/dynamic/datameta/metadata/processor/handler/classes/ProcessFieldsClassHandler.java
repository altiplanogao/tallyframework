package com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.classes;

import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.FieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ProcessResult;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.IFieldHandler;
import com.taoswork.tallybook.dynamic.datameta.metadata.utils.NativeClassHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public class ProcessFieldsClassHandler implements IClassHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessFieldsClassHandler.class);

    private final IFieldHandler fieldHandler;

    public ProcessFieldsClassHandler(IFieldHandler fieldHandler) {
        this.fieldHandler = fieldHandler;
    }

    @Override
    public ProcessResult process(Class clz, ClassMetadata classMetadata) {
        int failed = 0;
        int handled = 0;
        int totalfields = 0;
        NativeClassHelper.FieldScanMethod fsm = new NativeClassHelper.FieldScanMethod();
        fsm.setIncludeStatic(false).setIncludeTransient(false).setIncludeId(true)
                .setScanSuper(false);
        List<Field> fields = NativeClassHelper.getFields(clz, fsm);
        FieldMetadata rawNameFieldMetadata = null;
        FieldMetadata nameFieldMetadata = null;
        int fieldOrigIndex = 0;
        for (Field field: fields) {
            totalfields++;
            String fieldName = field.getName();

            FieldMetadata fieldMetadata = new FieldMetadata(fieldOrigIndex, field);
            if(field.getName().toLowerCase().equals("name")){
                rawNameFieldMetadata = fieldMetadata;
            }
            ProcessResult pr = fieldHandler.process(field, fieldMetadata);
            if (classMetadata.getRWFieldMetadataMap().containsKey(fieldName)) {
                LOGGER.error("FieldMetadata with name '{}' already exist.", fieldName);
            }
            classMetadata.getRWFieldMetadataMap().put(fieldName, fieldMetadata);
            if (ProcessResult.FAILED.equals(pr)) {
                failed++;
                LOGGER.error("FAILURE happened on field '{}' processing of class '{}'", field.getName(), clz.getSimpleName());
            } else if (ProcessResult.HANDLED.equals(pr)) {
                handled++;
            }
            if(fieldMetadata.isNameField()){
                nameFieldMetadata = fieldMetadata;
            }
            fieldOrigIndex++;
        }

        if((nameFieldMetadata == null) && (rawNameFieldMetadata != null)){
            nameFieldMetadata = rawNameFieldMetadata;
            nameFieldMetadata.setNameField(true);
        }

        if (failed > 0) {
            return ProcessResult.FAILED;
        } else if (totalfields <= 0) {
            return ProcessResult.INAPPLICABLE;
        } else if (handled > 0) {
            return ProcessResult.HANDLED;
        } else {
            LOGGER.error("No Field processed successfuly, treat unknown result as error.");
            return ProcessResult.FAILED;
        }
    }
}
