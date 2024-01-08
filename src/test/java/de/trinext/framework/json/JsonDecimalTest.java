package de.trinext.framework.json;

import org.junit.jupiter.api.Test;

import static de.trinext.framework.util.RandomHelper.*;
import static org.junit.jupiter.api.Assertions.*;
import static test.util.TestConstants.WORDS_PER_TEST;

/**
 * @author Dennis Woithe
 * @see JsonDecimal
 */
class JsonDecimalTest {

    @Test
    void test_from_float() {
        runForRandomFloats(WORDS_PER_TEST, randFloat -> assertEquals(
                (float) randFloat,
                JsonDecimal.from((float) randFloat).value.floatValue()
        ));
    }

    @Test
    void test_from_double() {
        runForRandomDoubles(WORDS_PER_TEST, randDouble -> assertEquals(
                randDouble,
                JsonDecimal.from(randDouble).value.doubleValue()
        ));
    }

    @Test
    void test_from_big_dec() {
        runForRandomBigDecs(WORDS_PER_TEST, randBigDec -> assertEquals(
                randBigDec,
                JsonDecimal.from(randBigDec).value
        ));
    }

    @Test
    void test_get_as_big_int() {
        // TODO: Implement me!
    }

    @Test
    void test_get_as_big_dec() {
        // TODO: Implement me!
    }

    @Test
    void test_has_decimal_places() {
        // TODO: Implement me!
    }

    @Test
    void test_to_string() {
        runForRandomBigDecs(WORDS_PER_TEST, randBigDec -> assertEquals(
                randBigDec.toPlainString(),
                JsonDecimal.from(randBigDec).toString()
        ));
    }

    @Test
    void test_from_number() {
        // TODO: Implement me!
    }

    @Test
    void test_get_as_byte() {
        // TODO: Implement me!
    }

    @Test
    void test_get_as_short() {
        // TODO: Implement me!
    }

    @Test
    void test_get_as_int() {
        // TODO: Implement me!
    }

    @Test
    void test_get_as_long() {
        // TODO: Implement me!
    }

    @Test
    void test_get_as_float() {
        // TODO: Implement me!
    }

    @Test
    void test_get_as_double() {
        // TODO: Implement me!
    }

    @Test
    void test_get_as_number() {
        // TODO: Implement me!
    }

    @Test
    void test_type_name() {
        assertEquals(JsonDecimal.class.getSimpleName(), JsonDecimal.from(randomBigDec()).typeName());
    }

    @Test
    void test_hash_code() {
        runForRandomBigDecs(WORDS_PER_TEST, randBigDec -> assertEquals(
                randBigDec.hashCode(),
                JsonDecimal.from(randBigDec).hashCode()
        ));
    }

    @Test
    @SuppressWarnings("AssertBetweenInconvertibleTypes")
    void test_equals() {
        runForRandomDoubles(WORDS_PER_TEST, randDouble -> {
            var jD = JsonDecimal.from(randDouble);
            assertEquals(jD, JsonDecimal.from(randDouble));
            assertNotEquals(randDouble, jD);
            assertNotEquals(null, jD);
        });
    }

    @Test
    void test_find_path() {
        // TODO: Implement me!
    }

    @Test
    void test_remove_path() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_byte() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_short() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_int() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_long() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_float() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_double() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_bool() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_char() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_number() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_string() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_big_int() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_big_dec() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_date() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_time() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_date_time() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_enum() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_obj_with_cls() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_obj_with_function() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_stream_of_with_cls() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_list_of_with_cls() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_set_of_with_cls() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_stream_of_with_function() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_list_of_with_function() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_set_of_with_function() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_array_of_with_cls() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_array_of_with_function() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_byte_array() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_short_array() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_int_array() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_long_array() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_float_array() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_double_array() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_boolean_array() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_char_array() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_path() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_path_as_byte() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_path_as_short() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_path_as_int() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_path_as_long() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_path_as_double() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_path_as_float() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_path_as_number() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_path_as_string() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_path_as_big_int() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_path_as_big_dec() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_path_as_bool() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_path_as_date() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_path_as_time() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_path_as_date_time() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_path_as_enum() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_path_as_obj_with_cls() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_path_as_obj_with_function() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_path_as_stream_of_with_cls() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_path_as_stream_of_with_function() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_path_as_list_of_with_cls() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_path_as_list_of_with_function() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_path_as_set_of_with_cls() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_path_as_set_of_with_function() {
        // TODO: Implement me!
    }

}