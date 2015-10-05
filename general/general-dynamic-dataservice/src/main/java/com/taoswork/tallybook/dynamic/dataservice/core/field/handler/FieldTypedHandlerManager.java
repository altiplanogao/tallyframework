package com.taoswork.tallybook.dynamic.dataservice.core.field.handler;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldMetadata;
import com.taoswork.tallybook.dynamic.dataservice.core.field.FieldTypeType;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.*;

public class FieldTypedHandlerManager<H extends IFieldTypedHandler> {
    //Real data
    private MultiValueMap<FieldTypeType, H> typedFieldHandlers = new LinkedMultiValueMap<FieldTypeType, H>();

    //Cache
    private Map<FieldTypeType, Set<H>> cachedFieldHandlers = new HashMap<FieldTypeType, Set<H>>();

    public FieldTypedHandlerManager addHandlers(H... fieldHandlers){
        for (H handler : fieldHandlers){
            this.addHandler(handler);
        }
        return this;
    }

    public FieldTypedHandlerManager addHandler(H fieldHandler) {
        if (fieldHandler != null) {
            FieldTypeType typeType = new FieldTypeType(
                fieldHandler.supportedFieldType(), fieldHandler.supportedFieldClass());
            typedFieldHandlers.add(typeType, fieldHandler);
        }
        return this;
    }

    public Collection<H> getHandlers(FieldMetadata fieldMetadata) {
        return this.getHandlers(fieldMetadata.getFieldType(), fieldMetadata.getFieldClass());
    }

    public Collection<H> getHandlers(FieldType type, Class clz){
        FieldTypeType typeType = new FieldTypeType(type, clz);
        Set<H> cache = cachedFieldHandlers.getOrDefault(typeType, null);
        if(cache == null){
            cache = new HashSet<H>();
            FieldTypeType typeOnlyType = new FieldTypeType(type);
            FieldTypeType typeOnlyClz = new FieldTypeType(clz);

            List<H> typed = typedFieldHandlers.getOrDefault(typeType, null);
            List<H> typedT = typedFieldHandlers.getOrDefault(typeOnlyType, null);
            List<H> typedC = typedFieldHandlers.getOrDefault(typeOnlyClz, null);

            if(typed != null)
                cache.addAll(typed);
            if(typedT != null)
                cache.addAll(typedT);
            if(typedC != null)
                cache.addAll(typedC);

            cachedFieldHandlers.put(typeType, cache);
        }
        return cache;
    }

}
