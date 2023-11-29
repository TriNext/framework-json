package de.trinext.framework.json;

import com.google.gson.JsonPrimitive;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static test.util.TestConstants.*;
import static test.util.RandomHelper.runForRandomStrings;

/**
 * @author Dennis Woithe
 */
class JsonStringTest {

    // ==== METHODS ========================================================== //

    @Test
    void test_hash_code() {
        runForRandomStrings(WORD_LENGTH, WORDS_PER_TEST, randStr -> assertEquals(
                randStr.hashCode(),
                JsonString.from(randStr).hashCode()
        ));
    }

    @Test @SuppressWarnings({"SimplifiableAssertion", "EqualsBetweenInconvertibleTypes"})
    void test_equals() {
        runForRandomStrings(WORD_LENGTH, WORDS_PER_TEST,
                randStr -> {
                    var jS = JsonString.from(randStr);
                    assertTrue(jS.equals(JsonString.from(randStr)));
                    assertFalse(jS.equals(randStr));
                    assertFalse(jS.equals(null));
                });
    }

    @Test
    void test_to_string() {
        runForRandomStrings(WORD_LENGTH, WORDS_PER_TEST,
                randStr -> assertEquals(
                        new JsonPrimitive(randStr).toString(),
                        JsonString.from(randStr).toString()
                ));
    }

    @Test
    void test_from_literal() {
        runForRandomStrings(WORD_LENGTH, WORDS_PER_TEST,
                randStr -> assertEquals(
                        randStr,
                        JsonString.from(randStr).value
                ));
    }

    @Test
    void test_json_type_name() {
        assertEquals(JsonString.class.getSimpleName(), JsonString.from("").typeName());
    }

    @Test
    void test_try_getters() {
        runForRandomStrings(WORD_LENGTH, WORDS_PER_TEST, randStr -> assertEquals(
                randStr,
                JsonString.from(randStr).tryGetAsString().orElseThrow()
        ));
    }

    @Test
    void test_try_getters_empty() {
        var nonJNr = JsonInteger.from(0);
        assertTrue(nonJNr.tryGetAsString().isEmpty());
    }

}