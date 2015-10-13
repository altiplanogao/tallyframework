package com.taoswork.tallybook.dynamic.dataservice.core.dao.query.criteria.predicate;

import com.taoswork.tallybook.dynamic.dataservice.core.dao.query.criteria.util.FieldPathBuilder;
import com.taoswork.tallybook.general.extension.collections.CollectionUtility;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.List;

public class BooleanEqualPredicateProvider implements PredicateProvider {
    @Override
    public Predicate buildPredicate(CriteriaBuilder builder, FieldPathBuilder fieldPathBuilder,
                                    From root,
                                    Class ceilingEntity, String fullPropertyName,
                                    Path explicitPath, List directValues) {
        Path<String> path;
        if (explicitPath != null) {
            path = explicitPath;
        } else {
            path = fieldPathBuilder.buildPath(root, fullPropertyName);//.getPath(root, fullPropertyName, builder);
        }
        if (CollectionUtility.isEmpty(directValues)) {
            return null;
        }
        if (directValues.size() == 1) {
            return builder.equal(builder.lower(path), directValues.get(0));
        }
        return null;
    }
}
