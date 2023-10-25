package de.trinext.framework.json;

import com.google.gson.JsonPrimitive;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static test.util.TestHelper.testForRandomBigDecs;
import static test.util.TestHelper.testForRandomBigInts;

/**
 * @author Dennis Woithe
 */
class JsonNumberTest {

    private static final int NRS_PER_TEST = 100;

    // ==== METHODS ========================================================== //

    @Test @SuppressWarnings("OverlyLongLambda")
    void test_from_int_literal() {
        testForRandomBigInts(NRS_PER_TEST, randBigInt -> {
            var randByte = randBigInt.byteValue();
            assertEquals(
                    JsonInteger.from(randByte),
                    JsonNumber.from(randByte)
            );
            var randShort = randBigInt.shortValue();
            assertEquals(
                    JsonInteger.from(randShort),
                    JsonNumber.from(randShort)
            );
            var randInt = randBigInt.intValue();
            assertEquals(
                    JsonInteger.from(randInt),
                    JsonNumber.from(randInt)
            );
            var randLong = randBigInt.longValue();
            assertEquals(
                    JsonInteger.from(randLong),
                    JsonNumber.from(randLong)
            );
            assertEquals(
                    JsonInteger.from(randBigInt),
                    JsonNumber.from(randBigInt)
            );
        });
    }

    @Test
    void test_from_dec_literal() {
        testForRandomBigDecs(NRS_PER_TEST, randBigDec -> {
            assertEquals(
                    randBigDec.floatValue(),
                    JsonNumber.from(randBigDec.floatValue()).getValue().floatValue()
            );
            assertEquals(
                    randBigDec.doubleValue(),
                    JsonNumber.from(randBigDec.doubleValue()).getValue().doubleValue()
            );
            assertEquals(
                    randBigDec,
                    JsonNumber.from(randBigDec).getValue()
            );
        });
    }

    @Test
    void test_from_number_literal() {
        testForRandomBigDecs(NRS_PER_TEST, randBigDec -> {
            @SuppressWarnings("all")
            var anonNr = new Number() {

                // ==== METHODS ========================================================== //

                @Override
                public int intValue() {
                    return randBigDec.intValue();
                }

                @Override
                public long longValue() {
                    return randBigDec.longValue();
                }

                @Override
                public float floatValue() {
                    return randBigDec.floatValue();
                }

                @Override
                public double doubleValue() {
                    return randBigDec.doubleValue();
                }

                @Override
                public String toString() {
                    return randBigDec.toString();
                }
            };
            assertEquals(
                    randBigDec,
                    JsonNumber.from(anonNr).getValue()
            );
        });
    }

    @Test
    void test_from_gson() {
        testForRandomBigInts(NRS_PER_TEST, randBigInt -> assertEquals(
                JsonNumber.from(randBigInt),
                JsonNumber.from(new JsonPrimitive(randBigInt))
        ));
        testForRandomBigDecs(NRS_PER_TEST, randBigDec -> assertEquals(
                JsonNumber.from(randBigDec),
                JsonNumber.from(new JsonPrimitive(randBigDec))
        ));
    }

}