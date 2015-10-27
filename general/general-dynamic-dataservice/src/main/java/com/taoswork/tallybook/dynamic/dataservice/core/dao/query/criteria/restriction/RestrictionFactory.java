package com.taoswork.tallybook.dynamic.dataservice.core.dao.query.criteria.restriction;

import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Gao Yuan on 2015/6/17.
 */
public class RestrictionFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestrictionFactory.class);

    private static RestrictionFactory _instance = new RestrictionFactory();

    private RestrictionFactory(){
    }

    public Restriction getRestriction(FieldType fieldType, Class javaType){
        switch (fieldType) {
            case ID:
                return Restrictions.LongRestriction;
            case NAME:
                return Restrictions.StringLikeRestriction;
            case BOOLEAN:
                return Restrictions.BooleanRestriction;
            case INTEGER:
                break;
            case DATE:
                break;
            case ENUMERATION:
                return Restrictions.EnumRestriction;
            case FOREIGN_KEY:
                return Restrictions.ForeignKeyRestriction;
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
        return null;
    }

    public static RestrictionFactory instance() {
        return _instance;
    }
}
