package com.taoswork.tallybook.dataservice.jpa.core.dao.query.criteria.predicate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/6/17.
 */
public interface PredicateProvider<T, Y> {
    Predicate buildPredicate(CriteriaBuilder builder,
                             Class ceilingEntity, String fullPropertyName,
                             Path<T> explicitPath, List<Y> directValues);


}
