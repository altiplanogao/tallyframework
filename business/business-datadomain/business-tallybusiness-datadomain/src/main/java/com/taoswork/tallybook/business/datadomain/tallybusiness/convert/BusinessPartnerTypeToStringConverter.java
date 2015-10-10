package com.taoswork.tallybook.business.datadomain.tallybusiness.convert;

import com.taoswork.tallybook.business.datadomain.tallybusiness.BusinessPartnerType;

import javax.persistence.AttributeConverter;

public class BusinessPartnerTypeToStringConverter implements AttributeConverter<BusinessPartnerType, String> {
    @Override
    public String convertToDatabaseColumn(BusinessPartnerType attribute) {
        return attribute == null ? "" : attribute.getType();
    }

    @Override
    public BusinessPartnerType convertToEntityAttribute(String dbData) {
        return BusinessPartnerType.fromType(dbData);
    }

}
