package com.taoswork.tallybook.authority.solution.domain.resource;

import com.taoswork.tallybook.authority.core.ProtectionMode;
import com.taoswork.tallybook.authority.solution.domain.resource.protection.ProtectionGate;
import com.taoswork.tallybook.authority.solution.domain.resource.protection.ProtectionValidator;
import com.taoswork.tallybook.authority.solution.domain.resource.protection.ResourceNameFieldGate;
import com.taoswork.tallybook.datadomain.base.entity.MapField;
import com.taoswork.tallybook.datadomain.base.entity.MapMode;
import com.taoswork.tallybook.datadomain.base.entity.PersistEntity;
import com.taoswork.tallybook.datadomain.base.entity.PersistField;
import com.taoswork.tallybook.datadomain.base.presentation.FieldType;
import com.taoswork.tallybook.datadomain.base.presentation.PresentationClass;
import com.taoswork.tallybook.datadomain.base.presentation.PresentationField;
import com.taoswork.tallybook.datadomain.base.presentation.Visibility;
import com.taoswork.tallybook.datadomain.base.presentation.typed.BooleanMode;
import com.taoswork.tallybook.datadomain.base.presentation.typed.PresentationBoolean;
import com.taoswork.tallybook.datadomain.base.presentation.typed.PresentationEnum;
import com.taoswork.tallybook.datadomain.onmongo.AbstractDocument;
import org.apache.commons.lang3.StringUtils;
import org.mongodb.morphia.annotations.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Gao Yuan on 2015/6/5.
 */
@Entity
@Indexes(
        {
                @Index(value = "protectionSpace", fields ={
                        @Field(Protection.FN_PROTECTION_SPACE)}),
                @Index(fields = @Field(Protection.FN_NAMESPACE)),
                @Index(value = "scopedResource", fields = {
                        @Field(Protection.FN_PROTECTION_SPACE),
                        @Field(Protection.FN_NAMESPACE),
                        @Field("resource")
                }, unique = true)
        }
)
@PersistEntity(asDefaultPermissionGuardian = true,
        validators = ProtectionValidator.class,
        valueGates = ProtectionGate.class
)
@PresentationClass()
public class Protection
        extends AbstractDocument {

    @PersistField(fieldType = FieldType.STRING, required = true)
    @PresentationField(order = 3, visibility = Visibility.HIDDEN_ALL)
    protected String protectionSpace;
    public static final String FN_PROTECTION_SPACE = "protectionSpace";

    private String namespace;
    public static final String FN_NAMESPACE = "namespace";

    @PersistField(fieldType = FieldType.NAME, required = true, length = 100)
    @PresentationField(order = 1)
    protected String name;

    @PersistField(fieldType = FieldType.STRING)
    @PresentationField(order = 2, visibility = Visibility.GRID_HIDE)
    protected String description;

    @Indexed
    @PersistField(fieldType = FieldType.STRING, required = true, fieldValueGateOverride= ResourceNameFieldGate.class)
    @PresentationField(order = 3)
    protected String resource;
    public static final String FN_RESOURCE_ENTITY = "resource";

    @PersistField(fieldType = FieldType.STRING)
    @PresentationField(order = 4)
    protected String category;

    @PersistField(fieldType = FieldType.ENUMERATION, required = true, length = 10)
    @PresentationField(order = 5)
    @PresentationEnum(enumeration = DProtectionMode.class)
    protected DProtectionMode protectionMode;

    @PersistField(fieldType = FieldType.BOOLEAN, required = true)
    @PresentationField(order = 6)
    @PresentationBoolean(mode = BooleanMode.YesNo)
    protected Boolean masterControlled = Boolean.TRUE;

    //key uses ProtectionCase's unique code
    @MapField(mode = MapMode.Entity, keyFieldOnValue = "uuid")
    private Map<String, ProtectionCase> cases = new HashMap<String, ProtectionCase>();

    @Version
    protected Long version = null;

    public String getProtectionSpace() {
        return protectionSpace;
    }

    public void setProtectionSpace(String protectionSpace) {
        this.protectionSpace = protectionSpace;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(Class resource) {
        if(null == resource)
            setResource("");
        setResource(resource.getName());
    }

    public void setResource(String resource) {
        this.resource = ResourceNameFieldGate.unifiedResourceName(resource);
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isMasterControlled() {
        return masterControlled;
    }

    public void setMasterControlled(Boolean masterControlled) {
        this.masterControlled = masterControlled;
    }

    public DProtectionMode getProtectionMode() {
        return protectionMode;
    }

    public void setProtectionMode(DProtectionMode protectionMode) {
        this.protectionMode = protectionMode;
    }

    public ProtectionCase getResourceCase(String caseCode) {
        return cases.get(caseCode);
    }

    public Map<String, ProtectionCase> getCases() {
        return cases;
    }

    public void addCase(ProtectionCase _case) {
        if(StringUtils.isBlank(_case.getUuid())){
            _case.setUuid(UUID.randomUUID().toString());
        }
        cases.put(_case.getUuid(), _case);
    }

    public void setCases(Map<String, ProtectionCase> cases) {
        this.cases = cases;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getVersion() {
        return version;
    }

    public static String unifiedResourceName(String resourceName){
        return ResourceNameFieldGate.unifiedResourceName(resourceName);
    }

    public static String unifiedResourceName(Class resource){
        return ResourceNameFieldGate.unifiedResourceName(resource.getName());
    }

}
