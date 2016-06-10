package com.taoswork.tallybook.dataservice.mongo.core.query.criteria.predicate;

import com.taoswork.tallybook.general.extension.collections.CollectionUtility;
import org.mongodb.morphia.query.Criteria;
import org.mongodb.morphia.query.Query;

import java.util.List;

public class BooleanEqualPredicateProvider implements PredicateProvider<Boolean> {
    @Override
    public <T> Criteria buildPredicate(Query<T> query, String path, List<Boolean> directValues) {
        if (CollectionUtility.isEmpty(directValues)) {
            return null;
        }
        if (directValues.size() == 1) {
            return query.criteria(path).equal(directValues.get(0));
        }
        return null;
    }
}
