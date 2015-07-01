package com.taoswork.tallybook.dynamic.dataservice.query.dto;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Gao Yuan on 2015/6/18.
 */
public class PropertyCriteriaTest {
    @Test
    public void testPropertyFilterCriteriaClone(){
        PropertyFilterCriteria filterCriteria = new PropertyFilterCriteria("aaa");
        filterCriteria.addFilterValue("abc").addFilterValue("def");

        try {
            PropertyFilterCriteria clone = filterCriteria.clone();
            Assert.assertTrue(clone != filterCriteria);
            Assert.assertTrue(filterCriteria.equals(clone));
        } catch (CloneNotSupportedException e){
            Assert.fail();
        }
    }

    @Test
    public void testPropertySortCriteriaClone(){
        PropertySortCriteria criteria = new PropertySortCriteria("aaa");
        criteria.setSortDirection(SortDirection.ASCENDING);

        try {
            PropertySortCriteria clone = criteria.clone();
            Assert.assertTrue(clone != criteria);
            Assert.assertTrue(criteria.equals(clone));
        } catch (CloneNotSupportedException e){
            Assert.fail();
        }
    }

    @Test
    public void testCTO(){
        PropertySortCriteria sortCriteria = new PropertySortCriteria("aaa");
        sortCriteria.setSortDirection(SortDirection.ASCENDING);

        PropertyFilterCriteria filterCriteria = new PropertyFilterCriteria("aaa");
        filterCriteria.addFilterValue("abc").addFilterValue("def");

        CriteriaTransferObject cto = new CriteriaTransferObject();
        cto.setFirstResult(111).setPageSize(123)
                .addFilterCriteria(filterCriteria)
                .addSortCriteria(sortCriteria);

        try {
            CriteriaTransferObject ctoClone = cto.clone();
            Assert.assertTrue(cto != ctoClone);
            Assert.assertEquals(cto, ctoClone);
       } catch (CloneNotSupportedException e){
            Assert.fail();
        }

    }
}
