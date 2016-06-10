package com.taoswork.tallybook.dataservice.core.dao.query.criteria.desc;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Gao Yuan on 2015/11/3.
 */
public abstract class Range<T> {
//    public final static String GREATER_THAN = "(";
//    public final static String GE_THAN = "[";
//    public final static String LESS_THAN = ")";
//    public final static String LE_THAN = "]";
//    public final static String SEPARATOR = ",";

    //Symbol could be used in url (possibly using json), so avoid using '[' ']', use 'R' and 'G' instead
    public final static String GREATER_THAN = "(";
    public final static String GE_THAN = "R";
    public final static String LESS_THAN = ")";
    public final static String LE_THAN = "G";

    public final static String SEPARATOR = "n";

    private final static String NUMBER_FORMAT = "(\\-{0,1}[0-9]*\\.{0,1}[0-9]*)";
    //    private final static String LEADING_BRACKET = "([\\(\\[]{1})";
//    private final static String ENDING_BRACKET = "([\\)\\]]{1})";
    private final static String LEADING_BRACKET = "([\\(R]{1})";
    private final static String ENDING_BRACKET = "([\\)G]{1})";
    private final static String SEPARATOR_RE = "n";
    private final static String RANGE_REGEX = "^"
            + LEADING_BRACKET + NUMBER_FORMAT + SEPARATOR_RE
            + NUMBER_FORMAT + ENDING_BRACKET + "$";
    private final static String RANGE_QUICK_REGEX = "^"
            + LEADING_BRACKET + "(\\S)*" + SEPARATOR_RE
            + "(\\S)*" + ENDING_BRACKET + "$";

    private final static Pattern RANGE_PATTERN = Pattern.compile(RANGE_REGEX);
    private final static Pattern RANGE_QUICK_PATTERN = Pattern.compile(RANGE_QUICK_REGEX);


    public final T from;
    public final T to;
    public final boolean includeFrom;
    public final boolean includeTo;

    public Range(boolean includeFrom, T from, T to, boolean includeTo) {
        this.from = from;
        this.to = to;
        this.includeFrom = includeFrom;
        this.includeTo = includeTo;
    }

    public Range(String fromBracket, T from, T to, String toBracket) {
        this.includeFrom = checkIncludeFrom(fromBracket);
        this.from = from;
        this.to = to;
        this.includeTo = checkIncludeTo(toBracket);
    }

    private boolean checkIncludeFrom(String fromBracket) {
        if (GREATER_THAN.equals(fromBracket)) {
            return false;
        } else if (GE_THAN.equals(fromBracket)) {
            return true;
        } else {
            throw new IllegalArgumentException();
        }
    }

    private boolean checkIncludeTo(String toBracket) {
        if (LESS_THAN.equals(toBracket)) {
            return false;
        } else if (LE_THAN.equals(toBracket)) {
            return true;
        } else {
            throw new IllegalArgumentException();
        }
    }

    protected Range(String input) {
        Matcher matcher = RANGE_PATTERN.matcher(input);
        if (matcher.find()) {
            int i = 1;
            String _fromB = matcher.group(i++);
            String _from = matcher.group(i++);
            String _to = matcher.group(i++);
            String _toB = matcher.group(i++);
            if (StringUtils.isEmpty(_from)) {
                this.from = null;
                this.includeFrom = false;
            } else {
                this.from = numberFromString(_from);
                this.includeFrom = checkIncludeFrom(_fromB);
            }
            if (StringUtils.isEmpty(_to)) {
                this.to = null;
                this.includeTo = false;
            } else {
                this.to = numberFromString(_to);
                this.includeTo = checkIncludeTo(_toB);
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    public static <T extends Range> T getRangeFromString(Class<T> rangeClass, String input) {
        try {
            Constructor cons = rangeClass.getConstructor(new Class[]{String.class});
            return (T) cons.newInstance(input);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected abstract T numberFromString(String str);

    public static boolean checkIsRange(String input, boolean quick) {
        Matcher matcher = (quick ? RANGE_QUICK_PATTERN : RANGE_PATTERN).matcher(input);
        if (matcher.find()) {
            return true;
        }
        return false;
    }
}