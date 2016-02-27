package com.taoswork.tallybook.dataservice.mongo.core.query.criteria.predicate;

import com.taoswork.tallybook.general.extension.collections.CollectionUtility;
import org.mongodb.morphia.query.Criteria;
import org.mongodb.morphia.query.Query;

import java.util.ArrayList;
import java.util.List;

public class ExternalForeignKeyEqualPredicateProvider implements PredicateProvider<Object> {
    @Override
    public <T> Criteria buildPredicate(Query<T> query, String path, List<Object> directValues) {
        if (CollectionUtility.isEmpty(directValues)) {
            return null;
        }
        if (directValues.size() == 0) {
            return null;
        } else if (directValues.size() == 1) {
            return query.criteria(path).equal(directValues.get(0));
        } else {
            List<Criteria> predicates = new ArrayList<Criteria>();
            for (Object directVal : directValues) {
                Criteria predicate = query.criteria(path).equal(directVal);
                predicates.add(predicate);
            }
            return query.or(predicates.toArray(new Criteria[predicates.size()]));
        }
    }
}
