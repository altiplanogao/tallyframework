package com.taoswork.tallybook.dynamic.datameta.description.builder;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.IFieldInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.base.IFieldInfoRW;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.typed.*;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.typedcollection.CollectionFieldInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.typedcollection.MapFieldInfo;
import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.IFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.embedded.EmbeddedFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typed.*;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typedcollection.CollectionFieldMetadata;
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

    private static IFieldInfo createFieldInfo(String prefix, IFieldMetadata fieldMetadata) {
        String name = prepend(prefix, fieldMetadata.getName());
        String friendlyName = fieldMetadata.getFriendlyName();
        boolean editable = fieldMetadata.isEditable();

        IFieldInfoRW result = null;
        if (fieldMetadata instanceof EnumFieldMetadata) {
            EnumFieldInfo enumFieldInfo = new EnumFieldInfo(name, friendlyName, editable, ((EnumFieldMetadata) fieldMetadata).getEnumerationType());
            result = enumFieldInfo;
        } else if (fieldMetadata instanceof BooleanFieldMetadata) {
            BooleanFieldInfo booleanFieldInfo = new BooleanFieldInfo(name, friendlyName, editable, ((BooleanFieldMetadata) fieldMetadata).getModel());
            result = booleanFieldInfo;
        } else if (fieldMetadata instanceof DateFieldMetadata) {
            DateFieldMetadata dfm = (DateFieldMetadata)fieldMetadata;
            DateFieldInfo dateFieldInfo = new DateFieldInfo(name, friendlyName, editable, dfm.getModel(), dfm.getCellModel());
            result = dateFieldInfo;
        } else if (fieldMetadata instanceof StringFieldMetadata) {
            StringFieldInfo stringFieldInfo = new StringFieldInfo(name, friendlyName, editable, ((StringFieldMetadata) fieldMetadata).getLength());
//            stringFieldInfo.setNameField(fieldMetadata.isNameField());
            result = stringFieldInfo;
        } else if (fieldMetadata instanceof PaleFieldMetadata) {
            PaleFieldInfo stringFieldInfo = new PaleFieldInfo(name, friendlyName, editable);
            result = stringFieldInfo;
        } else if (fieldMetadata instanceof ForeignEntityFieldMetadata) {
            ForeignEntityFieldMetadata feFm = (ForeignEntityFieldMetadata) fieldMetadata;
            ForeignKeyFieldInfo fkFieldInfo = new ForeignKeyFieldInfo(name, friendlyName, editable,
                feFm.getEntityType().getName(), feFm.getIdField(), feFm.getDisplayField());
            result = fkFieldInfo;
        } else if (fieldMetadata instanceof ExternalForeignEntityFieldMetadata) {
            ExternalForeignEntityFieldMetadata feFm = (ExternalForeignEntityFieldMetadata) fieldMetadata;
            ExternalForeignKeyFieldInfo fkFieldInfo = new ExternalForeignKeyFieldInfo(name, friendlyName, editable,
                feFm.getEntityType().getName(), feFm.getEntityFieldName(), feFm.getEntityIdProperty(), feFm.getEntityDisplayProperty());
            result = fkFieldInfo;
        } else if (fieldMetadata instanceof EmbeddedFieldMetadata) {
            throw new IllegalArgumentException();
        } else if (fieldMetadata instanceof CollectionFieldMetadata) {
            CollectionFieldInfo collectionFieldInfo = new CollectionFieldInfo(name, friendlyName, editable);
            result = collectionFieldInfo;
        } else if (fieldMetadata instanceof MapFieldMetadata) {
            MapFieldInfo mapFieldInfo = new MapFieldInfo(name, friendlyName, editable);
            result = mapFieldInfo;
        } else {
            throw new IllegalArgumentException();
        }
//        result.setName(fieldMetadata.getName());
//        result.setFriendlyName(fieldMetadata.getFriendlyName());
        result.setOrder(fieldMetadata.getOrder());
        result.setRequired(fieldMetadata.isRequired());
        result.setVisibility(fieldMetadata.getVisibility());
        result.setFieldType(fieldMetadata.getFieldType());

        return result;
    }

    public static int createFieldInfos(String prefix, IFieldMetadata fieldMetadata, Collection<IFieldInfo> fieldInfos) {
        int counter = 0;
        if (fieldMetadata instanceof EmbeddedFieldMetadata) {
            EmbeddedFieldMetadata embeddedFieldMetadata = (EmbeddedFieldMetadata) fieldMetadata;
            int baseOrder = fieldMetadata.getOrder();
            int baseVisibility = fieldMetadata.getVisibility();
            String subPrefix = prepend(prefix, fieldMetadata.getName());
            ClassMetadata emCm = ((EmbeddedFieldMetadata) fieldMetadata).getClassMetadata();
            Map<String, IFieldMetadata> fieldMetadataMap = emCm.getReadonlyFieldMetadataMap();
            List<IFieldInfo> subFieldInfos = new ArrayList<IFieldInfo>();
            for (Map.Entry<String, IFieldMetadata> fieldMetadataEntry : fieldMetadataMap.entrySet()) {
                IFieldMetadata subFieldMetadata = fieldMetadataEntry.getValue();
                createFieldInfos(subPrefix, subFieldMetadata, subFieldInfos);
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
            IFieldInfo fieldInfo = createFieldInfo(prefix, fieldMetadata);
            fieldInfos.add(fieldInfo);
            counter++;
        }
        return counter;
    }

    public static Collection<IFieldInfo> createFieldInfos(IFieldMetadata fieldMetadata){
        List<IFieldInfo> result = new ArrayList<IFieldInfo>();
        createFieldInfos("", fieldMetadata, result);
        return result;
    }
}
