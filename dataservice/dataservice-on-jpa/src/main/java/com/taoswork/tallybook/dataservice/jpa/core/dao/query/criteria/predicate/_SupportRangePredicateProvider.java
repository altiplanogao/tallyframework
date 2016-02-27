package com.taoswork.tallybook.dataservice.jpa.core.dao.query.criteria.predicate;

import com.taoswork.tallybook.dataservice.core.dao.query.criteria.desc.Range;
import com.taoswork.tallybook.general.extension.collections.CollectionUtility;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/11/3.
 */
public abstract class _SupportRangePredicateProvider<Nt extends Number, Dt extends Comparable, Rt extends Range<Nt>>
        implements PredicateProvider {

    @Override
    public Predicate buildPredicate(CriteriaBuilder builder,
                                    Class ceilingEntity, String fullPropertyName,
                                    Path explicitPath, List _directValues) {
        if (CollectionUtility.isEmpty(_directValues)) {
            return null;
        }
        List<Object> directTypedValues = _directValues;
        final Path<String> path = explicitPath;
        if (directTypedValues.size() == 0) {
            return null;
        } else if (directTypedValues.size() == 1) {
            Object directVal = directTypedValues.get(0);
            return this.buildSinglePredicate(builder, path, directVal);
        } else {
            List<Predicate> predicates = new ArrayList<Predicate>();
            for (Object directVal : directTypedValues) {
                Predicate predicate = buildSinglePredicate(builder, path, directVal);
                predicates.add(predicate);
            }
            return builder.or(predicates.toArray(new Predicate[predicates.size()]));
        }
    }

    private Predicate buildSinglePredicate(CriteriaBuilder builder, Path path, Object value) {
        if (value instanceof Range) {
            return buildSinglePredicateForRange(builder, path, (Rt) value);
        } else {
            return buildSinglePredicateForNumber(builder, path, (Nt) value);
        }
    }

    private Predicate buildSinglePredicateForNumber(CriteriaBuilder builder, Path path, Nt value) {
        Dt dval = numberTypeToDateType(value);
        return builder.equal(path, dval);
    }

    private Predicate buildSinglePredicateForRange(CriteriaBuilder builder, Path path, Rt range) {
        Expression<Dt> castedPath = path;
        Nt from = range.from;
        Nt to = range.to;
        if (from == null && to == null) {
            return null;
        }
        if (from != null && to != null) {
            Dt dfrom = numberTypeToDateType(from);
            Dt dto = numberTypeToDateType(to);
            return builder.between(castedPath, dfrom, dto);
        }
        if (from != null) {
            Dt dfrom = numberTypeToDateType(from);
            boolean includeFrom = range.includeFrom;
            if (includeFrom) {
                return builder.greaterThanOrEqualTo(path, dfrom);
            } else {
                return builder.greaterThan(path, dfrom);
            }
        } else {
            Dt dto = numberTypeToDateType(to);
            boolean includeTo = range.includeTo;
            if (includeTo) {
                return builder.lessThanOrEqualTo(path, dto);
            } else {
                return builder.lessThan(path, dto);
            }
        }
    }

    protected abstract Dt numberTypeToDateType(Nt number);

}
