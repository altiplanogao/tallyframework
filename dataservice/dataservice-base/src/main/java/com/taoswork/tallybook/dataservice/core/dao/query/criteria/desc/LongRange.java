package com.taoswork.tallybook.dataservice.core.dao.query.criteria.desc;

/**
 * Created by Gao Yuan on 2015/11/3.
 */
public class LongRange extends Range<Long> {
    public LongRange(boolean includeFrom, Long from, Long to, boolean includeTo) {
        super(includeFrom, from, to, includeTo);
    }

    public LongRange(String fromBracket, Long from, Long to, String toBracket) {
        super(fromBracket, from, to, toBracket);
    }

    protected LongRange(String input) {
        super(input);
    }

    @Override
    protected Long numberFromString(String str) {
        return Long.parseLong(str);
    }

    public static LongRange getInstanceFromString(String input) {
        try {
            return new LongRange(input);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
