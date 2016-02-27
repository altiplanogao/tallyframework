package com.taoswork.tallybook.dataservice.jpa.core.dao.query.criteria.restriction;

import com.taoswork.tallybook.dataservice.core.dao.query.criteria.converter.FilterValueConverter;
import com.taoswork.tallybook.dataservice.jpa.core.dao.query.criteria.predicate.PredicateProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/6/17.
 */
public class Restriction {
    protected FilterValueConverter filterValueConverter;
    protected PredicateProvider predicateProvider;

    public Restriction(FilterValueConverter filterValueConverter, PredicateProvider predicateProvider) {
        this.filterValueConverter = filterValueConverter;
        this.predicateProvider = predicateProvider;
    }

    public FilterValueConverter getFilterValueConverter() {
        return filterValueConverter;
    }

    public Restriction setFilterValueConverter(FilterValueConverter filterValueConverter) {
        this.filterValueConverter = filterValueConverter;
        return this;
    }

    public PredicateProvider getPredicateProvider() {
        return predicateProvider;
    }

    public Restriction setPredicateProvider(PredicateProvider predicateProvider) {
        this.predicateProvider = predicateProvider;
        return this;
    }

    public List<Object> convertValues(Class type, List<String> valueStrings) {
        List<Object> vals = new ArrayList<Object>();
        for (String valString : valueStrings) {
            Object val = filterValueConverter.convert(type, valString);
            if (val != null) {
                vals.add(val);
            }
        }
        return vals;
    }
}
