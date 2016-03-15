package com.taoswork.tallybook.descriptor.description.builder.m2i;

import com.taoswork.tallybook.descriptor.description.descriptor.field.IFieldInfo;
import com.taoswork.tallybook.descriptor.description.descriptor.field.base.IFieldInfoRW;
import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
import com.taoswork.tallybook.descriptor.metadata.IFieldMeta;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2016/2/22.
 */
public abstract class FM2I<Fm extends IFieldMeta> implements IFM2I {
    public static String prepend(String prefix, String name) {
        if (StringUtils.isNotEmpty(prefix)) {
            name = prefix + "." + name;
        }
        return name;
    }

    public static void setFieldInfoCommonFields(IFieldMeta fm, IFieldInfoRW fi) {
        fi.setOrder(fm.getOrder());
        fi.setRequired(fm.isRequired());
        fi.setVisibility(fm.getVisibility());
        fi.setFieldType(fm.getFieldType());
        fi.setIgnored(fm.getIgnored());
    }

    @Override
    public IFieldInfo createInfo(IClassMeta topMeta, String prefix, IFieldMeta fieldMeta, Collection<Class> collectionTypeReferenced) {
        String name = FM2I.prepend(prefix, fieldMeta.getName());
        String friendlyName = fieldMeta.getFriendlyName();
        IFieldInfoRW fiwr = doCreateInfo(topMeta, (Fm) fieldMeta, name, friendlyName, collectionTypeReferenced);
        setFieldInfoCommonFields(fieldMeta, fiwr);
        return fiwr;
    }

    public abstract IFieldInfoRW doCreateInfo(IClassMeta topMeta, Fm fieldMeta,
                                            String name, String friendlyName, Collection<Class> collectionTypeReferenced);
}
