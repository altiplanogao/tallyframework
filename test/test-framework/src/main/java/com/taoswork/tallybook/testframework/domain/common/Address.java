package com.taoswork.tallybook.testframework.domain.common;

import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationField;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.Visibility;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Access(AccessType.FIELD)
public class Address {
    @PresentationField(visibility = Visibility.GRID_HIDE)
    private String street;

    private String city;

    private String state;

    @Column(name="ZIP_CODE")
    private String zip;
// ...
}