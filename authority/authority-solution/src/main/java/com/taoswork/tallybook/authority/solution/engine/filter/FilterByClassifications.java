package com.taoswork.tallybook.authority.solution.engine.filter;

import com.taoswork.tallybook.authority.solution.domain.IClassifiable;
import org.apache.commons.lang3.StringUtils;
import org.mongodb.morphia.query.Criteria;
import org.mongodb.morphia.query.Query;

import java.util.List;

/**
 * Created by Gao Yuan on 2016/2/10.
 */
public class FilterByClassifications
        extends BaseFilter
        implements IMongoFilter {
    private String expectedClassification;
    private boolean valid = true;

    public FilterByClassifications(){

    }

    @Override
    protected void doAnalyzeFilterParameter(String parameter) {
        valid = false;
        if(!StringUtils.isBlank(parameter)){
            expectedClassification = parameter;
            valid = true;
        }else {
            valid = false;
            throw new IllegalArgumentException(FilterByClassifications.class.getName());
        }
    }

    @Override
    public boolean isMatch(Object entity) {
        if (null == entity) {
            return false;
        }
        if(!(entity instanceof IClassifiable)){
            return false;
        }
        IClassifiable classifiable = (IClassifiable) entity;
        if(classifiable == null)
            return false;
        if(!valid)
            return false;
        List<String> classifications = classifiable.getClassifications();
        if(classifications == null || classifications.isEmpty()){
            return false;
        }
        return classifications.contains(expectedClassification);
    }

    @Override
    public <T> Criteria makeMorphiaCriteria(Query<T> query) {
        Criteria criteria = query
                .criteria(IClassifiable.FN_CLASSIFICATIONS)
                .contains(expectedClassification);
        return criteria;
    }

    @Override
    public <T> Criteria makeMorphiaUnmatchCriteria(Query<T> query) {
        Criteria criteria = query
                .criteria(IClassifiable.FN_CLASSIFICATIONS)
                .not()
                .contains(expectedClassification);
        return criteria;
    }
}
