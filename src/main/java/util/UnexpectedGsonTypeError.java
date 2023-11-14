package util;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

/**
 * The default case in a switch-statement which tries to
 * exhaustively match all subclasses of {@link JsonElement}.
 *
 * @author Dennis Woithe
 * @deprecated Gets removed when {@link com.google.gson} is not wrapped anymore.
 */
@Deprecated
@SuppressWarnings({"SerializableHasSerializationMethods", "unused", "WeakerAccess", "CyclicClassDependency"})
public class UnexpectedGsonTypeError extends AssertionError {

    // ==== CONSTRUCTORS ===================================================== //

    /**
     * @param jElem The actual instance
     * @param expected {@link JsonElement} or {@link JsonPrimitive}.
     */
    public UnexpectedGsonTypeError(JsonElement jElem, Class<? extends JsonElement> expected) {
        super(jElem == null
              ? String.format("Expected %s but received null.", expected)
              : String.format("Expected %s but received element of unknown type %s.", expected, GsonHelper.gsonTypeName(jElem))
        );
    }

}