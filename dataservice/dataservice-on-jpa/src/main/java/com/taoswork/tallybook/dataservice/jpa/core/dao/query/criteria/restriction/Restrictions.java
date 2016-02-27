package com.taoswork.tallybook.dataservice.jpa.core.dao.query.criteria.restriction;

import com.taoswork.tallybook.dataservice.core.dao.query.criteria.converter.*;
import com.taoswork.tallybook.dataservice.jpa.core.dao.query.criteria.converter.StringLikeFilterValueConverter;
import com.taoswork.tallybook.dataservice.jpa.core.dao.query.criteria.predicate.*;

/**
 * Created by Gao Yuan on 2015/6/17.
 */
public class Restrictions {

    private static class Converters {
        public static final FilterValueConverter DoNothingConverter = new DoNothingValueConverter();
        public static final FilterValueConverter StringLikeConverter = new StringLikeFilterValueConverter();
        public static final FilterValueConverter EnumConverter = new EnumFilterValueConverter();
        public static final FilterValueConverter BooleanConverter = new BooleanFilterValueConverter();
        public static final FilterValueConverter IntRConverter = new IntRFilterValueConverter();
        public static final FilterValueConverter LongRConverter = new LongRFilterValueConverter();
        public static final FilterValueConverter FkInLongConverter = new FkInLongFilterValueConverter();
        public static final FilterValueConverter FkInStringConverter = new FkInStringFilterValueConverter();
        public static final FilterValueConverter DateRConverter = LongRConverter;
    }

    private static class Predicates {
        public static final PredicateProvider StringLikeProvider = new StringLikePredicateProvider();
        public static final PredicateProvider StringEqualProvider = new StringEqualPredicateProvider();
        public static final PredicateProvider EnumEqualProvider = new EnumEqualPredicateProvider();
        public static final PredicateProvider BooleanEqualProvider = new BooleanEqualPredicateProvider();
        public static final PredicateProvider IntRProvider = new IntRPredicateProvider();
        public static final PredicateProvider LongRProvider = new LongRPredicateProvider();
        public static final PredicateProvider DateRProvider = new DateRPredicateProvider();
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
            Converters.LongRConverter,
            Predicates.LongRProvider);

    public static final Restriction IntRestriction = new Restriction(
            Converters.IntRConverter,
            Predicates.IntRProvider);

    public static final Restriction FkInLongRestriction = new Restriction(
            Converters.FkInLongConverter,
            Predicates.ForeignKeyEqualProvider);

    public static final Restriction FkInStringRestriction = new Restriction(
            Converters.FkInStringConverter,
            Predicates.ForeignKeyEqualProvider);

    public static final Restriction ExternalFkInLongRestriction = new Restriction(
            Converters.FkInLongConverter,
            Predicates.ExternalForeignKeyEqualProvider);

    public static final Restriction ExternalFkInStringRestriction = new Restriction(
            Converters.FkInStringConverter,
            Predicates.ExternalForeignKeyEqualProvider);

    public static final Restriction DateRangeRestriction = new Restriction(
            Converters.DateRConverter,
            Predicates.DateRProvider);

}
