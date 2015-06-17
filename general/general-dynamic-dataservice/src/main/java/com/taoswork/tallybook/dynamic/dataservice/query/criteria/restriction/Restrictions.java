package com.taoswork.tallybook.dynamic.dataservice.query.criteria.restriction;

import com.taoswork.tallybook.dynamic.dataservice.query.criteria.converter.FilterValueConverter;
import com.taoswork.tallybook.dynamic.dataservice.query.criteria.converter.StringLikeFilterValueConverter;
import com.taoswork.tallybook.dynamic.dataservice.query.criteria.predicate.PredicateProvider;
import com.taoswork.tallybook.dynamic.dataservice.query.criteria.predicate.StringLikePredicateProvider;

/**
 * Created by Gao Yuan on 2015/6/17.
 */
public class Restrictions {

    private static class Converters{
        public static final FilterValueConverter StringLikeFilterValueConverter = new StringLikeFilterValueConverter();
    }

    private static class Predicates{
        public static final PredicateProvider StringLikePredicateProvider = new StringLikePredicateProvider();
    }

    public static final Restriction StringLikeRestriction = new Restriction(
            Converters.StringLikeFilterValueConverter, Predicates.StringLikePredicateProvider);
}
