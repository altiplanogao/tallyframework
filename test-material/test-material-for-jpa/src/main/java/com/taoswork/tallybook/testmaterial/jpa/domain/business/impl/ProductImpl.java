package com.taoswork.tallybook.testmaterial.jpa.domain.business.impl;

import com.taoswork.tallybook.testmaterial.jpa.domain.TallyMockupDataDomain;
import com.taoswork.tallybook.testmaterial.jpa.domain.business.IProduct;

import javax.persistence.*;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
@Entity
@Table(name = "PRODUCT")
public class ProductImpl implements IProduct {
    protected static final String ID_GENERATOR_NAME = "ProductImpl_IdGen";

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = ID_GENERATOR_NAME)
    @TableGenerator(
            name = ID_GENERATOR_NAME,
            table = TallyMockupDataDomain.ID_GENERATOR_TABLE_NAME,
            initialValue = 0)
    @Column(name = "ID")
    private int id;
    private String name;

    private Double price;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Double getPrice() {
        return price;
    }

    @Override
    public void setPrice(Double price) {
        this.price = price;
    }
}
