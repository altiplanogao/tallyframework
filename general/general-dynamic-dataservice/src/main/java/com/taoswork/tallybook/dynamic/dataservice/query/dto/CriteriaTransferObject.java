package com.taoswork.tallybook.dynamic.dataservice.query.dto;

import java.util.*;

/**
 * Created by Gao Yuan on 2015/5/22.
 */
public class CriteriaTransferObject {
    private int firstResult;
    private int maxResultCount;

    private final Map<String, PropertyFilterCriteria> filterCriterias = new HashMap<String, PropertyFilterCriteria>();
    private final List<PropertySortCriteria> sortCriterias = new ArrayList<PropertySortCriteria>();

    public int getFirstResult() {
        return firstResult;
    }

    public void setFirstResult(int firstResult) {
        this.firstResult = firstResult;
    }

    public int getMaxResultCount() {
        return maxResultCount;
    }

    public void setMaxResultCount(int maxResults) {
        this.maxResultCount = maxResults;
    }

    public CriteriaTransferObject addFilterCriteria(PropertyFilterCriteria criteria){
        filterCriterias.put(criteria.getPropertyName(), criteria);
        return this;
    }

    public CriteriaTransferObject addFilterCriterias(Collection<PropertyFilterCriteria> criterias){
        for(PropertyFilterCriteria criteria : criterias){
            addFilterCriteria(criteria);
        }
        return this;
    }

    public CriteriaTransferObject clearFilterCriteria(){
        filterCriterias.clear();
        return this;
    }

    public Map<String, PropertyFilterCriteria> getFilterCriterias(){
        return Collections.unmodifiableMap(filterCriterias);
    }
    public Collection<PropertyFilterCriteria> getFilterCriteriasCollection(){
        return Collections.unmodifiableCollection(filterCriterias.values());
    }

    public PropertyFilterCriteria getFilterCriteria(String name) {
        PropertyFilterCriteria criteria = filterCriterias.getOrDefault(name, null);
        if(criteria == null){
            criteria = new PropertyFilterCriteria(name);
            this.addFilterCriteria(criteria);
        }
        return criteria;
    }

    public CriteriaTransferObject addSortCriteria(PropertySortCriteria criteria){
        sortCriterias.add(criteria);
        return this;
    }

    public CriteriaTransferObject addSortCriterias(Collection<PropertySortCriteria> criterias){
        for(PropertySortCriteria criteria : criterias){
            addSortCriteria(criteria);
        }
        return this;
    }

    public CriteriaTransferObject clearSortCriteria(){
        sortCriterias.clear();
        return this;
    }

    public List<PropertySortCriteria> getSortCriterias(){
        return Collections.unmodifiableList(sortCriterias);
    }

    public PropertySortCriteria getSortCriteria(String name) {
        for(PropertySortCriteria criteria : sortCriterias){
            if(name.equals(criteria.getPropertyName())){
                return criteria;
            }
        }
        PropertySortCriteria criteria = new PropertySortCriteria(name);
        sortCriterias.add(criteria);
        return criteria;
    }

}
