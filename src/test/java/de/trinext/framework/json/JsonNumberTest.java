package de.trinext.framework.json;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static test.util.TestHelper.*;

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
                    JsonNumber.from(randBigDec.floatValue()).value.floatValue()
            );
            assertEquals(
                    randBigDec.doubleValue(),
                    JsonNumber.from(randBigDec.doubleValue()).value.doubleValue()
            );
            assertEquals(
                    randBigDec,
                    JsonNumber.from(randBigDec).value
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
                    return randBigDec.toPlainString();
                }
            };
            assertEquals(
                    randBigDec,
                    JsonNumber.from(anonNr).value
            );
        });
    }

    @Test
    void test_try_getters_empty() {
        var nonJNr = JsonString.from("not a number");
        var hugeDecimal = BigDecimal.valueOf(Double.MAX_VALUE).multiply(BigDecimal.TEN);

        assertTrue(nonJNr.tryGetAsInt().isEmpty());
        assertTrue(JsonInteger.from(hugeDecimal.toBigInteger()).tryGetAsInt().isEmpty());

        assertTrue(nonJNr.tryGetAsLong().isEmpty());
        assertTrue(JsonInteger.from(hugeDecimal.toBigInteger()).tryGetAsLong().isEmpty());

        assertTrue(nonJNr.tryGetAsDouble().isEmpty());
        assertEquals(Double.POSITIVE_INFINITY, JsonDecimal.from(hugeDecimal).tryGetAsDouble().orElseThrow());


        assertTrue(nonJNr.tryGetAsBigInt().isEmpty());
        assertTrue(nonJNr.tryGetAsBigDec().isEmpty());
        assertTrue(nonJNr.tryGetAsNumber().isEmpty());
    }

}