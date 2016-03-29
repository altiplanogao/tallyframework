package com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier.basic;

import com.taoswork.tallybook.descriptor.dataio.copier.CopierContext;
import com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier.BaseFieldCopier;
import com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier.IFieldCopierSolution;
import com.taoswork.tallybook.descriptor.dataio.reference.ExternalReference;
import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.basic.ExternalForeignEntityFieldMeta;

import java.lang.reflect.Field;

/**
 * Created by Gao Yuan on 2016/2/23.
 */
public class VExternalForeignEntityFieldCopier extends BaseFieldCopier<ExternalForeignEntityFieldMeta> {
    public VExternalForeignEntityFieldCopier(IFieldCopierSolution solution) {
        super(solution);
    }

    @Override
    public Class<? extends ExternalForeignEntityFieldMeta> targetMeta() {
        return ExternalForeignEntityFieldMeta.class;
    }

    @Override
    protected boolean doCopy(IClassMeta topMeta, ExternalForeignEntityFieldMeta fieldMeta,
                             Object source, Object target, int currentLevel, int levelLimit,
                             CopierContext copierContext) throws IllegalAccessException, InstantiationException {
        ExternalForeignEntityFieldMeta efeFm = fieldMeta;
        Field foreignKeyField = fieldMeta.getField();
        Field foreignValField = efeFm.getEntityField();
        Object keyVal = foreignKeyField.get(source);
        Object valVal = foreignValField.get(source);
        if (null == keyVal) {
        } else {
            if(valVal == null){
                final ExternalReference externalReference = (copierContext != null) ? copierContext.externalReference : null;
                if(externalReference != null) {
                    Class entityType = efeFm.getTargetType();
                    //backlog data: [type: entityType, key: keyVal]
                    //slot: [target: target, position: foreignValField]
                    externalReference.publishReference(target, foreignValField, entityType, keyVal.toString());
                }
            }
        }
        return true;
    }
}
