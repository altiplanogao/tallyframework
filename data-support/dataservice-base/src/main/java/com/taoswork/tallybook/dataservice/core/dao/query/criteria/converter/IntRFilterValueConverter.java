package com.taoswork.tallybook.dataservice.core.dao.query.criteria.converter;

import com.taoswork.tallybook.dataservice.core.dao.query.criteria.desc.IntegerRange;

/**
 * Created by Gao Yuan on 2015/11/2.
 */
public class IntRFilterValueConverter extends _SupportRangeFilterValueConverter<Integer, IntegerRange> {
    @Override
    protected Integer getNumberFromString(String numberString) {
        return Integer.parseInt(numberString);
    }

    @Override
    protected IntegerRange getRangeFromString(String rangeString) {
        return IntegerRange.getInstanceFromString(rangeString);
    }
}
