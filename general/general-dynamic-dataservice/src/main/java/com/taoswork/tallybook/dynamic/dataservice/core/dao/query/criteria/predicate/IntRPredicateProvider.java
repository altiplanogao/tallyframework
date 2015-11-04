package com.taoswork.tallybook.dynamic.dataservice.core.dao.query.criteria.predicate;

import com.taoswork.tallybook.dynamic.dataservice.core.dao.query.criteria.desc.IntegerRange;
import com.taoswork.tallybook.dynamic.dataservice.core.dao.query.criteria.desc.LongRange;

/**
 * Created by Gao Yuan on 2015/11/3.
 */
public class IntRPredicateProvider extends _SupportRangePredicateProvider<Integer, Integer, IntegerRange> {
    @Override
    protected Integer numberTypeToDateType(Integer number) {
        return number;
    }
}
