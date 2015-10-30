package com.taoswork.tallybook.dynamic.dataservice.core.dao.query.criteria.restriction;

import com.taoswork.tallybook.dynamic.dataservice.core.dao.query.criteria.converter.*;
import com.taoswork.tallybook.dynamic.dataservice.core.dao.query.criteria.predicate.*;

/**
 * Created by Gao Yuan on 2015/6/17.
 */
public class Restrictions {

    private static class Converters{
        public static final FilterValueConverter DoNothingConverter = new DoNothingValueConverter();
        public static final FilterValueConverter StringLikeConverter = new StringLikeFilterValueConverter();
        public static final FilterValueConverter EnumConverter = new EnumFilterValueConverter();
        public static final FilterValueConverter BooleanConverter = new BooleanFilterValueConverter();
        public static final FilterValueConverter LongConverter = new LongFilterValueConverter();
        public static final FilterValueConverter ForeignKeyConverter = new ForeignKeyFilterValueConverter();
    }

    private static class Predicates{
        public static final PredicateProvider StringLikeProvider = new StringLikePredicateProvider();
        public static final PredicateProvider StringEqualProvider = new StringEqualPredicateProvider();
        public static final PredicateProvider EnumEqualProvider = new EnumEqualPredicateProvider();
        public static final PredicateProvider BooleanEqualProvider = new BooleanEqualPredicateProvider();
        public static final PredicateProvider CommonEqualProvider = new CommonEqualPredicateProvider();
        public static final PredicateProvider ForeignKeyEqualProvider = new ForeignKeyEqualPredicateProvider();
        public static final PredicateProvider ExternalForeignKeyEqualProvider = new ExternalForeignKeyEqualPredicateProvider();
    }

    public static final Restriction StringEqualRestriction = new Restriction(
        Converters.DoNothingConverter,
        Predicates.StringEqualProvider);

    public static final Restriction StringLikeRestriction = new Restriction(
        Converters.StringLikeConverter,
        Predicates.StringLikeProvider);

    public static final Restriction EnumRestriction = new Restriction(
        Converters.EnumConverter,
        Predicates.EnumEqualProvider);

    public static final Restriction BooleanRestriction = new Restriction(
        Converters.BooleanConverter,
        Predicates.BooleanEqualProvider);

    public static final Restriction LongRestriction = new Restriction(
        Converters.LongConverter,
        Predicates.CommonEqualProvider);

    public static final Restriction ForeignKeyRestriction = new Restriction(
        Converters.ForeignKeyConverter,
        Predicates.ForeignKeyEqualProvider);

    public static final Restriction ExternalForeignKeyRestriction = new Restriction(
        Converters.ForeignKeyConverter,
        Predicates.ExternalForeignKeyEqualProvider);


}
