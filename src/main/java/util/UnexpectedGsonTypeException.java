package util;

import com.google.gson.*;
import de.trinext.framework.json.GsonPrimitiveTypeName;

/**
 * Thrown when a function expects a certain type of {@link JsonPrimitive}
 * buts receives something else.
 *
 * @author Dennis Woithe
 * @deprecated Gets removed when {@link com.google.gson} is not wrapped anymore.
 */
@Deprecated @SuppressWarnings("SerializableHasSerializationMethods")
public class UnexpectedGsonTypeException extends IllegalArgumentException {

    // ==== CONSTRUCTORS ===================================================== //

    /**
     * @param jElem The actual instance
     * @param expected one of {@link JsonObject},
     * * {@link JsonArray}, {@link JsonNull},
     * * {@link JsonPrimitive}
     */
    public UnexpectedGsonTypeException(JsonElement jElem, Class<? extends JsonElement> expected) {
        this(jElem, GsonHelper.gsonTypeName(expected));
    }

    /**
     * @param jElem The actual instance
     * @param expected one of {@link GsonPrimitiveTypeName#JSON_BOOLEAN_TYPE},
     * {@link GsonPrimitiveTypeName#JSON_STRING_TYPE}, {@link GsonPrimitiveTypeName#JSON_INTEGER_TYPE},
     * {@link GsonPrimitiveTypeName#JSON_DECIMAL_TYPE}
     */
    public UnexpectedGsonTypeException(JsonElement jElem, String expected) {
        super(jElem == null
              ? String.format("Expected %s but received null.", expected)
              : String.format("Expected %s but received element of type %s.", expected, GsonHelper.gsonTypeName(jElem))
        );
    }

}