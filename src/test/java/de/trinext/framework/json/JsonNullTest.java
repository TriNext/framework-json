package de.trinext.framework.json;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Dennis Woithe
 */
class JsonNullTest {

    // ==== METHODS ========================================================== //

    @Test
    void test_get_value() {
        assertNull(JsonNull.NULL.value);
    }

    @Test
    void test_hash_code() {
        assertEquals(0, JsonNull.NULL.hashCode());
    }

    @Test @SuppressWarnings({"SimplifiableAssertion", "EqualsWithItself"})
    void test_equals() {
        assertTrue(JsonNull.NULL.equals(JsonNull.NULL));
        assertFalse(JsonNull.NULL.equals(null));
    }

    @Test
    void test_to_string() {
        assertEquals("null", JsonNull.NULL.toString());
    }

    @Test
    void test_json_type_name() {
        assertEquals(JsonNull.class.getSimpleName(), JsonNull.NULL.typeName());
    }

}