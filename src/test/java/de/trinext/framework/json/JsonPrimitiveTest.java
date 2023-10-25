package de.trinext.framework.json;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static test.util.TestHelper.*;

/**
 * @author Dennis Woithe
 */
class JsonPrimitiveTest {

    private static final int WORDS_PER_TEST = 100;
    private static final int WORD_LENGTH = 10;

    // ==== METHODS ========================================================== //

    @Test
    void test_from_gson() {
        assertEquals(
                JsonBool.TRUE,
                JsonPrimitive.from(new com.google.gson.JsonPrimitive(true))
        );
        assertEquals(
                JsonBool.FALSE,
                JsonPrimitive.from(new com.google.gson.JsonPrimitive(false))
        );
        testForRandomStrings(
                WORD_LENGTH, WORDS_PER_TEST,
                randStr -> assertEquals(
                        JsonString.from(randStr),
                        JsonPrimitive.from(new com.google.gson.JsonPrimitive(randStr))
                ));
        testForRandomBigInts(
                WORDS_PER_TEST,
                randBigInt -> assertEquals(
                        JsonInteger.from(randBigInt),
                        JsonPrimitive.from(new com.google.gson.JsonPrimitive(randBigInt))
                ));
        testForRandomBigDecs(
                WORDS_PER_TEST,
                randBigDec -> assertEquals(
                        JsonDecimal.from(randBigDec),
                        JsonPrimitive.from(new com.google.gson.JsonPrimitive(randBigDec))
                ));
    }

}