package com.taoswork.tallybook.descriptor.description.builder;

import com.taoswork.tallybook.datadomain.base.presentation.Visibility;
import com.taoswork.tallybook.descriptor.description.builder.m2i.FM2I;
import com.taoswork.tallybook.descriptor.description.builder.m2i.FM2IPool;
import com.taoswork.tallybook.descriptor.description.descriptor.field.IFieldInfo;
import com.taoswork.tallybook.descriptor.description.descriptor.field.base.IFieldInfoRW;
import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
import com.taoswork.tallybook.descriptor.metadata.IFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.exception.MetadataException;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.embedded.EmbeddedFieldMeta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/10/24.
 */
public class FieldInfoBuilder {
    private final FM2IPool fm2IPool;

    public FieldInfoBuilder(FM2IPool fm2IPool) {
        this.fm2IPool = fm2IPool;
    }

    private IFieldInfo createFieldInfo(final IClassMeta topMeta, String prefix, IFieldMeta fieldMeta,
                                              Collection<Class> collectionTypeReferenced) {
        IFieldInfo resultByFm2i = fm2IPool.createByFM2I(topMeta, prefix, fieldMeta, collectionTypeReferenced);
        if(resultByFm2i != null){
            return resultByFm2i;
        }

        if (fieldMeta instanceof EmbeddedFieldMeta) {
            //handled in createFieldInfos()
            throw new MetadataException("EmbeddedFieldMeta shouldn't e handed here.");

        } else {
            throw new MetadataException("Unexpected FieldMeta type: " + fieldMeta.getClass());
        }
    }

    public Collection<IFieldInfo> createFieldInfos(IClassMeta topMeta, IFieldMeta fieldMeta, Collection<Class> collectionTypeReferenced) {
        List<IFieldInfo> result = new ArrayList<IFieldInfo>();
        createFieldInfos(topMeta, "", fieldMeta, result, collectionTypeReferenced);
        return result;
    }

    private int createFieldInfos(final IClassMeta topMeta, String prefix, IFieldMeta fieldMeta,
                                        Collection<IFieldInfo> fieldInfos, Collection<Class> collectionTypeReferenced) {
        int counter = 0;
        if (fieldMeta instanceof EmbeddedFieldMeta) {
            EmbeddedFieldMeta embeddedFieldMeta = (EmbeddedFieldMeta) fieldMeta;
            int baseOrder = fieldMeta.getOrder();
            int baseVisibility = fieldMeta.getVisibility();
            String subPrefix = FM2I.prepend(prefix, fieldMeta.getName());
            IClassMeta emCm = ((EmbeddedFieldMeta) fieldMeta).getClassMetadata();
            Map<String, IFieldMeta> fieldMetaMap = emCm.getReadonlyFieldMetaMap();
            List<IFieldInfo> subFieldInfos = new ArrayList<IFieldInfo>();
            for (Map.Entry<String, IFieldMeta> fieldMetaEntry : fieldMetaMap.entrySet()) {
                IFieldMeta subFieldMeta = fieldMetaEntry.getValue();
                createFieldInfos(topMeta, subPrefix, subFieldMeta, subFieldInfos, collectionTypeReferenced);
            }
            for (IFieldInfo subFi : subFieldInfos) {
                if (subFi instanceof IFieldInfoRW) {
                    ((IFieldInfoRW) subFi).setOrder(subFi.getOrder() + baseOrder);
                    ((IFieldInfoRW) subFi).setVisibility(Visibility.and(subFi.getVisibility(), baseVisibility));
                }
            }
            fieldInfos.addAll(subFieldInfos);
            counter += subFieldInfos.size();
        } else {
            IFieldInfo fieldInfo = createFieldInfo(topMeta, prefix, fieldMeta, collectionTypeReferenced);
            fieldInfos.add(fieldInfo);
            counter++;
        }
        return counter;
    }
}
