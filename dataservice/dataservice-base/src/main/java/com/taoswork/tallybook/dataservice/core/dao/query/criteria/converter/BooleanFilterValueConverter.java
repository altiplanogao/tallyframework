package com.taoswork.tallybook.dataservice.core.dao.query.criteria.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class BooleanFilterValueConverter implements FilterValueConverter<Boolean> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BooleanFilterValueConverter.class);
    private static final Set<String> trues;
    private static final Set<String> falses;

    static {
        trues = new HashSet<String>();
        trues.add("true");
        trues.add("yes");
        trues.add("t");
        trues.add("y");

        falses = new HashSet<String>();
        falses.add("false");
        falses.add("no");
        falses.add("f");
        falses.add("n");
    }

    @Override
    public Boolean convert(Class type, String stringValue) {
        if (Boolean.class.isAssignableFrom(type) || boolean.class.equals(type)) {
            stringValue = stringValue.toLowerCase();
            if (trues.contains(stringValue)) {
                return true;
            } else if (falses.contains(stringValue)) {
                return false;
            }
            return null;
        } else {
            return null;
        }
    }
}
