package com.taoswork.tallybook.dataservice.mongo.core.query.criteria.predicate;

import com.taoswork.tallybook.dataservice.core.dao.query.criteria.desc.Range;
import com.taoswork.tallybook.general.extension.collections.CollectionUtility;
import org.mongodb.morphia.query.Criteria;
import org.mongodb.morphia.query.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/11/3.
 */
public abstract class BaseRangePredicateProvider<Nt extends Number, Dt extends Comparable, Rt extends Range<Nt>>
        implements PredicateProvider<Object> {
    @Override
    public <T> Criteria buildPredicate(Query<T> query, String path, List<Object> directValues) {
        if (CollectionUtility.isEmpty(directValues)) {
            return null;
        }
        List<Object> directTypedValues = directValues;
        if (directTypedValues.size() == 0) {
            return null;
        } else if (directTypedValues.size() == 1) {
            Object directVal = directTypedValues.get(0);
            return this.buildSinglePredicate(query, path, directVal);
        } else {
            List<Criteria> predicates = new ArrayList<Criteria>();
            for (Object directVal : directTypedValues) {
                Criteria predicate = buildSinglePredicate(query, path, directVal);
                predicates.add(predicate);
            }
            return query.or(predicates.toArray(new Criteria[predicates.size()]));
        }
    }

    private <T> Criteria buildSinglePredicate(Query<T> query, String path, Object value) {
        if (value instanceof Range) {
            return buildSinglePredicateForRange(query, path, (Rt) value);
        } else {
            return buildSinglePredicateForNumber(query, path, (Nt) value);
        }
    }

    private <T> Criteria buildSinglePredicateForNumber(Query<T> query, String path, Nt value) {
        Dt dval = numberTypeToDateType(value);
        return query.criteria(path).equal(dval);
    }

    private <T> Criteria buildSinglePredicateForRange(Query<T> query, String path, Rt range) {
        Nt from = range.from;
        Nt to = range.to;
        if (from == null && to == null) {
            return null;
        }
        Criteria cf = null;
        if (from != null) {
            Dt dfrom = numberTypeToDateType(from);
            if (range.includeFrom) {
                cf = query.criteria(path).greaterThanOrEq(dfrom);
            } else {
                cf = query.criteria(path).greaterThan(dfrom);
            }
        }
        Criteria ct = null;
        if (to != null) {
            Dt dto = numberTypeToDateType(to);
            if (range.includeTo) {
                ct = query.criteria(path).lessThanOrEq(dto);
            } else {
                ct = query.criteria(path).lessThan(dto);
            }
        }
        if (cf != null) {
            if (ct != null) {
                return query.and(cf, ct);
            }
            return cf;
        }
        if (ct != null) {
            return ct;
        }
        return null;
    }

    protected abstract Dt numberTypeToDateType(Nt number);

}
