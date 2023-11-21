package de.trinext.framework.json;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static test.util.TestConstants.*;

/**
 * @author Dennis Woithe
 */
class JsonPrimitiveTest {
    private record TestObject(int a, String b) {}

    private static final TestObject testObjectInstance = new TestObject(1, "2");

    @Test @SuppressWarnings("ConstantConditions")
    void test_try_from(){
        assertEquals("\"" + STRING_CONSTANT + "\"", JsonPrimitive.tryFrom(STRING_CONSTANT).toString());
        assertEquals("false", JsonPrimitive.tryFrom(BOOL_CONSTANT_FALSE).toString());
        assertEquals(Boolean.FALSE, JsonPrimitive.tryFrom(BOOL_CONSTANT_FALSE).getValue());
        assertEquals(NUMBER_CONSTANT, JsonPrimitive.tryFrom(NUMBER_CONSTANT).getValue());
        assertThrows(IllegalArgumentException.class, () -> JsonPrimitive.tryFrom(testObjectInstance));
        assertThrows(NullPointerException.class, () -> JsonPrimitive.tryFrom(null).toString());
    }

}