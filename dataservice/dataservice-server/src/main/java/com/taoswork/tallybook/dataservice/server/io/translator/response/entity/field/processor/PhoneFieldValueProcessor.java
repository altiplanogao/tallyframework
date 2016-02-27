package com.taoswork.tallybook.dataservice.server.io.translator.response.entity.field.processor;

import com.taoswork.tallybook.dataservice.server.io.translator.response.entity.field.IFieldValueProcessor;

/**
 * Created by Gao Yuan on 2015/6/3.
 */
public class PhoneFieldValueProcessor implements IFieldValueProcessor {
    @Override
    public String getStringValue(Object value) {
        String phone = value.toString();
        int phoneLen = phone.length();
        if (phoneLen <= 4) {
            return phone;
        }
        int start = 0;
        int end = phoneLen % 4;
        if (end == 0) {
            end = 4;
        }
        StringBuilder sb = new StringBuilder();
        do {
            if (end > phoneLen) {
                break;
            }
            sb.append(phone.substring(start, end));
            sb.append(" ");
            start = end;
            end += 4;
        } while (true);
        return sb.toString();
    }
}
