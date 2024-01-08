package de.trinext.framework.json;

import org.junit.jupiter.api.Test;

import static de.trinext.framework.util.RandomHelper.runForRandomBigDecs;
import static de.trinext.framework.util.RandomHelper.runForRandomStrings;
import static org.junit.jupiter.api.Assertions.*;
import static test.util.TestConstants.WORDS_PER_TEST;

/**
 * @author Dennis Woithe
 * @see JsonPrimitive
 */
class JsonPrimitiveTest {

    @Test
    @SuppressWarnings("OverlyLongLambda")
    void test_from() {
        assertEquals(JsonNull.NULL, JsonPrimitive.from(null));
        assertEquals(JsonBool.FALSE, JsonPrimitive.from(false));
        assertEquals(JsonBool.TRUE, JsonPrimitive.from(true));
        runForRandomBigDecs(WORDS_PER_TEST, randNr -> {
            assertInstanceOf(JsonNumber.class, JsonPrimitive.from(randNr.byteValue()));
            assertInstanceOf(JsonNumber.class, JsonPrimitive.from(randNr.shortValue()));
            assertInstanceOf(JsonNumber.class, JsonPrimitive.from(randNr.intValue()));
            assertInstanceOf(JsonNumber.class, JsonPrimitive.from(randNr.longValue()));
            assertInstanceOf(JsonNumber.class, JsonPrimitive.from(randNr.floatValue()));
            assertInstanceOf(JsonNumber.class, JsonPrimitive.from(randNr.doubleValue()));
            assertInstanceOf(JsonNumber.class, JsonPrimitive.from(randNr.toBigInteger()));
            assertInstanceOf(JsonNumber.class, JsonPrimitive.from(randNr));
        });
        runForRandomStrings(WORDS_PER_TEST, WORDS_PER_TEST,
                randStr -> assertInstanceOf(JsonString.class, JsonPrimitive.from(randStr))
        );
    }

}