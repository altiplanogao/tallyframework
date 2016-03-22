package com.taoswork.tallybook.dataservice.jpa.core.dao.query.criteria.predicate;

import com.taoswork.tallybook.dataservice.core.dao.query.criteria.desc.LongRange;

import java.util.Date;

/**
 * Created by Gao Yuan on 2015/11/3.
 */
public class DateRPredicateProvider extends _SupportRangePredicateProvider<Long, Date, LongRange> {
    @Override
    protected Date numberTypeToDateType(Long number) {
        return new Date(number);
    }
}