package com.taoswork.tallybook.dynamic.dataservice.core.dataio;

import com.taoswork.tallybook.dynamic.dataservice.core.exception.ServiceException;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.BiConsumer;

public class ExternalReference {
    private static class ReferencingSlot{
        final Object holder;
        final Field holdingField;
        private final EntityReference entityReference;

        public ReferencingSlot(Object holder, Field holdingField, EntityReference entityReference) {
            this.holder = holder;
            this.holdingField = holdingField;
            this.entityReference = entityReference;
        }
    }

    private final Map<Class, EntityReferences> referencesByType = new HashMap();
    private final List<ReferencingSlot> referencingSlots = new ArrayList<ReferencingSlot>();

    public void publishReference(Object holder, Field holdingField, Class entityType, Object id){
        String entityTypeName = entityType.getName();
        referencesByType.putIfAbsent(entityType, new EntityReferences(entityTypeName));
        EntityReferences references = referencesByType.get(entityType);
        references.pushReference(id);

        EntityReference pr = new EntityReference(entityTypeName, id);
        referencingSlots.add(new ReferencingSlot(holder, holdingField, pr));
    }

    public boolean hasReference(){
        return !referencingSlots.isEmpty();
    }

    public Map<String, EntityRecords> calcReferenceValue(IEntityRecordsFetcher fetcher) throws ServiceException {
        Map<String, EntityRecords> result = new HashMap<String, EntityRecords>();
        for(Map.Entry<Class, EntityReferences> entry : referencesByType.entrySet()){
            Class entityType = entry.getKey();
            String entityTypeName = entityType.getName();
            EntityReferences entityReferences = entry.getValue();
            final EntityRecords entityRecords = new EntityRecords(entityTypeName);
            Collection<Object> ids = entityReferences.getIds();
            Map<Object, Object> records = fetcher.fetch(entityType, ids);

            records.forEach(new BiConsumer<Object, Object>() {
                @Override
                public void accept(Object k, Object v) {
                    entityRecords.setRecord(k, v);
                }
            });

            if(!entityRecords.isEmpty()){
                result.put(entityTypeName, entityRecords);
            }
        }
        return result;
    }

    public void fillReferencingSlots(Map<String, EntityRecords> records) throws ServiceException{
        try {
            for (ReferencingSlot slot : referencingSlots) {
                EntityReference reference = slot.entityReference;
                String entityType = reference.getEntityType();
                Object entityId = reference.getEntityId();
                Object entityRecord = null;
                EntityRecords typedRecords = records.get(entityType);
                if (typedRecords != null) {
                    entityRecord = typedRecords.getRecord(entityId);
                }
                if (entityRecord != null) {
                    slot.holdingField.set(slot.holder, entityRecord);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

}
