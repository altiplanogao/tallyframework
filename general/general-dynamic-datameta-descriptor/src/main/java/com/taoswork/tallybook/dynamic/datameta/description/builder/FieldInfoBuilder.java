package com.taoswork.tallybook.dynamic.datameta.description.builder;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.IFieldInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.base.IFieldInfoRW;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.typed.*;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.typedcollection.*;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.typedmap.MapFieldInfo;
import com.taoswork.tallybook.dynamic.datameta.metadata.CollectionTypesSetting;
import com.taoswork.tallybook.dynamic.datameta.metadata.EntryTypeUnion;
import com.taoswork.tallybook.dynamic.datameta.metadata.IClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.IFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.embedded.EmbeddedFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typed.*;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typedcollection.CollectionFieldMetadata;
import com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection.CollectionMode;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typedcollection.MapFieldMetadata;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.Visibility;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/10/24.
 */
public class FieldInfoBuilder {
    private static String prepend(String prefix, String name) {
        if (StringUtils.isNotEmpty(prefix)) {
            name = prefix + "." + name;
        }
        return name;
    }

    private static IFieldInfo createFieldInfo(final IClassMetadata topClassMetadata, String prefix, IFieldMetadata fieldMetadata,
                                              Collection<Class> collectionTypeReferenced) {
        String name = prepend(prefix, fieldMetadata.getName());
        String friendlyName = fieldMetadata.getFriendlyName();
        boolean editable = fieldMetadata.isEditable();

        IFieldInfoRW result = null;
        if (fieldMetadata instanceof StringFieldMetadata) {
            StringFieldInfo stringFieldInfo = new StringFieldInfo(name, friendlyName, editable, ((StringFieldMetadata) fieldMetadata).getLength());
            result = stringFieldInfo;
        } else if (fieldMetadata instanceof EnumFieldMetadata) {
            EnumFieldInfo enumFieldInfo = new EnumFieldInfo(name, friendlyName, editable, ((EnumFieldMetadata) fieldMetadata).getEnumerationType());
            result = enumFieldInfo;
        } else if (fieldMetadata instanceof BooleanFieldMetadata) {
            BooleanFieldInfo booleanFieldInfo = new BooleanFieldInfo(name, friendlyName, editable, ((BooleanFieldMetadata) fieldMetadata).getMode());
            result = booleanFieldInfo;
        } else if (fieldMetadata instanceof DateFieldMetadata) {
            DateFieldMetadata dfm = (DateFieldMetadata)fieldMetadata;
            DateFieldInfo dateFieldInfo = new DateFieldInfo(name, friendlyName, editable, dfm.getMode(), dfm.getCellMode());
            result = dateFieldInfo;
        } else if (fieldMetadata instanceof ForeignEntityFieldMetadata) {
            ForeignEntityFieldMetadata feFm = (ForeignEntityFieldMetadata) fieldMetadata;
            ForeignKeyFieldInfo fkFieldInfo = new ForeignKeyFieldInfo(name, friendlyName, editable,
                feFm.getDeclareType(), feFm.getTargetType(),
                feFm.getIdField(), feFm.getDisplayField());
            result = fkFieldInfo;
        } else if (fieldMetadata instanceof ExternalForeignEntityFieldMetadata) {
            ExternalForeignEntityFieldMetadata feFm = (ExternalForeignEntityFieldMetadata) fieldMetadata;
            ExternalForeignKeyFieldInfo fkFieldInfo = new ExternalForeignKeyFieldInfo(name, friendlyName, editable,
                feFm.getDeclareType(), feFm.getTargetType(), feFm.getTheDataFieldName(), feFm.getIdProperty(), feFm.getDisplayProperty());
            result = fkFieldInfo;
        } else if (fieldMetadata instanceof PaleFieldMetadata) {
            PaleFieldInfo stringFieldInfo = new PaleFieldInfo(name, friendlyName, editable);
            result = stringFieldInfo;
        } else if (fieldMetadata instanceof EmbeddedFieldMetadata) {
            //handled in createFieldInfos()
            throw new IllegalArgumentException();
        } else if (fieldMetadata instanceof CollectionFieldMetadata) {
            final CollectionFieldMetadata typedFieldMetadata = (CollectionFieldMetadata)fieldMetadata;
            CollectionTypesSetting collectionTypesSetting = typedFieldMetadata.getCollectionTypesSetting();
//            final EntryTypeUnion entryTypeUnion = typedFieldMetadata.getEntryTypeUnion();

            final Class referencingCollectionEntryCls = typedFieldMetadata.getPresentationClass();
            if(referencingCollectionEntryCls != null){
                collectionTypeReferenced.add(referencingCollectionEntryCls);
            }
            final String referencingCollectionEntryClsName = (referencingCollectionEntryCls != null)? referencingCollectionEntryCls.getName() : "";

            final CollectionMode collectionMode = collectionTypesSetting.getCollectionMode();
            switch (collectionMode){
                case Primitive:
                    result = new PrimitiveCollectionFieldInfo(name, friendlyName, editable, referencingCollectionEntryClsName);
                    break;
                case Basic:
                    result = new BasicCollectionFieldInfo(name, friendlyName, editable, referencingCollectionEntryClsName);
                    break;
                case Entity:
                    result = new EntityCollectionFieldInfo(name, friendlyName, editable, referencingCollectionEntryClsName);
                    break;
                case Lookup:
                    result = new LookupCollectionFieldInfo(name, friendlyName, editable, referencingCollectionEntryClsName);
                    break;
                case AdornedLookup:
                    result = new AdornedLookupCollectionFieldInfo(name, friendlyName, editable, referencingCollectionEntryClsName);
                    break;
                default:
                    throw new IllegalArgumentException("CollectionMode not supported: " + collectionMode);
            }
        } else if (fieldMetadata instanceof MapFieldMetadata) {
            MapFieldMetadata typedFieldMetadata = (MapFieldMetadata)fieldMetadata;
            EntryTypeUnion keyEntryTypeUnion = typedFieldMetadata.getKeyType();
            EntryTypeUnion valueEntryTypeUnion = typedFieldMetadata.getValueType();
            Class keyReferencingCollectionEntryCls = keyEntryTypeUnion.getPresentationClass();
            if(keyReferencingCollectionEntryCls != null){
                collectionTypeReferenced.add(keyReferencingCollectionEntryCls);
            }
            Class valReferencingCollectionEntryCls = valueEntryTypeUnion.getPresentationClass();
            if(valReferencingCollectionEntryCls != null){
                collectionTypeReferenced.add(valReferencingCollectionEntryCls);
            }

            MapFieldInfo mapFieldInfo = new MapFieldInfo(name, friendlyName, editable,
                keyEntryTypeUnion, valueEntryTypeUnion);
            result = mapFieldInfo;
        } else {
            throw new IllegalArgumentException();
        }
        result.setOrder(fieldMetadata.getOrder());
        result.setRequired(fieldMetadata.isRequired());
        result.setVisibility(fieldMetadata.getVisibility());
        result.setFieldType(fieldMetadata.getFieldType());
        result.setIgnored(fieldMetadata.getIgnored());

        return result;
    }

