package com.gooagoo.com.alibaba.fastjson.serializer;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import com.gooagoo.com.alibaba.fastjson.JSON;
import com.gooagoo.com.alibaba.fastjson.JSONException;
import com.gooagoo.com.alibaba.fastjson.parser.DefaultJSONParser;
import com.gooagoo.com.alibaba.fastjson.parser.JSONLexer;
import com.gooagoo.com.alibaba.fastjson.parser.JSONToken;
import com.gooagoo.com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;


public class CharArrayCodec implements ObjectDeserializer {

    @SuppressWarnings("unchecked")
    public <T> T deserialze(DefaultJSONParser parser, Type clazz, Object fieldName) {
        return (T) deserialze(parser);
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T deserialze(DefaultJSONParser parser) {
        final JSONLexer lexer = parser.lexer;
        if (lexer.token() == JSONToken.LITERAL_STRING) {
            String val = lexer.stringVal();
            lexer.nextToken(JSONToken.COMMA);
            return (T) val.toCharArray();
        }
        
        if (lexer.token() == JSONToken.LITERAL_INT) {
            Number val = lexer.integerValue();
            lexer.nextToken(JSONToken.COMMA);
            return (T) val.toString().toCharArray();
        }

        Object value = parser.parse();

        if (value instanceof  String) {
            return (T) ((String) value).toCharArray();
        }

        if (value instanceof Collection) {
            Collection<?> collection = (Collection) value;

            boolean accept = true;
            for (Object item : collection) {
                if (item instanceof String) {
                    int itemLength = ((String) item).length();
                    if (itemLength != 1) {
                        accept = false;
                        break;
                    }
                }
            }

            if (!accept) {
                throw new JSONException("can not cast to char[]");
            }
        }

        return value == null //
            ? null //
            : (T) JSON.toJSONString(value).toCharArray();
    }

    public int getFastMatchToken() {
        return JSONToken.LITERAL_STRING;
    }
}
