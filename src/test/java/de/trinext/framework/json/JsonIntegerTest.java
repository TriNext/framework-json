package de.trinext.framework.json;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static test.util.TestConstants.NRS_PER_TEST;
import static test.util.RandomHelper.runForRandomBigInts;

/**
 * @author Dennis Woithe
 */
class JsonIntegerTest {

    @Test
    void test_hash_code() {
        runForRandomBigInts(NRS_PER_TEST, randBigInt -> assertEquals(
                randBigInt.hashCode(),
                JsonInteger.from(randBigInt).hashCode()
        ));
    }

    @Test @SuppressWarnings({"SimplifiableAssertion", "EqualsBetweenInconvertibleTypes"})
    void test_equals() {
        runForRandomBigInts(NRS_PER_TEST, randBigInt -> {
            var jI = JsonInteger.from(randBigInt);
            assertTrue(jI.equals(JsonInteger.from(randBigInt)));
            assertFalse(jI.equals(randBigInt));
            assertFalse(jI.equals(null));
        });
    }

    @Test
    void test_to_string() {
        runForRandomBigInts(NRS_PER_TEST, randBigInt -> assertEquals(
                String.valueOf(randBigInt),
                JsonInteger.from(randBigInt).toString()
        ));
    }

    @Test @SuppressWarnings("OverlyLongLambda")
    void test_from_literal() {
        runForRandomBigInts(NRS_PER_TEST, randBigInt -> {
            var randByte = randBigInt.byteValue();
            assertEquals(randByte, JsonInteger.from(randByte).value.byteValue());
            var randShort = randBigInt.shortValue();
            assertEquals(randShort, JsonInteger.from(randShort).value.shortValue());
            var randInt = randBigInt.intValue();
            assertEquals(randInt, JsonInteger.from(randInt).value.intValue());
            var randLong = randBigInt.longValue();
            assertEquals(randLong, JsonInteger.from(randLong).value.longValue());
        });
    }

    @Test
    void test_json_type_name() {
        assertEquals(JsonInteger.class.getSimpleName(), JsonInteger.from(0).typeName());
    }
    @Test
    void test_try_get_int() {
        var jInt = JsonInteger.from(0);
        assertEquals(0, jInt.tryGetAsInt().orElseThrow());
    }

}