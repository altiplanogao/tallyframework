package com.taoswork.tallybook.descriptor.metadata.processor.handler.classes;

import com.taoswork.tallybook.datadomain.base.entity.PersistField;
import com.taoswork.tallybook.descriptor.metadata.IFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.classmetadata.MutableClassMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.FieldMetaMediate;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.IFieldMetaSeed;
import com.taoswork.tallybook.descriptor.metadata.processor.IClassProcessor;
import com.taoswork.tallybook.descriptor.metadata.processor.IFieldProcessor;
import com.taoswork.tallybook.descriptor.metadata.processor.ProcessResult;
import com.taoswork.tallybook.descriptor.metadata.processor.handler.basic.IClassHandler;
import com.taoswork.tallybook.descriptor.metadata.utils.GeneralFieldScanMethod;
import com.taoswork.tallybook.general.solution.reflect.ClassUtility;
import com.taoswork.tallybook.general.solution.reflect.FieldScanMethod;
import com.taoswork.tallybook.general.solution.reflect.FieldScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ProcessFieldsClassHandler implements IClassHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessFieldsClassHandler.class);

    private final IFieldProcessor fieldHandler;

    public ProcessFieldsClassHandler(IClassProcessor classProcessor) {
        this.fieldHandler = classProcessor.getTopFieldProcessor();
    }

    @Override
    public ProcessResult process(Class clz, MutableClassMeta mutableClassMetadata) {
        int failed = 0;
        int handled = 0;
        int totalfields = 0;
        FieldScanMethod fsm = new GeneralFieldScanMethod();
        fsm.setIncludeStatic(false).setIncludeTransient(false).setIncludeId(true)
                .setScanSuper(false);
        List<Field> fields = new ArrayList<Field>();
        Collection<String> pfos = mutableClassMetadata.getFieldsOverrided();
        for(String fieldName : pfos) {
            Field f = ClassUtility.getFieldOfName(clz, fieldName, true);
            if (f == null) {
                throw new RuntimeException("[" + ProcessFieldsClassHandler.class + "] Field " + fieldName + " in " + clz + " doesn't exist , aborting ...");
            }
            fields.add(f);
        }
        FieldScanner.getFields(clz, fsm, fields);
        int fieldOrigIndex = 0;
        for (Field field : fields) {
            totalfields++;
            String fieldName = field.getName();

            FieldMetaMediate fieldMetaMediate = new FieldMetaMediate(mutableClassMetadata, fieldOrigIndex, field);
            ProcessResult pr = fieldHandler.process(field, fieldMetaMediate);
            if (mutableClassMetadata.getRWFieldMetaMap().containsKey(fieldName)) {
                LOGGER.error("FieldMeta with name '{}' already exist.", fieldName);
            }
            IFieldMeta fieldMeta = createFieldMeta(fieldMetaMediate);
            mutableClassMetadata.getRWFieldMetaMap().put(fieldName, fieldMeta);
            if (ProcessResult.FAILED.equals(pr)) {
                failed++;
                LOGGER.error("FAILURE happened on field '{}' processing of class '{}'", field.getName(), clz.getSimpleName());
            } else if (ProcessResult.HANDLED.equals(pr)) {
                handled++;
            }
            fieldOrigIndex++;
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

    public static IFieldMeta createFieldMeta(FieldMetaMediate intermediate) {
        IFieldMetaSeed seed = intermediate.getMetaSeed();
        if(seed == null){
            LOGGER.error("Intermediate Field Metadata Seed not defined, aborting ...");
            throw new RuntimeException("Intermediate Field Metadata Seed not defined, aborting ...");
        }else {
            return seed.makeFieldMeta(intermediate.getBasicFieldMetaObject());
        }
    }
}
