package com.taoswork.tallybook.testmaterial.jpa.domain.business.embed;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class EmployeeNameX {
    @Column(name = "F_NAME_X")
    private String first_Name;
    @Column(name = "L_NAME_X")
    private String last_Name;
// ...
}