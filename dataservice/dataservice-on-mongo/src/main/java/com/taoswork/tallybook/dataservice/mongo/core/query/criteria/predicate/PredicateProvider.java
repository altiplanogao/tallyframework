package com.taoswork.tallybook.dataservice.mongo.core.query.criteria.predicate;

import org.mongodb.morphia.query.Criteria;
import org.mongodb.morphia.query.Query;

import java.util.List;


/**
 * Created by Gao Yuan on 2016/2/17.
 */
public interface PredicateProvider<V> {
    <T> Criteria buildPredicate(Query<T> query, String path, List<V> directValues);
    //Criteria buildPredicate(Query q, );
}
