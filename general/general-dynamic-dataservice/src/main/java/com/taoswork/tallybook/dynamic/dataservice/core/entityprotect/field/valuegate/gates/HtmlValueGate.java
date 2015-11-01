package com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.field.valuegate.gates;

import com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.field.valuegate.TypedFieldValueGateBase;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;
import net.sf.xsshtmlfilter.HTMLFilter;
import org.apache.commons.lang3.StringUtils;
import org.owasp.validator.html.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;

//http://stackoverflow.com/questions/24723/best-regex-to-catch-xss-cross-site-scripting-attack-in-java
//https://www.owasp.org/index.php/XSS_(Cross_Site_Scripting)_Prevention_Cheat_Sheet
//https://www.owasp.org/index.php/Category:OWASP_AntiSamy_Project
public class HtmlValueGate extends TypedFieldValueGateBase<String> {
    private final static Logger LOGGER = LoggerFactory.getLogger(HtmlValueGate.class);

    //File from antisamy-sample-configs.jar
    private final static String ANTISAMY_POLICY_FILE = "antisamy-myspace.xml";
    private static Policy policy;

    static {
        URL policyResource = HtmlValueGate.class.getClassLoader().getResource(ANTISAMY_POLICY_FILE);
        try {
            policy = Policy.getInstance(policyResource.openStream());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (PolicyException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private ThreadLocal<HTMLFilter> filterThreadLocal = new ThreadLocal<HTMLFilter>() {
        @Override
        protected HTMLFilter initialValue() {
            return new HTMLFilter();
        }
    };

    public HtmlValueGate() {
    }

    @Override
    public FieldType supportedFieldType() {
        return FieldType.HTML;
    }

    @Override
    public Class supportedFieldClass() {
        return String.class;
    }

    @Override
    protected String doStore(String val, String oldVal) {
        if (StringUtils.isEmpty(val))
            return val;
        AntiSamy antiSamy = new AntiSamy();
        try {
            CleanResults cr = antiSamy.scan(val, policy);
            return cr.getCleanHTML();
        } catch (PolicyException e) {
            e.printStackTrace();
        } catch (ScanException e) {
            e.printStackTrace();
        }

        //fallback
        HTMLFilter filter = filterThreadLocal.get();
        String clean = filter.filter(val);
        return clean;
    }
}
