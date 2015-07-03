package com.taoswork.tallybook.dynamic.dataservice.query.criteria.restriction;

import com.taoswork.tallybook.dynamic.dataservice.query.criteria.predicate.PredicateProvider;
import com.taoswork.tallybook.dynamic.dataservice.query.criteria.converter.FilterValueConverter;

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

    public List<Object> convertValues(List<String> valueStrings) {
        List<Object> vals = new ArrayList<Object>();
        for(String valString : valueStrings){
            Object val = filterValueConverter.convert(valString);
            vals.add(val);
        }
        return vals;
    }
}