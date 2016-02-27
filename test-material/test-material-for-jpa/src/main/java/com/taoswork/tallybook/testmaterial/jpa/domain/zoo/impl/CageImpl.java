package com.taoswork.tallybook.testmaterial.jpa.domain.zoo.impl;

import com.taoswork.tallybook.datadomain.base.presentation.PresentationField;
import com.taoswork.tallybook.testmaterial.jpa.domain.zoo.Cage;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CAGE")
public class CageImpl implements Cage {
    protected static final String ID_GENERATOR_NAME = "CageImpl_IdGen";

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = ID_GENERATOR_NAME)
    @TableGenerator(
            name = ID_GENERATOR_NAME,
            table = "ID_GENERATOR_TABLE",
            initialValue = 1)
    @Column(name = "ID")
    @PresentationField(group = "General")
    protected Long id;

    @Column(name = "SN")
    private String sn;

    @Temporal(TemporalType.DATE)
    private Date createDate;


//    private Animal[] currentTenants;

//    private List<>

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public CageImpl setId(Long id) {
        this.id = id;
        return this;
    }

    @Override
    public String getSn() {
        return sn;
    }

    @Override
    public CageImpl setSn(String sn) {
        this.sn = sn;
        return this;
    }

    @Override
    public Date getCreateDate() {
        return createDate;
    }

    @Override
    public CageImpl setCreateDate(Date createDate) {
        this.createDate = createDate;
        return this;
    }
}
