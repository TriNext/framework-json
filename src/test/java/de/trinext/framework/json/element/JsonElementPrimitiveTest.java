package de.trinext.framework.json.element;

import de.trinext.framework.json.JsonMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static test.util.RandomHelper.*;
import static test.util.TestConstants.field;

/**
 * @author Dennis Woithe
 */
final class JsonElementPrimitiveTest {

    @Test
    void test_try_get_as_int() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_long() {
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