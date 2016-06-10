package com.taoswork.tallybook.dataservice.jpa.core.dao.query.criteria.predicate;

import com.taoswork.tallybook.general.extension.collections.CollectionUtility;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class ForeignKeyEqualPredicateProvider implements PredicateProvider {
    @Override
    public Predicate buildPredicate(CriteriaBuilder builder,
                                    Class ceilingEntity, String fullPropertyName,
                                    Path explicitPath, List directValues) {
        if (CollectionUtility.isEmpty(directValues)) {
            return null;
        }
        final Path<String> path = explicitPath;
        if (directValues.size() == 0) {
            return null;
        } else if (directValues.size() == 1) {
            return builder.equal(path, directValues.get(0));
        } else {
            List<Predicate> predicates = new ArrayList<Predicate>();
            for (Object directVal : directValues) {
                Predicate predicate = builder.equal(path, directVal);
                predicates.add(predicate);
            }
            return builder.or(predicates.toArray(new Predicate[predicates.size()]));
        }
    }
}
