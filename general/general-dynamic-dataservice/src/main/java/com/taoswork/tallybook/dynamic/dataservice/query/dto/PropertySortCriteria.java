package com.taoswork.tallybook.dynamic.dataservice.query.dto;

/**
 * Created by Gao Yuan on 2015/6/15.
 */
public class PropertySortCriteria extends PropertyCriteria{

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

    public void setSortAscending(Boolean sortAscending) {
        if(null == sortAscending){
            setSortDirection(null);
        }else {
            setSortDirection((sortAscending) ? SortDirection.ASCENDING : SortDirection.DESCENDING);
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
    public String toString() {
        return "SORT: " + propertyName +
                (SortDirection.ASCENDING.equals(sortDirection) ?
                        "_/ asc" :
                        "\\_ desc");
    }
}
