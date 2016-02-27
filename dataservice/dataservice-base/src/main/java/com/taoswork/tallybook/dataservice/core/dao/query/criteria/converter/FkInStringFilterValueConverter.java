package com.taoswork.tallybook.dataservice.core.dao.query.criteria.converter;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;

public class FkInStringFilterValueConverter implements FilterValueConverter<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FkInStringFilterValueConverter.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    private static class IdAndName implements Serializable {
        public String id;
        public String name;
    }

    @Override
    public String convert(Class type, String stringValue) {
        if (stringValue == null)
            return null;
        try {
            if (stringValue.startsWith("{")) {
                IdAndName idAndName = objectMapper.readValue(stringValue, IdAndName.class);
                return idAndName.id;
            } else {
                return stringValue;
            }
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
