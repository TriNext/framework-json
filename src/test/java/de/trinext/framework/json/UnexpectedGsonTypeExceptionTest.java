package de.trinext.framework.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import util.UnexpectedGsonTypeException;

/**
 * @author Dennis Woithe
 */
class UnexpectedGsonTypeExceptionTest {

    // ==== METHODS ========================================================== //

    @Test
    void test_construct_with_null() {
        Assertions.assertDoesNotThrow(() -> new UnexpectedGsonTypeException(null, JsonElement.class));
        Assertions.assertDoesNotThrow(() -> new UnexpectedGsonTypeException(null, ""));
    }

    @Test
    void test_construct_with_value() {
        var jObj = new JsonObject();
        Assertions.assertDoesNotThrow(() -> new UnexpectedGsonTypeException(jObj, JsonElement.class));
        Assertions.assertDoesNotThrow(() -> new UnexpectedGsonTypeException(jObj, ""));
    }

}