package com.taoswork.tallybook.dataservice.jpa.core.dao.query.criteria.predicate;

import com.taoswork.tallybook.dataservice.core.dao.query.criteria.desc.LongRange;

/**
 * Created by Gao Yuan on 2015/11/3.
 */
public class LongRPredicateProvider extends _SupportRangePredicateProvider<Long, Long, LongRange> {
    @Override
    protected Long numberTypeToDateType(Long number) {
        return number;
    }
}
