package com.taoswork.tallybook.general.authority.domain.resource.converter;

import com.taoswork.tallybook.general.authority.domain.resource.ResourceProtectionMode;

import javax.persistence.AttributeConverter;

/**
 * Created by Gao Yuan on 2015/10/20.
 */
public class ProtectionModeToStringConverter  implements AttributeConverter<ResourceProtectionMode, String> {
    @Override
    public String convertToDatabaseColumn(ResourceProtectionMode attribute) {
        return attribute == null ? "" : attribute.getType();
    }

    @Override
    public ResourceProtectionMode convertToEntityAttribute(String dbData) {
        return ResourceProtectionMode.fromType(dbData);
    }
}
