package com.taoswork.tallybook.dynamic.dataservice.core.dao.query.criteria.converter;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taoswork.tallybook.general.extension.utils.IFriendlyEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ForeignKeyFilterValueConverter implements FilterValueConverter<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ForeignKeyFilterValueConverter.class);

    private ObjectMapper objectMapper = new ObjectMapper();
    private static class IdAndName{
        public String id;
        public String name;
    }

    @Override
    public String convert(Class type, String stringValue) {
        try {
            IdAndName idAndName = objectMapper.readValue(stringValue, IdAndName.class);
            return idAndName.id;
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
