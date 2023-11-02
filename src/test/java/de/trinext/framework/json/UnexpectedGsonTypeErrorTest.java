package de.trinext.framework.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import util.UnexpectedGsonTypeError;

/**
 * @author Dennis Woithe
 */
class UnexpectedGsonTypeErrorTest {

    // ==== METHODS ========================================================== //

    @Test
    void test_construct_with_null() {
        Assertions.assertDoesNotThrow(() -> new UnexpectedGsonTypeError(null, com.google.gson.JsonElement.class));
    }

    @Test
    void test_construct_with_value() {
        Assertions.assertDoesNotThrow(() -> new UnexpectedGsonTypeError(new JsonObject(), JsonElement.class));
    }

}