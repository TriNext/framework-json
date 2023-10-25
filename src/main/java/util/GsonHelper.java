package util;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.google.gson.*;
import de.trinext.framework.json.Json;

import static de.trinext.framework.json.GsonPrimitiveTypeName.*;

/**
 * Static helper methods for com.google.gson
 *
 * @author Dennis Woithe
 * @see Json for tree-conversion & (de-)serialization
 * @deprecated Gets removed when {@link com.google.gson} is not wrapped anymore.
 */
@SuppressWarnings("InterfaceNeverImplemented")
@Deprecated
public interface GsonHelper {

    // ==== STATIC FUNCTIONS ================================================= //

    /** Returns the type name of a {@link JsonElement}. */
    static String gsonTypeName(JsonElement jElem) {
        if (jElem instanceof JsonPrimitive jPrim) {
            if (jPrim.isBoolean())
                return JSON_BOOLEAN_TYPE;
            if (jPrim.isString())
                return JSON_STRING_TYPE;
            if (jPrim.isNumber())
                return gsonNrIsInt(jPrim) ? JSON_INTEGER_TYPE : JSON_DECIMAL_TYPE;
            throw new UnexpectedGsonTypeError(jElem, JsonPrimitive.class);
        }
        return jElem.getClass().getSimpleName();
    }

    /** Returns true if a {@link JsonPrimitive} is an Integer (not a Decimal) */
    static boolean gsonNrIsInt(JsonPrimitive jPrim) {
        if (!jPrim.isNumber())
            throw new UnexpectedGsonTypeException(jPrim, JsonPrimitive.class);
        return BigDecimalHelper.isInteger(jPrim.getAsBigDecimal());
    }

    /** Returns true if a {@link JsonPrimitive} is a Decimal (not an Integer) */
    static boolean gsonNrIsDec(JsonPrimitive jPrim) {
        if (!jPrim.isNumber())
            throw new UnexpectedGsonTypeException(jPrim, JsonPrimitive.class);
        return !BigDecimalHelper.isInteger(jPrim.getAsBigDecimal());
    }

    /** Returns the type name of a gson-class. */
    static String gsonTypeName(Class<? extends JsonElement> jElem) {
        return jElem.getSimpleName();
    }

    /** Creates a {@link Stream} from a {@link JsonArray}. */
    static Stream<JsonElement> arrayToStream(JsonArray jArr) {
        return StreamSupport.stream(jArr.spliterator(), false);
    }

}