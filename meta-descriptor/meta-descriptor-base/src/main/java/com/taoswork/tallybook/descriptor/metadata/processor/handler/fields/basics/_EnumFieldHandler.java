package com.taoswork.tallybook.descriptor.metadata.processor.handler.fields.basics;

import com.taoswork.tallybook.datadomain.base.entity.PersistField;
import com.taoswork.tallybook.datadomain.base.presentation.FieldType;
import com.taoswork.tallybook.datadomain.base.presentation.typed.PresentationEnum;
import com.taoswork.tallybook.descriptor.metadata.classmetadata.ClassMetaUtils;
import com.taoswork.tallybook.descriptor.metadata.exception.MetadataException;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.FieldMetaMediate;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.basic.EnumFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.processor.ProcessResult;
import com.taoswork.tallybook.descriptor.metadata.processor.handler.basic.BaseFieldHandler;
import com.taoswork.tallybook.general.extension.utils.IFriendlyEnum;

import java.lang.reflect.Field;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
class _EnumFieldHandler extends BaseFieldHandler {
    @Override
    protected boolean canProcess(Field field, FieldMetaMediate metaMediate) {
        PersistField persistField = field.getDeclaredAnnotation(PersistField.class);
        if (persistField != null &&
                FieldType.ENUMERATION.equals(persistField.fieldType())) {
            return true;
        } else if (IFriendlyEnum.class.isAssignableFrom(field.getType())) {
            return true;
        }
        return false;
    }

    @Override
    protected ProcessResult doProcess(Field field, FieldMetaMediate metaMediate) {
        PresentationEnum presentationEnum = field.getDeclaredAnnotation(PresentationEnum.class);
        Class enumCls = null;
        if (presentationEnum != null) {
            enumCls = presentationEnum.enumeration();
            if (enumCls.equals(void.class)) {
                enumCls = field.getType();
            }
        } else {
            enumCls = field.getType();
        }
        if (IFriendlyEnum.class.isAssignableFrom(enumCls)) {
            EnumFieldMeta.Seed enumSeed = new EnumFieldMeta.Seed(enumCls);
            metaMediate.setMetaSeed(enumSeed);
            return ProcessResult.HANDLED;
        }
        MetadataException.throwFieldConfigurationException(field);
        return ProcessResult.FAILED;
    }
}
