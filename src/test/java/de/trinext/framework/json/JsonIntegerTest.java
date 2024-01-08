package de.trinext.framework.json;

import org.junit.jupiter.api.Test;

import static de.trinext.framework.util.RandomHelper.randomInt;
import static de.trinext.framework.util.RandomHelper.runForRandomBigInts;
import static org.junit.jupiter.api.Assertions.*;
import static test.util.TestConstants.WORDS_PER_TEST;

/**
 * @author Dennis Woithe
 * @see JsonInteger
 */
class JsonIntegerTest {

    @Test
    void test_from_byte() {
        runForRandomBigInts(WORDS_PER_TEST, randBigInt -> {
            var randByte = randBigInt.byteValue();
            assertEquals(randByte, JsonInteger.from(randByte).value.byteValue());
        });
    }

    @Test
    void test_from_short() {
        runForRandomBigInts(WORDS_PER_TEST, randBigInt -> {
            var randShort = randBigInt.shortValue();
            assertEquals(randShort, JsonInteger.from(randShort).value.shortValue());
        });
    }

    @Test
    void test_from_int() {
        runForRandomBigInts(WORDS_PER_TEST, randBigInt -> {
            var randInt = randBigInt.intValue();
            assertEquals(randInt, JsonInteger.from(randInt).value.intValue());
        });
    }

    @Test
    void test_from_long() {
        runForRandomBigInts(WORDS_PER_TEST, randBigInt -> {
            var randLong = randBigInt.longValue();
            assertEquals(randLong, JsonInteger.from(randLong).value.longValue());
        });
    }

    @Test
    void test_from_big_int() {
        runForRandomBigInts(WORDS_PER_TEST, randBigInt -> assertEquals(
                randBigInt,
                JsonInteger.from(randBigInt).getAsBigInt()
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
        runForRandomBigInts(WORDS_PER_TEST, randBigInt -> assertEquals(
                String.valueOf(randBigInt),
                JsonInteger.from(randBigInt).toString()
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
        assertEquals(JsonInteger.class.getSimpleName(), JsonInteger.from(randomInt()).typeName());
    }

    @Test
    void test_hash_code() {
        runForRandomBigInts(WORDS_PER_TEST, randBigInt -> assertEquals(
                randBigInt.hashCode(),
                JsonInteger.from(randBigInt).hashCode()
        ));
    }

    @Test
    @SuppressWarnings("AssertBetweenInconvertibleTypes")
    void test_equals() {
        runForRandomBigInts(WORDS_PER_TEST, randBigInt -> {
            var jI = JsonInteger.from(randBigInt);
            assertEquals(jI, JsonInteger.from(randBigInt));
            assertNotEquals(randBigInt, jI);
            assertNotEquals(null, jI);
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