    public static Collection<IFieldInfo> createFieldInfos(IClassMetadata topClassMetadata, IFieldMetadata fieldMetadata, Collection<Class> collectionTypeReferenced){
        List<IFieldInfo> result = new ArrayList<IFieldInfo>();
        createFieldInfos(topClassMetadata, "", fieldMetadata, result, collectionTypeReferenced);
        return result;
    }

    private static int createFieldInfos(final IClassMetadata topClassMetadata, String prefix, IFieldMetadata fieldMetadata,
                                        Collection<IFieldInfo> fieldInfos, Collection<Class> collectionTypeReferenced) {
        int counter = 0;
        if (fieldMetadata instanceof EmbeddedFieldMetadata) {
            EmbeddedFieldMetadata embeddedFieldMetadata = (EmbeddedFieldMetadata) fieldMetadata;
            int baseOrder = fieldMetadata.getOrder();
            int baseVisibility = fieldMetadata.getVisibility();
            String subPrefix = prepend(prefix, fieldMetadata.getName());
            IClassMetadata emCm = ((EmbeddedFieldMetadata) fieldMetadata).getClassMetadata();
            Map<String, IFieldMetadata> fieldMetadataMap = emCm.getReadonlyFieldMetadataMap();
            List<IFieldInfo> subFieldInfos = new ArrayList<IFieldInfo>();
            for (Map.Entry<String, IFieldMetadata> fieldMetadataEntry : fieldMetadataMap.entrySet()) {
                IFieldMetadata subFieldMetadata = fieldMetadataEntry.getValue();
                createFieldInfos(topClassMetadata, subPrefix, subFieldMetadata, subFieldInfos, collectionTypeReferenced);
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
            IFieldInfo fieldInfo = createFieldInfo(topClassMetadata, prefix, fieldMetadata, collectionTypeReferenced);
            fieldInfos.add(fieldInfo);
            counter++;
        }
        return counter;
    }
}
