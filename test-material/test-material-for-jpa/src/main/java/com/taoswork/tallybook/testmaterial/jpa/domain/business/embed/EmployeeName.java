package com.taoswork.tallybook.testmaterial.jpa.domain.business.embed;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class EmployeeName {
    @Column(name = "F_NAME", insertable = false, updatable = false)
    private String first_Name;
    @Column(name = "L_NAME", insertable = false, updatable = false)
    private String last_Name;
// ...
}