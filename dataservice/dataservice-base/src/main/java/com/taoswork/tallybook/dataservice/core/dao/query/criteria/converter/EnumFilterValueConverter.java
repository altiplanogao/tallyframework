package com.taoswork.tallybook.dataservice.core.dao.query.criteria.converter;

import com.taoswork.tallybook.general.extension.utils.IFriendlyEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnumFilterValueConverter implements FilterValueConverter<IFriendlyEnum> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnumFilterValueConverter.class);

    @Override
    public IFriendlyEnum convert(Class type, String stringValue) {
        if (IFriendlyEnum.class.isAssignableFrom(type)) {
            IFriendlyEnum enumVal = null;
            try {
                enumVal = (IFriendlyEnum) type.getField(stringValue).get(null);
                return enumVal;
            } catch (Exception e) {
                return null;
            }
        } else {
            return null;
        }
    }
}
