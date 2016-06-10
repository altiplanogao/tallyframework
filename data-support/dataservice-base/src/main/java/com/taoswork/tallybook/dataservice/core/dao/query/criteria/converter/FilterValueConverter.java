package com.taoswork.tallybook.dataservice.core.dao.query.criteria.converter;

/**
 * @author
 */
public interface FilterValueConverter<T> {

    T convert(Class type, String stringValue);

}
