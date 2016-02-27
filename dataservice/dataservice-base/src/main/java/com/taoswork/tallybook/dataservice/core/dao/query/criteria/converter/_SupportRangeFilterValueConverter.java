package com.taoswork.tallybook.dataservice.core.dao.query.criteria.converter;

import com.taoswork.tallybook.dataservice.core.dao.query.criteria.desc.Range;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Gao Yuan on 2015/11/2.
 */
abstract class _SupportRangeFilterValueConverter<Nt extends Number, Rt extends Range<Nt>> implements FilterValueConverter<Object> {
    protected abstract Nt getNumberFromString(String numberString);

    protected abstract Rt getRangeFromString(String rangeString);

    @Override
    public Object convert(Class type, String stringValue) {
        if (StringUtils.isEmpty(stringValue)) {
            return null;
        }
        boolean isRange = Range.checkIsRange(stringValue, true);
        if (isRange) {
            return getRangeFromString(stringValue);
        } else {
            return getNumberFromString(stringValue);
        }
    }
}
