package de.trinext.framework.json;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Dennis Woithe
 */
class JsonBoolTest {

    // ==== METHODS ========================================================== //

    @Test
    void test_get_value() {
        assertTrue(JsonBool.TRUE.getValue());
        assertFalse(JsonBool.FALSE.getValue());
    }

    @Test
    void test_hash_code() {
        assertEquals(Boolean.TRUE.hashCode(), JsonBool.TRUE.hashCode());
        assertEquals(Boolean.FALSE.hashCode(), JsonBool.FALSE.hashCode());
    }

    @Test @SuppressWarnings({"SimplifiableAssertion", "EqualsWithItself"})
    void test_equals() {
        assertTrue(JsonBool.TRUE.equals(JsonBool.TRUE));
        assertTrue(JsonBool.FALSE.equals(JsonBool.FALSE));

        assertFalse(JsonBool.TRUE.equals(JsonBool.FALSE));
        assertFalse(JsonBool.FALSE.equals(JsonBool.TRUE));

        assertFalse(JsonBool.TRUE.equals(null));
        assertFalse(JsonBool.FALSE.equals(null));
    }

    @Test
    void test_to_string() {
        assertEquals(Boolean.TRUE.toString(), JsonBool.TRUE.toString());
        assertEquals(Boolean.FALSE.toString(), JsonBool.FALSE.toString());
    }

    @Test
    void test_from_literal() {
        assertEquals(JsonBool.TRUE, JsonBool.from(true));
        assertEquals(JsonBool.FALSE, JsonBool.from(false));
    }

    @Test
    void test_json_type_name() {
        assertEquals(JsonBool.class.getSimpleName(), JsonBool.TRUE.typeName());
        assertEquals(JsonBool.class.getSimpleName(), JsonBool.FALSE.typeName());
    }

}