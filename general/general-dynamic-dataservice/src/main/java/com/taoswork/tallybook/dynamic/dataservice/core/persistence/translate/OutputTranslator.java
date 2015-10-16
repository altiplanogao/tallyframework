package com.taoswork.tallybook.dynamic.dataservice.core.persistence.translate;

import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.IFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.embedded.EmbeddedFieldMetadata;
import com.taoswork.tallybook.dynamic.dataservice.core.exception.ServiceException;
import com.taoswork.tallybook.dynamic.dataservice.core.metaaccess.DynamicEntityMetadataAccess;
import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

public class OutputTranslator {
    private final DynamicEntityMetadataAccess dynamicEntityMetadataAccess;

    public OutputTranslator(DynamicEntityMetadataAccess dynamicEntityMetadataAccess) {
        this.dynamicEntityMetadataAccess = dynamicEntityMetadataAccess;
    }

    public <T extends Persistable> T makeSafeCopy(T rec, boolean skipCollection) throws ServiceException {
        if(rec == null)
            return null;
        try {
            T result = (T)rec.getClass().newInstance();
            ClassMetadata classMetadata = this.dynamicEntityMetadataAccess.getClassMetadata(rec.getClass(), false);
            Collection<String> nonCollectionFields = classMetadata.getNonCollectionFields();
            Map<String, IFieldMetadata> fieldMetadataMap = classMetadata.getReadonlyFieldMetadataMap();
            for (Map.Entry<String, IFieldMetadata> fieldMetaEntry : fieldMetadataMap.entrySet()) {
                String fieldName = fieldMetaEntry.getKey();
                IFieldMetadata fieldMetadata = fieldMetaEntry.getValue();
                if (fieldMetadata instanceof EmbeddedFieldMetadata) {
                    //...
                } else if (fieldMetadata.isCollectionField()) {
                    if (skipCollection) {
                        continue;
                    }
                    //...
                } else {
                    Field field = fieldMetadata.getField();
                    field.set(result, field.get(rec));
                }
            }
            return result;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }
}
