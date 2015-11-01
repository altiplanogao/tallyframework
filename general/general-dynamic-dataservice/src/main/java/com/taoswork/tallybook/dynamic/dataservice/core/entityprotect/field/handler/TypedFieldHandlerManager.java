package com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.field.handler;

import com.taoswork.tallybook.dynamic.datameta.metadata.IFieldMetadata;
import com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.field.FieldTypeType;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.*;

public class TypedFieldHandlerManager<H extends ITypedFieldHandler> {
    //Real data
    private MultiValueMap<FieldTypeType, H> typedFieldHandlers = new LinkedMultiValueMap<FieldTypeType, H>();

    //Cache
    private Map<FieldTypeType, Set<H>> cachedFieldHandlers = new HashMap<FieldTypeType, Set<H>>();

    public TypedFieldHandlerManager addHandlers(H... fieldHandlers) {
        for (H handler : fieldHandlers) {
            this.addHandler(handler);
        }
        return this;
    }

    public TypedFieldHandlerManager addHandler(H fieldHandler) {
        if (fieldHandler != null) {
            FieldTypeType typeType = new FieldTypeType(
                fieldHandler.supportedFieldType(), fieldHandler.supportedFieldClass());
            typedFieldHandlers.add(typeType, fieldHandler);
        }
        return this;
    }

    public Collection<H> getHandlers(IFieldMetadata fieldMetadata) {
        return this.getHandlers(fieldMetadata.getFieldType(), fieldMetadata.getFieldClass());
    }

    public Collection<H> getHandlers(FieldType type, Class clz) {
        FieldTypeType typeType = new FieldTypeType(type, clz);
        Set<H> cache = cachedFieldHandlers.get(typeType);
        if (cache == null) {
            cache = new HashSet<H>();
            FieldTypeType typeOnlyType = new FieldTypeType(type);
            FieldTypeType typeOnlyClz = new FieldTypeType(clz);

            List<H> typed = typedFieldHandlers.get(typeType);
            List<H> typedT = typedFieldHandlers.get(typeOnlyType);
            List<H> typedC = typedFieldHandlers.get(typeOnlyClz);

            if (typed != null)
                cache.addAll(typed);
            if (typedT != null)
                cache.addAll(typedT);
            if (typedC != null)
                cache.addAll(typedC);

            cachedFieldHandlers.put(typeType, cache);
        }
        return cache;
    }

}
