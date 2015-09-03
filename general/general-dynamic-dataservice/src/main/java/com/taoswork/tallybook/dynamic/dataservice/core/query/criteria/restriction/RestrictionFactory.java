package com.taoswork.tallybook.dynamic.dataservice.core.query.criteria.restriction;

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

    public Restriction getRestriction(FieldType fieldType){
        switch (fieldType){
            case BOOLEAN:
                break;
            case INTEGER:
                break;
            case DATE:
                break;
            case ENUMERATION:
                return Restrictions.EnumRestriction;
            default:
                LOGGER.warn("Restriction for '{}' not found, use string for replacement.", fieldType);
                return Restrictions.StringLikeRestriction;
                //break;
        }
        return null;
    }

    public static RestrictionFactory instance() {
        return _instance;
    }
}
