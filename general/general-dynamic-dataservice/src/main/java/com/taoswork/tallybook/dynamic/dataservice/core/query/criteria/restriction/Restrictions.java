package com.taoswork.tallybook.dynamic.dataservice.core.query.criteria.restriction;

import com.taoswork.tallybook.dynamic.dataservice.core.query.criteria.converter.*;
import com.taoswork.tallybook.dynamic.dataservice.core.query.criteria.predicate.BooleanEqualPredicateProvider;
import com.taoswork.tallybook.dynamic.dataservice.core.query.criteria.predicate.StringEqualPredicateProvider;
import com.taoswork.tallybook.dynamic.dataservice.core.query.criteria.predicate.PredicateProvider;
import com.taoswork.tallybook.dynamic.dataservice.core.query.criteria.predicate.StringLikePredicateProvider;

/**
 * Created by Gao Yuan on 2015/6/17.
 */
public class Restrictions {

    private static class Converters{
        public static final FilterValueConverter DoNothingConverter = new DoNothingValueConverter();
        public static final FilterValueConverter StringLikeConverter = new StringLikeFilterValueConverter();
        public static final FilterValueConverter EnumConverter = new EnumFilterValueConverter();
        public static final FilterValueConverter BooleanConverter = new BooleanFilterValueConverter();
    }

    private static class Predicates{
        public static final PredicateProvider StringLikeProvider = new StringLikePredicateProvider();
        public static final PredicateProvider StringEqualProvider = new StringEqualPredicateProvider();
        public static final PredicateProvider BooleanEqualProvider = new BooleanEqualPredicateProvider();
    }

    public static final Restriction StringLikeRestriction = new Restriction(
        Converters.StringLikeConverter,
        Predicates.StringLikeProvider);

    public static final Restriction EnumRestriction = new Restriction(
        Converters.EnumConverter,
        Predicates.StringEqualProvider);

    public static final Restriction BooleanRestriction = new Restriction(
        Converters.BooleanConverter,
        Predicates.BooleanEqualProvider);
}
