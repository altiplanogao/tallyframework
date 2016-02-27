package com.taoswork.tallybook.testmaterial.jpa.domain.business.enumtype;

import javax.persistence.AttributeConverter;

public class CompanyTypeToStringConverter implements AttributeConverter<CompanyType, String> {
    @Override
    public String convertToDatabaseColumn(CompanyType attribute) {
        return attribute == null ? "" : attribute.getType();
    }

    @Override
    public CompanyType convertToEntityAttribute(String dbData) {
        return CompanyType.fromType(dbData);
    }
}
