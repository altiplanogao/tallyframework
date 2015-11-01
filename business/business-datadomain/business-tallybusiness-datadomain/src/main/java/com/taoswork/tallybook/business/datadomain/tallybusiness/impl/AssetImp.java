package com.taoswork.tallybook.business.datadomain.tallybusiness.impl;


import com.taoswork.tallybook.business.datadomain.tallybusiness.Asset;
import com.taoswork.tallybook.business.datadomain.tallybusiness.BusinessUnit;
import com.taoswork.tallybook.business.datadomain.tallybusiness.WorkPlan;
import com.taoswork.tallybook.general.datadomain.support.entity.PersistField;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationField;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.Visibility;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/4/14.
 */
@Entity
@Table(name = "TB_ASSET")
public class AssetImp implements Asset {

    @Id
    @Column(name = "ID")
    @PresentationField(order = 1)
    protected Long id;

    @Column(name = "NAME", nullable = false)
    @PersistField(fieldType = FieldType.NAME)
    @PresentationField(order = 2)
    protected String name;

    @Column(name = "DESC")
    @PresentationField(order = 4, visibility = Visibility.GRID_HIDE)
    protected String description;

    protected List<WorkPlan> workPlans;

    //inverse controlled fields:
    protected BusinessUnit owner;


}
