package com.taoswork.tallybook.dynamic.dataservice.query.criteria.restriction;

import com.taoswork.tallybook.dynamic.datadomain.presentation.client.SupportedFieldType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/6/17.
 */
public class RestrictionFactory {
    private static RestrictionFactory _instance = new RestrictionFactory();

    private RestrictionFactory(){
    }

    public Restriction getRestriction(SupportedFieldType fieldType){
        switch (fieldType){
            case BOOLEAN:
                break;
            case INTEGER:
                break;
            case DATE:
                break;
            default:
                return Restrictions.StringLikeRestriction;
                //break;
        }
        return null;
    }

    public static RestrictionFactory instance() {
        return _instance;
    }
}
