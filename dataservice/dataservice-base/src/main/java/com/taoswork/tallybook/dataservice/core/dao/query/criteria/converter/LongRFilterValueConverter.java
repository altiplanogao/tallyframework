package com.taoswork.tallybook.dataservice.core.dao.query.criteria.converter;

import com.taoswork.tallybook.dataservice.core.dao.query.criteria.desc.LongRange;

/**
 * Created by Gao Yuan on 2015/11/2.
 */
public class LongRFilterValueConverter extends _SupportRangeFilterValueConverter<Long, LongRange> {
    @Override
    protected Long getNumberFromString(String numberString) {
        return Long.parseLong(numberString);
    }

    @Override
    protected LongRange getRangeFromString(String rangeString) {
        return LongRange.getInstanceFromString(rangeString);
    }
}
