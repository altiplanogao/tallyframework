package com.taoswork.tallybook.dynamic.dataservice.entity.metadata.processor.handler.classes;

import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.utils.NativeClassHelper;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.FieldMetadata;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.processor.ProcessResult;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.processor.handler.fields.IFieldHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Map;

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
        Map<String, Field> fields = NativeClassHelper.getFields(clz, fsm);
        FieldMetadata rawNameFieldMetadata = null;
        FieldMetadata nameFieldMetadata = null;
        for (Map.Entry<String, Field> entry : fields.entrySet()) {
            totalfields++;
            Field field = entry.getValue();
            FieldMetadata fieldMetadata = new FieldMetadata(field);
            if(field.getName().toLowerCase().equals("name")){
                rawNameFieldMetadata = fieldMetadata;
            }
            ProcessResult pr = fieldHandler.process(field, fieldMetadata);
            if (classMetadata.getRWFieldMetadataMap().containsKey(entry.getKey())) {
                LOGGER.error("FieldMetadata with name '{}' already exist.", entry.getKey());
            }
            classMetadata.getRWFieldMetadataMap().put(entry.getKey(), fieldMetadata);
            if (ProcessResult.FAILED.equals(pr)) {
                failed++;
                LOGGER.error("FAILURE happened on field '{}' processing of class '{}'", field.getName(), clz.getSimpleName());
            } else if (ProcessResult.HANDLED.equals(pr)) {
                handled++;
            }
            if(fieldMetadata.isNameField()){
                nameFieldMetadata = fieldMetadata;
            }
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
