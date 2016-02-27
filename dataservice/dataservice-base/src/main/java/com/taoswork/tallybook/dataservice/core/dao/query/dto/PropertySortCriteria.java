package com.taoswork.tallybook.dataservice.core.dao.query.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by Gao Yuan on 2015/6/15.
 */
public class PropertySortCriteria extends PropertyCriteria {

    protected SortDirection sortDirection;

    public PropertySortCriteria(String propertyName) {
        super(propertyName);
    }

    public PropertySortCriteria(String propertyName, SortDirection sortDirection) {
        super(propertyName);
        setSortDirection(sortDirection);
    }


    public Boolean getSortAscending() {
        return (sortDirection == null) ? null : SortDirection.ASCENDING.equals(sortDirection);
    }

    public PropertySortCriteria setSortAscending(Boolean sortAscending) {
        if (null == sortAscending) {
            return setSortDirection(null);
        } else {
            return setSortDirection((sortAscending) ? SortDirection.ASCENDING : SortDirection.DESCENDING);
        }
    }

    public SortDirection getSortDirection() {
        return sortDirection;
    }

    public PropertySortCriteria setSortDirection(SortDirection sortDirection) {
        this.sortDirection = sortDirection;
        return this;
    }

    @Override
    public PropertySortCriteria clone() throws CloneNotSupportedException {
        PropertySortCriteria criteria = (PropertySortCriteria) super.clone();
        criteria.setSortDirection(this.sortDirection);
        return criteria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof PropertySortCriteria)) return false;

        PropertySortCriteria criteria = (PropertySortCriteria) o;

        return new EqualsBuilder()
                .append(propertyName, criteria.propertyName)
                .append(sortDirection, criteria.sortDirection)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(propertyName)
                .append(sortDirection)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "SORT: " + propertyName +
                (SortDirection.ASCENDING.equals(sortDirection) ?
                        "_/ asc" :
                        "\\_ desc");
    }
}
