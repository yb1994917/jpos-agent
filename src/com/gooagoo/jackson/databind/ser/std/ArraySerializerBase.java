package com.gooagoo.jackson.databind.ser.std;

import java.io.IOException;

import com.gooagoo.jackson.annotation.JsonFormat;
import com.gooagoo.jackson.core.*;
import com.gooagoo.jackson.databind.*;
import com.gooagoo.jackson.databind.jsontype.TypeSerializer;
import com.gooagoo.jackson.databind.ser.*;

/**
 * Intermediate base class for serializers used for various
 * Java arrays.
 * 
 * @param <T> Type of arrays serializer handles
 */
@SuppressWarnings("serial")
public abstract class ArraySerializerBase<T>
    extends ContainerSerializer<T>
    implements ContextualSerializer // for 'unwrapSingleElemArray'
{
    protected final BeanProperty _property;

    /**
     * Setting for specific local override for "unwrap single element arrays":
     * true for enable unwrapping, false for preventing it, `null` for using
     * global configuration.
     *
     * @since 2.6
     */
    protected final Boolean _unwrapSingle;
    
    protected ArraySerializerBase(Class<T> cls)
    {
        super(cls);
        _property = null;
        _unwrapSingle = null;
    }

    /**
     * Use either variant that just takes type (non-contextual), or,
     * copy constructor that allows passing of property.
     *
     * @deprecated Since 2.6
     */
    @Deprecated
    protected ArraySerializerBase(Class<T> cls, BeanProperty property)
    {
        super(cls);
        _property = property;
        _unwrapSingle = null;
    }

    protected ArraySerializerBase(ArraySerializerBase<?> src)
    {
        super(src._handledType, false);
        _property = src._property;
        _unwrapSingle = src._unwrapSingle;
    }

    /**
     * @since 2.6
     */
    protected ArraySerializerBase(ArraySerializerBase<?> src, BeanProperty property,
            Boolean unwrapSingle)
    {
        super(src._handledType, false);
        _property = property;
        _unwrapSingle = unwrapSingle;
    }

    /**
     * @deprecated Since 2.6
     */
    @Deprecated
    protected ArraySerializerBase(ArraySerializerBase<?> src, BeanProperty property)
    {
        super(src._handledType, false);
        _property = property;
        _unwrapSingle = src._unwrapSingle;
    }

    /**
     * @since 2.6
     */
    public abstract JsonSerializer<?> _withResolved(BeanProperty prop,
            Boolean unwrapSingle);

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializers,
            BeanProperty property) throws JsonMappingException
    {
        Boolean unwrapSingle = null;

        // First: if we have a property, may have property-annotation overrides
        if (property != null) {
            JsonFormat.Value format = findFormatOverrides(serializers, property, handledType());
            if (format != null) {
                unwrapSingle = format.getFeature(JsonFormat.Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED);
                if (unwrapSingle != _unwrapSingle) {
                    return _withResolved(property, unwrapSingle);
                }
            }
        }
        return this;
    }
    
    // NOTE: as of 2.5, sub-classes SHOULD override (in 2.4 and before, was final),
    // at least if they can provide access to actual size of value and use `writeStartArray()`
    // variant that passes size of array to output, which is helpful with some data formats
    @Override
    public void serialize(T value, JsonGenerator gen, SerializerProvider provider) throws IOException
    {
        if (((_unwrapSingle == null) &&
                provider.isEnabled(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED))
                || (_unwrapSingle == Boolean.TRUE)) {
            if (hasSingleElement(value)) {
                serializeContents(value, gen, provider);
                return;
            }
        }
        gen.setCurrentValue(value);
        gen.writeStartArray();
        // [databind#631]: Assign current value, to be accessible by custom serializers
        serializeContents(value, gen, provider);
        gen.writeEndArray();
    }

    @Override
    public final void serializeWithType(T value, JsonGenerator gen, SerializerProvider provider,
            TypeSerializer typeSer)
        throws IOException
    {
        typeSer.writeTypePrefixForArray(value, gen);
        // [databind#631]: Assign current value, to be accessible by custom serializers
        gen.setCurrentValue(value);
        serializeContents(value, gen, provider);
        typeSer.writeTypeSuffixForArray(value, gen);
    }
    
    protected abstract void serializeContents(T value, JsonGenerator jgen, SerializerProvider provider)
        throws IOException;
}
