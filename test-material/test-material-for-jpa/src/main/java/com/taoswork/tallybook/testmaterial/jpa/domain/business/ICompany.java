package com.taoswork.tallybook.testmaterial.jpa.domain.business;

import com.taoswork.tallybook.datadomain.base.entity.PersistEntity;
import com.taoswork.tallybook.datadomain.base.entity.Persistable;
import com.taoswork.tallybook.testmaterial.jpa.domain.business.dataprotect.CompanyValidator;
import com.taoswork.tallybook.testmaterial.jpa.domain.business.dataprotect.CompanyGate;
import com.taoswork.tallybook.testmaterial.jpa.domain.business.enumtype.CompanyType;
import com.taoswork.tallybook.testmaterial.jpa.domain.common.Address;

import java.util.Date;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/9/21.
 */
@PersistEntity(
        validators = {CompanyValidator.class},
        valueGates = {CompanyGate.class}
)
public interface ICompany extends Persistable {
    Long getId();

    void setId(Long id);

    Long getAsset();

    void setAsset(Long asset);

    String getName();

    void setName(String name);

    String getDescription();

    void setDescription(String description);

    String getDescription2();

    void setDescription2(String description2);

    String getHandyMemo1();

    void setHandyMemo1(String handyMemo1);

    String getHandyMemo2();

    void setHandyMemo2(String handyMemo2);

    Date getCreationDate();

    void setCreationDate(Date creationDate);

    CompanyType getCompanyType();

    void setCompanyType(CompanyType companyType);

    boolean isLocked();

    void setLocked(boolean locked);

    Boolean isActive();

    void setActive(Boolean active);

    String getTaxCode();

    void setTaxCode(String taxCode);

    Long getAdminId();

    void setAdminId(Long adminId);

    List<String> getPublicProducts();

    void setPublicProducts(List<String> publicProducts);

    List<String> getPrivateProducts();

    void setPrivateProducts(List<String> privateProducts);

    String getEmail();

    void setEmail(String email);

    String getPhone();

    void setPhone(String phone);

    Address getAddress();

    void setAddress(Address address);
}
