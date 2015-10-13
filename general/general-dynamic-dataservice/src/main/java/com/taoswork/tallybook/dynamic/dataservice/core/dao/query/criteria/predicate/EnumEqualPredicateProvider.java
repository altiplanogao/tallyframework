package com.taoswork.tallybook.dynamic.dataservice.core.dao.query.criteria.predicate;

import com.taoswork.tallybook.dynamic.dataservice.core.dao.query.criteria.util.FieldPathBuilder;
import com.taoswork.tallybook.general.extension.collections.CollectionUtility;
import com.taoswork.tallybook.general.extension.utils.IFriendlyEnum;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/6/17.
 */
public class EnumEqualPredicateProvider implements PredicateProvider<String, IFriendlyEnum> {
    @Override
    public Predicate buildPredicate(CriteriaBuilder builder, FieldPathBuilder fieldPathBuilder,
                                    From root,
                                    Class ceilingEntity, String fullPropertyName,
                                    Path<String> explicitPath, List<IFriendlyEnum> directValues) {
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
        } else {
            List<Predicate> predicates = new ArrayList<Predicate>();
            for (IFriendlyEnum directVal : directValues) {
                Predicate predicate = builder.equal(builder.lower(path), directVal);
                predicates.add(predicate);
            }
            return builder.or(predicates.toArray(new Predicate[]{}));
        }
    }
}
