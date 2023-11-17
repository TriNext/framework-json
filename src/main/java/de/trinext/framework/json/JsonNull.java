package de.trinext.framework.json;

import java.util.Objects;

/**
 * The json representation of no value.
 *
 * @author Dennis Woithe
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public final class JsonNull extends JsonElement<Void> {

    // ==== CONSTANTS ======================================================== //

    public static final JsonNull NULL = new JsonNull();

    // ==== CONSTRUCTORS ===================================================== //

    /** Hidden singleton constructor */
    private JsonNull() {
        super(null);
    }

    @Override
    public String toString() {
        return Objects.toString(null);
    }

}