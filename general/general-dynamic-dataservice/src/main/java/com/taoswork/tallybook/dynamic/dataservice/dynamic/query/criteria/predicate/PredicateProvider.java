package com.taoswork.tallybook.dynamic.dataservice.dynamic.query.criteria.predicate;

import com.taoswork.tallybook.dynamic.dataservice.dynamic.query.criteria.util.FieldPathBuilder;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/6/17.
 */
public interface PredicateProvider<T, Y> {
    Predicate buildPredicate(CriteriaBuilder builder, FieldPathBuilder fieldPathBuilder,
                             From root,
                             Class ceilingEntity, String fullPropertyName,
                             Path<T> explicitPath, List<Y> directValues);


}
