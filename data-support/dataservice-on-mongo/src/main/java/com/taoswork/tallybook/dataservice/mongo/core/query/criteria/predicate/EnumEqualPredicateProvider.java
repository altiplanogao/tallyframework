package com.taoswork.tallybook.dataservice.mongo.core.query.criteria.predicate;

import com.taoswork.tallybook.general.extension.collections.CollectionUtility;
import com.taoswork.tallybook.general.extension.utils.IFriendlyEnum;
import org.mongodb.morphia.query.Criteria;
import org.mongodb.morphia.query.Query;

import java.util.List;

/**
 * Created by Gao Yuan on 2015/6/17.
 */
public class EnumEqualPredicateProvider implements PredicateProvider<IFriendlyEnum> {
    @Override
    public <T> Criteria buildPredicate(Query<T> query, String path, List<IFriendlyEnum> directValues) {
        if (CollectionUtility.isEmpty(directValues)) {
            return null;
        }
        if (directValues.size() == 1) {
            return query.criteria(path).equal(directValues.get(0));
        } else {
            query.criteria(path).in(directValues);
        }
        return null;
    }
}
