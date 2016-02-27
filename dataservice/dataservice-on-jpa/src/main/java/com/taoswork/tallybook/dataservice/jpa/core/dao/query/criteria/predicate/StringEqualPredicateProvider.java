package com.taoswork.tallybook.dataservice.jpa.core.dao.query.criteria.predicate;

import com.taoswork.tallybook.general.extension.collections.CollectionUtility;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/6/17.
 */
public class StringEqualPredicateProvider implements PredicateProvider<String, String> {
    @Override
    public Predicate buildPredicate(CriteriaBuilder builder,
                                    Class ceilingEntity, String fullPropertyName,
                                    Path<String> explicitPath, List<String> directValues) {
        if (CollectionUtility.isEmpty(directValues)) {
            return null;
        }
        final Path<String> path = explicitPath;
        if (directValues.size() == 1) {
            return builder.equal(builder.lower(path), directValues.get(0));
        } else {
            List<Predicate> predicates = new ArrayList<Predicate>();
            for (String directVal : directValues) {
                Predicate predicate = builder.equal(builder.lower(path), directVal);
                predicates.add(predicate);
            }
            return builder.or(predicates.toArray(new Predicate[]{}));
        }
    }
}
