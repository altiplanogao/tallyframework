package com.taoswork.tallybook.dataservice.mongo.core.query.criteria.restriction;

import com.taoswork.tallybook.datadomain.base.presentation.FieldType;
import com.taoswork.tallybook.general.solution.exception.UnImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Gao Yuan on 2015/6/17.
 */
public class RestrictionFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestrictionFactory.class);

    private static RestrictionFactory _instance = new RestrictionFactory();

    private RestrictionFactory() {
    }

    public Restriction getRestriction(FieldType fieldType, Class javaType) {
        switch (fieldType) {
            case ID:
                return Restrictions.ObjectIdRestriction;
            case NAME:
                return Restrictions.StringLikeRestriction;
            case BOOLEAN:
                return Restrictions.BooleanRestriction;
            case INTEGER:
                return Restrictions.IntRestriction;
            case DATE:
                return Restrictions.DateRangeRestriction;
            case ENUMERATION:
                return Restrictions.EnumRestriction;
            case FOREIGN_KEY:
                if(Long.class.equals(javaType)){
                    return Restrictions.FkInLongRestriction;
                }else if(String.class.equals(javaType)){
                    return Restrictions.FkInStringRestriction;
                }else {
                    throw new UnImplementedException("Unhandled Foreign key type");
                }
//                return Restrictions.FkInStringRestriction;
            case EXTERNAL_FOREIGN_KEY:
                if(Long.class.equals(javaType)){
                    return Restrictions.ExternalFkInLongRestriction;
                }else if(String.class.equals(javaType)){
                    return Restrictions.ExternalFkInStringRestriction;
                }else {
                    throw new UnImplementedException("Unhandled Foreign key type");
                }
            default:
                if (Long.class.equals(javaType)) {
                    return Restrictions.LongRestriction;
                } else if (String.class.equals(javaType)) {
                    return Restrictions.StringLikeRestriction;
                } else {
                    LOGGER.warn("Restriction for '{}' not found, use string for replacement.", fieldType);
                    return Restrictions.StringEqualRestriction;
                }
                //break;
        }
//        return null;
    }

    public static RestrictionFactory instance() {
        return _instance;
    }
}
