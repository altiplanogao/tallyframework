package com.taoswork.tallybook.descriptor.metadata.processor.handler.fields.list;

import com.taoswork.tallybook.datadomain.base.entity.CollectionField;
import com.taoswork.tallybook.datadomain.base.entity.CollectionMode;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.FieldMetaMediate;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.IFieldMetaSeed;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.list.LookupListFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.processor.ProcessResult;
import com.taoswork.tallybook.descriptor.metadata.processor.handler.basic.BaseFieldHandler;
import com.taoswork.tallybook.descriptor.metadata.processor.handler.basic.IFacet;

import java.lang.reflect.Field;
import java.util.Collection;

/**
 * Created by Gao Yuan on 2016/2/20.
 */
public class LookupListFieldHandler extends BaseFieldHandler {
    @Override
    protected boolean canProcess(Field field, FieldMetaMediate metaMediate) {
        Class collectionType = field.getType();
        if (!Collection.class.isAssignableFrom(collectionType)) {
            return false;
        }
        IFacet facet = metaMediate.getFacet(LookupListFieldMeta.Facet.class);
        if(facet != null){
            return true;
        }
        CollectionField cf = field.getDeclaredAnnotation(CollectionField.class);
        if(cf != null && CollectionMode.Lookup.equals(cf.mode())){
            return true;
        }
        return false;
    }

    @Override
    protected ProcessResult doProcess(Field field, FieldMetaMediate metaMediate) {
        LookupListFieldMeta.Facet facet =
                LookupListFieldMeta.Facet.byAnnotation(field);
        if(facet == null){
            facet = metaMediate.getFacet(LookupListFieldMeta.Facet.class);
        }
        if(facet != null){
            IFieldMetaSeed seed = new LookupListFieldMeta.Seed(facet);
            metaMediate.setMetaSeed(seed);
            return ProcessResult.HANDLED;
        }
        return ProcessResult.FAILED;
    }
}
