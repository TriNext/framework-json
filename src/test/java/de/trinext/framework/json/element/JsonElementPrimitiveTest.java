package de.trinext.framework.json.element;

import java.util.*;

import de.trinext.framework.json.*;
import org.junit.jupiter.api.Test;

import static de.trinext.framework.util.RandomHelper.*;
import static org.junit.jupiter.api.Assertions.*;
import static test.util.TestConstants.*;

/**
 * @author Dennis Woithe
 */
final class JsonElementPrimitiveTest {

    @Test
    void test_try_get_as_byte() {
        runForRandomStrings(WORD_LENGTH, WORDS_PER_TEST, randomString -> {
            var nonJInt = JsonString.from(randomString);
            assertEquals(Optional.empty(), nonJInt.tryGetAsByte());
        });
        runForRandomBytes(WORDS_PER_TEST, randomByte -> {
            var jByte = JsonInteger.from((byte) randomByte);
            assertEquals(Optional.of(randomByte), jByte.tryGetAsByte());
        });
        runForRandomBigDecs(WORDS_PER_TEST, randomBigDec -> {
            var jDec = JsonDecimal.from(randomBigDec);
            assertEquals(Optional.of(randomBigDec.byteValue()), jDec.tryGetAsByte());
        });
    }

    @Test
    void test_try_get_as_short() {
        runForRandomStrings(WORD_LENGTH, WORDS_PER_TEST, randomString -> {
            var nonJInt = JsonString.from(randomString);
            assertEquals(Optional.empty(), nonJInt.tryGetAsShort());
        });
        runForRandomShorts(WORDS_PER_TEST, randomShort -> {
            var jShort = JsonInteger.from((short) randomShort);
            assertEquals(Optional.of(randomShort), jShort.tryGetAsShort());
        });
        runForRandomBigDecs(WORDS_PER_TEST, randomBigDec -> {
            var jDec = JsonDecimal.from(randomBigDec);
            assertEquals(Optional.of(randomBigDec.shortValue()), jDec.tryGetAsShort());
        });
    }

    @Test
    void test_try_get_as_int() {
        runForRandomStrings(WORD_LENGTH, WORDS_PER_TEST, randomString -> {
            var nonJInt = JsonString.from(randomString);
            assertEquals(OptionalInt.empty(), nonJInt.tryGetAsInt());
        });
        runForRandomInts(WORDS_PER_TEST, randomInt -> {
            var jInt = JsonInteger.from(randomInt);
            assertEquals(OptionalInt.of(randomInt), jInt.tryGetAsInt());
        });
        runForRandomBigDecs(WORDS_PER_TEST, randomBigDec -> {
            var jDec = JsonDecimal.from(randomBigDec);
            assertEquals(OptionalInt.of(randomBigDec.intValue()), jDec.tryGetAsInt());
        });
    }

    @Test
    void test_try_get_as_long() {
        runForRandomStrings(WORD_LENGTH, WORDS_PER_TEST, randomString -> {
            var nonJInt = JsonString.from(randomString);
            assertEquals(OptionalLong.empty(), nonJInt.tryGetAsLong());
        });
        runForRandomLongs(WORDS_PER_TEST, randomLong -> {
            var jLong = JsonInteger.from(randomLong);
            assertEquals(OptionalLong.of(randomLong), jLong.tryGetAsLong());
        });
        runForRandomBigDecs(WORDS_PER_TEST, randomBigDec -> {
            var jDec = JsonDecimal.from(randomBigDec);
            assertEquals(OptionalLong.of(randomBigDec.longValue()), jDec.tryGetAsLong());
        });
    }

    @Test
    void test_try_get_as_float() {
        runForRandomStrings(WORD_LENGTH, WORDS_PER_TEST, randomString -> {
            var nonJDec = JsonString.from(randomString);
            assertEquals(Optional.empty(), nonJDec.tryGetAsFloat());
        });
        runForRandomFloats(WORDS_PER_TEST, randomFloat -> {
            var jDec = JsonDecimal.from((float) randomFloat);
            assertEquals(Optional.of(randomFloat), jDec.tryGetAsFloat());
        });
        runForRandomBigDecs(WORDS_PER_TEST, randomBigDec -> {
            var jDec = JsonDecimal.from(randomBigDec);
            assertEquals(Optional.of(randomBigDec.floatValue()), jDec.tryGetAsFloat());
        });
    }

    @Test
    void test_try_get_as_double() {
        runForRandomStrings(WORD_LENGTH, WORDS_PER_TEST, randomString -> {
            var nonJDec = JsonString.from(randomString);
            assertEquals(OptionalDouble.empty(), nonJDec.tryGetAsDouble());
        });
        runForRandomDoubles(WORDS_PER_TEST, randomDouble -> {
            var jDec = JsonDecimal.from(randomDouble);
            assertEquals(OptionalDouble.of(randomDouble), jDec.tryGetAsDouble());
        });
        runForRandomBigDecs(WORDS_PER_TEST, randomBigDec -> {
            var jDec = JsonDecimal.from(randomBigDec);
            assertEquals(OptionalDouble.of(randomBigDec.doubleValue()), jDec.tryGetAsDouble());
        });
    }

    @Test
    void test_try_get_as_bool() {
        runForRandomStrings(WORD_LENGTH, WORDS_PER_TEST, randomString -> {
            var nonJBool = JsonString.from(randomString);
            assertEquals(Optional.empty(), nonJBool.tryGetAsBool());
        });
        assertEquals(Optional.of(true), JsonBool.TRUE.tryGetAsBool());
        assertEquals(Optional.of(false), JsonBool.FALSE.tryGetAsBool());
    }

    // ------------------------------------------------------------------------ //

    @Test
    void test_try_get_path_as_int() {
        var randInt = randomInt();
        var map = new JsonMap().add(field(1), randInt);
        var optDeserialized = map.tryGetPathAsInt(field(1));
        assertTrue(optDeserialized.isPresent());
        assertEquals(randInt, optDeserialized.getAsInt());
    }

    @Test
    void test_try_get_path_as_long() {
        var randLong = randomLong();
        var map = new JsonMap().add(field(1), randLong);
        var optDeserialized = map.tryGetPathAsLong(field(1));
        assertTrue(optDeserialized.isPresent());
        assertEquals(randLong, optDeserialized.getAsLong());
    }

    @Test
    void test_try_get_path_as_double() {
        var randDouble = randomDouble();
        var map = new JsonMap().add(field(1), randDouble);
        var optDeserialized = map.tryGetPathAsDouble(field(1));
        assertTrue(optDeserialized.isPresent());
        assertEquals(randDouble, optDeserialized.getAsDouble());
    }

    @Test
    void test_try_get_path_as_bool() {
        var map = new JsonMap()
                .add(field(1), true)
                .add(field(2), false)
                .add(field(3), Boolean.TRUE)
                .add(field(4), Boolean.FALSE);
        assertTrue(map.tryGetPathAsBool(field(1)).orElseThrow());
        assertFalse(map.tryGetPathAsBool(field(2)).orElseThrow());
        assertTrue(map.tryGetPathAsBool(field(3)).orElseThrow());
        assertFalse(map.tryGetPathAsBool(field(4)).orElseThrow());
    }

}