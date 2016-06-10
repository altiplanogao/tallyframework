package com.taoswork.tallybook.dataservice.core.dao.query.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.*;

/**
 * Created by Gao Yuan on 2015/5/22.
 */
public class CriteriaTransferObject implements Cloneable {
    public static final int SINGLE_QUERY_DEFAULT_PAGE_SIZE = 50;
    public static final int SINGLE_QUERY_MAX_PAGE_SIZE_ALLOWED = 200;

    private long firstResult;
    private int pageSize = SINGLE_QUERY_DEFAULT_PAGE_SIZE;

    private Map<String, PropertyFilterCriteria> filterCriterias = new HashMap<String, PropertyFilterCriteria>();
    private List<PropertySortCriteria> sortCriterias = new ArrayList<PropertySortCriteria>();

    public CriteriaTransferObject() {
        firstResult = 0;
    }

    public CriteriaTransferObject(String propertyName, String filterValue) {
        this();
        addFilterCriteria(propertyName, filterValue);
    }

    public long getFirstResult() {
        return firstResult;
    }

    public CriteriaTransferObject setFirstResult(long firstResult) {
        if (firstResult >= 0) {
            this.firstResult = firstResult;
        }
        return this;
    }

    public int getPageSize() {
        return pageSize;
    }

    public CriteriaTransferObject setPageSize(int maxResults) {
        if (maxResults > SINGLE_QUERY_MAX_PAGE_SIZE_ALLOWED) {
            maxResults = SINGLE_QUERY_MAX_PAGE_SIZE_ALLOWED;
        }
        if (maxResults > 0) {
            this.pageSize = maxResults;
        }
        return this;
    }

    public CriteriaTransferObject addFilterCriteria(PropertyFilterCriteria criteria) {
        filterCriterias.put(criteria.getPropertyName(), criteria);
        return this;
    }

    public CriteriaTransferObject addFilterCriteria(String propertyName, String filterValue) {
        PropertyFilterCriteria criteria = new PropertyFilterCriteria(propertyName, filterValue);
        addFilterCriteria(criteria);
        return this;
    }

    public CriteriaTransferObject addFilterCriterias(Collection<PropertyFilterCriteria> criterias) {
        for (PropertyFilterCriteria criteria : criterias) {
            addFilterCriteria(criteria);
        }
        return this;
    }

    public CriteriaTransferObject clearFilterCriteria() {
        filterCriterias.clear();
        return this;
    }

    public Map<String, PropertyFilterCriteria> getFilterCriterias() {
        return Collections.unmodifiableMap(filterCriterias);
    }

    public Collection<PropertyFilterCriteria> getFilterCriteriasCollection() {
        return Collections.unmodifiableCollection(filterCriterias.values());
    }

    public PropertyFilterCriteria getFilterCriteria(String name) {
        PropertyFilterCriteria criteria = filterCriterias.get(name);
        if (criteria == null) {
            criteria = new PropertyFilterCriteria(name);
            this.addFilterCriteria(criteria);
        }
        return criteria;
    }

    public CriteriaTransferObject addSortCriteria(PropertySortCriteria criteria) {
        sortCriterias.add(criteria);
        return this;
    }

    public CriteriaTransferObject addSortCriterias(Collection<PropertySortCriteria> criterias) {
        for (PropertySortCriteria criteria : criterias) {
            addSortCriteria(criteria);
        }
        return this;
    }

    public CriteriaTransferObject clearSortCriteria() {
        sortCriterias.clear();
        return this;
    }

    public List<PropertySortCriteria> getSortCriterias() {
        return Collections.unmodifiableList(sortCriterias);
    }

    public PropertySortCriteria getSortCriteria(String name) {
        for (PropertySortCriteria criteria : sortCriterias) {
            if (name.equals(criteria.getPropertyName())) {
                return criteria;
            }
        }
        PropertySortCriteria criteria = new PropertySortCriteria(name);
        sortCriterias.add(criteria);
        return criteria;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;

        if (!(o instanceof CriteriaTransferObject)) return false;

        CriteriaTransferObject that = (CriteriaTransferObject) o;

        return new EqualsBuilder()
                .append(firstResult, that.firstResult)
                .append(pageSize, that.pageSize)
                .append(filterCriterias, that.filterCriterias)
                .append(sortCriterias, that.sortCriterias)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(firstResult)
                .append(pageSize)
                .append(filterCriterias)
                .append(sortCriterias)
                .toHashCode();
    }

    @Override
    public CriteriaTransferObject clone() throws CloneNotSupportedException {
        CriteriaTransferObject cto = (CriteriaTransferObject) super.clone();
        cto.filterCriterias = new HashMap<String, PropertyFilterCriteria>();
        cto.filterCriterias.putAll(filterCriterias);

        cto.sortCriterias = new ArrayList<PropertySortCriteria>();
        cto.sortCriterias.addAll(sortCriterias);
        return cto;
    }
}
