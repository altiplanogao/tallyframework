package com.taoswork.tallybook.dataservice.core.dao.query.criteria.desc;

/**
 * Created by Gao Yuan on 2015/11/3.
 */
public class IntegerRange extends Range<Integer> {
    public IntegerRange(boolean includeFrom, Integer from, Integer to, boolean includeTo) {
        super(includeFrom, from, to, includeTo);
    }

    public IntegerRange(String fromBracket, Integer from, Integer to, String toBracket) {
        super(fromBracket, from, to, toBracket);
    }

    protected IntegerRange(String input) {
        super(input);
    }

    @Override
    protected Integer numberFromString(String str) {
        return Integer.parseInt(str);
    }

    public static IntegerRange getInstanceFromString(String input) {
        try {
            return new IntegerRange(input);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
