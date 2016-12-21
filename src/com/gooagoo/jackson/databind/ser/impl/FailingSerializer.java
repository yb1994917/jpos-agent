package com.gooagoo.jackson.databind.ser.impl;

import java.io.IOException;
import java.lang.reflect.Type;

import com.gooagoo.jackson.core.*;
import com.gooagoo.jackson.databind.JavaType;
import com.gooagoo.jackson.databind.JsonMappingException;
import com.gooagoo.jackson.databind.JsonNode;
import com.gooagoo.jackson.databind.SerializerProvider;
import com.gooagoo.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.gooagoo.jackson.databind.ser.std.StdSerializer;

/**
 * Special bogus "serializer" that will throw
 * {@link JsonGenerationException} if its {@link #serialize}
 * gets invoked. Most commonly registered as handler for unknown types,
 * as well as for catching unintended usage (like trying to use null
 * as Map/Object key).
 */
@SuppressWarnings("serial")
public class FailingSerializer
    extends StdSerializer<Object>
{
    protected final String _msg;
    
    public FailingSerializer(String msg) {
        super(Object.class);
        _msg = msg;
    }
    
    @Override
    public void serialize(Object value, JsonGenerator g, SerializerProvider provider) throws IOException
    {
        throw new JsonGenerationException(_msg, g);
    }

    @Override
    public JsonNode getSchema(SerializerProvider provider, Type typeHint) throws JsonMappingException {
        return null;
    }
    
    @Override
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint)
    {
        ;
    }
}
