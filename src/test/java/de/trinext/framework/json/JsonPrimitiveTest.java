package de.trinext.framework.json;

import java.math.BigInteger;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Dennis Woithe
 */
class JsonPrimitiveTest {

    private static final String TEST_STRING = "Hello World";
    private static final Boolean BOOL_CONSTANT = Boolean.FALSE;
    private static final Number NUMBER_CONSTANT = BigInteger.ONE;
    private record TestObject(int a, String b) {}

    private static final TestObject testObjectInstance = new TestObject(1, "2");

    @Test @SuppressWarnings("ConstantConditions")
    void test_try_from(){
        assertEquals("\"" + TEST_STRING + "\"", JsonPrimitive.tryFrom(TEST_STRING).toString());
        assertEquals("false", JsonPrimitive.tryFrom(BOOL_CONSTANT).toString());
        assertEquals(Boolean.FALSE, JsonPrimitive.tryFrom(BOOL_CONSTANT).getValue());
        assertEquals(NUMBER_CONSTANT, JsonPrimitive.tryFrom(NUMBER_CONSTANT).getValue());
        assertThrows(IllegalArgumentException.class, () -> JsonPrimitive.tryFrom(testObjectInstance));
        assertThrows(NullPointerException.class, () -> JsonPrimitive.tryFrom(null).toString());
    }

}