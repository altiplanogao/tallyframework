package com.taoswork.tallybook.authority.solution.engine.filter;

import org.mongodb.morphia.query.Criteria;
import org.mongodb.morphia.query.Query;

/**
 * Created by Gao Yuan on 2016/2/26.
 */
public interface IMongoFilter {
    <T> Criteria makeMorphiaCriteria(Query<T> query);

    <T> Criteria makeMorphiaUnmatchCriteria(Query<T> query);
}
