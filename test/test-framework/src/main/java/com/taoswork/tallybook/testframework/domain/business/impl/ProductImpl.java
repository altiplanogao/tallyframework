package com.taoswork.tallybook.testframework.domain.business.impl;

import com.taoswork.tallybook.testframework.domain.business.IProduct;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
@Entity
@Table(name = "PRODUCT")
public class ProductImpl implements IProduct{
    @Id
    private int id;
    private String name;

    private Double price;
}
