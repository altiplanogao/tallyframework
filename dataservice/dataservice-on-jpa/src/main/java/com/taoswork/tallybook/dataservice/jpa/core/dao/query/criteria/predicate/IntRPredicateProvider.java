package com.taoswork.tallybook.dataservice.jpa.core.dao.query.criteria.predicate;

import com.taoswork.tallybook.dataservice.core.dao.query.criteria.desc.IntegerRange;

/**
 * Created by Gao Yuan on 2015/11/3.
 */
public class IntRPredicateProvider extends _SupportRangePredicateProvider<Integer, Integer, IntegerRange> {
    @Override
    protected Integer numberTypeToDateType(Integer number) {
        return number;
    }
}
