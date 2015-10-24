package com.taoswork.tallybook.dynamic.dataservice.core.dao.query.criteria.predicate;

import com.taoswork.tallybook.dynamic.dataservice.core.dao.query.criteria.util.FieldPathBuilder;
import com.taoswork.tallybook.general.extension.collections.CollectionUtility;

import javax.persistence.criteria.*;
import java.util.List;

public class BooleanEqualPredicateProvider implements PredicateProvider {
    @Override
    public Predicate buildPredicate(CriteriaBuilder builder, FieldPathBuilder fieldPathBuilder,
                                    From root,
                                    Class ceilingEntity, String fullPropertyName,
                                    Path explicitPath, List directValues) {
        if (CollectionUtility.isEmpty(directValues)) {
            return null;
        }
        final Expression<Boolean> path;
        if (explicitPath != null) {
            path = explicitPath;
        } else {
            path = fieldPathBuilder.buildPath(root, fullPropertyName);//.getPath(root, fullPropertyName, builder);
        }
        if (directValues.size() == 1) {
            return builder.equal(path, directValues.get(0));
        }
        return null;
    }
}
