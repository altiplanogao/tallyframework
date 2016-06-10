package com.taoswork.tallybook.datadomain.onmongo.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;

import java.io.IOException;

/**
 * Created by Gao Yuan on 2016/3/20.
 */
public class ObjectIdJsonDeserializer extends StdDeserializer<ObjectId> {
    public ObjectIdJsonDeserializer() {
        super(ObjectId.class);
    }

    public ObjectIdJsonDeserializer(JavaType valueType) {
        super(valueType);
    }

    @Override
    public ObjectId deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonToken t = jp.getCurrentToken();
        String value;
        if (t == JsonToken.VALUE_STRING) {
            value = jp.getText();
        } else if (t == JsonToken.VALUE_NULL) {
            value = null; // since we have established that '_elementDeserializer == null' earlier
        } else {
            value = _parseString(jp, ctxt);
        }
        if(StringUtils.isNotEmpty(value)){
            return new ObjectId(value);
        }

        return null;
    }
}
