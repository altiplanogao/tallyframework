package com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata;

import com.taoswork.tallybook.dynamic.datameta.metadata.IFieldMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class FieldMetadataCreator {
    private static final Logger LOGGER = LoggerFactory.getLogger(FieldMetadataCreator.class);

    public static IFieldMetadata create(FieldMetadataIntermediate intermediate) {
        Class<? extends IFieldMetadata> targetType = intermediate.getTargetMetadataType();
        if (targetType == null) {
            LOGGER.error("Intermediate Field Metadata has no target type, aborting ...");
            throw new RuntimeException("Intermediate Field Metadata has no target type, aborting ...");
        }
        try {
            Constructor constructor = targetType.getConstructor(new Class[]{FieldMetadataIntermediate.class});
            IFieldMetadata fieldMetadata = (IFieldMetadata) constructor.newInstance(new Object[]{intermediate});
            return fieldMetadata;
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            LOGGER.error("Type {} haven't implemented Constructor(FieldMetadataIntermediate), aborting...");
            throw new RuntimeException("Type {} haven't implemented Constructor(FieldMetadataIntermediate), aborting...");
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
