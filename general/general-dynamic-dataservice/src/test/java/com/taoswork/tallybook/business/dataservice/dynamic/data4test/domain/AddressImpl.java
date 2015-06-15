package com.taoswork.tallybook.business.dataservice.dynamic.data4test.domain;

import javax.persistence.Embeddable;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
@Embeddable
public class AddressImpl {
    String city;
    String street;
    String zipCode;
}
