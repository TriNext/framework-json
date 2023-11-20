package de.trinext.framework.json;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author ErikBannasch
 */
final class JsonContainerTest {

    private static final String TEST_STRING = "TRUE";
    private static final float FLOAT_CONSTANT_1 = 2.2f;
    private static final double DOUBLE_PI_CONSTANT = 3.141_59;
    private static final long LONG_CONSTANT_1 = 777_777L;
    private static final boolean BOOL_CONSTANT_TRUE = Boolean.TRUE;
    private static final boolean BOOL_CONSTANT_FALSE = Boolean.FALSE;
    private final JsonMap testMap = new JsonMap();
    private static final Set<Integer> SET_CONSTANT = Set.of(1, 2, 3);


    private static final String //
            LIST_FIELD = "LIST_FIELD",
            STRING_FIELD = "STRING_FIELD",
            INT_FIELD = "INT_FIELD",
            ENUM_FIELD = "ENUM_FIELD",
            DOUBLE_FIELD = "DOUBLE_FIELD",
            LONG_FIELD = "LONG_FIELD",
            FLOAT_FIELD = "FLOAT_FIELD",
            NON_EXISTEND_FIELD = "NON_EXISTEND_FIELD",
            BIG_INT_FIELD = "NUMBER_FIELD",
            BIG_DEC_FIELD = "BIG_DEC_FIELD",
            BOOL_FIELD_TRUE = "BOOL_FIELD_TRUE",
            BOOL_FIELD_FALSE = "BOOL_FIELD_FALSE",
            OBJ_FIELD = "OBJ_FIELD",
            SET_FIELD = "SET_FIELD";


    private record TestObject(
            int a,
            String b
    ) { }

    private static final TestObject testObjectInstance = new TestObject(1, "2");

    private static final List<Integer> INT_LIST = List.of(1, 2, 3);

    enum TestEnum {TEST}

    @BeforeEach
    void setUp() {
        testMap.add(STRING_FIELD, TEST_STRING);
        testMap.add(INT_FIELD, 2);
        testMap.add(ENUM_FIELD, TestEnum.TEST);
        testMap.add(DOUBLE_FIELD, DOUBLE_PI_CONSTANT);
        testMap.add(LONG_FIELD, LONG_CONSTANT_1);
        testMap.add(FLOAT_FIELD, FLOAT_CONSTANT_1);
        testMap.add(BIG_INT_FIELD, BigInteger.ONE);
        testMap.add(BIG_DEC_FIELD, BigDecimal.TEN);
        testMap.add(BOOL_FIELD_TRUE, BOOL_CONSTANT_TRUE);
        testMap.add(BOOL_FIELD_FALSE, BOOL_CONSTANT_FALSE);
        testMap.add(OBJ_FIELD, testObjectInstance);
        testMap.add(LIST_FIELD, INT_LIST);
        testMap.add(SET_FIELD,SET_CONSTANT);
    }

    @Test
    void test_try_get_enum() {
        var jsonString = JsonString.from(TestEnum.TEST.toString());
        assertEquals(TestEnum.TEST, jsonString.tryGetEnum(TestEnum.class).orElseThrow());
    }

    @Test
    void test_try_get_path_enum() {
        assertEquals(TestEnum.TEST, testMap.tryGetPathAsEnum(ENUM_FIELD, TestEnum.class).orElseThrow());
        assertTrue(testMap.tryGetPathAsEnum(INT_FIELD, TestEnum.class).isEmpty());
    }

    @Test
    void test_try_enum_empty() {
        var nonJStr = JsonInteger.from(0);
        assertTrue(nonJStr.tryGetEnum(TestEnum.class).isEmpty());
    }

    @Test
    void test_try_get_path_as_int() {
        assertEquals(2, testMap.tryGetPathAsInt(INT_FIELD).orElseThrow());
        assertTrue(() -> testMap.tryGetPathAsInt(ENUM_FIELD).isEmpty());
    }

    @Test
    void test_try_get_path_as_long() {
        assertEquals(2, testMap.tryGetPathAsLong(INT_FIELD).orElseThrow());
        assertTrue(testMap.tryGetPathAsLong(ENUM_FIELD).isEmpty());
        assertTrue(testMap.tryGetPathAsLong(STRING_FIELD).isEmpty());
        assertTrue(testMap.tryGetPathAsLong(DOUBLE_FIELD).isEmpty());
    }

    @Test
    void test_try_get_path_as_double() {
        assertEquals(2, testMap.tryGetPathAsDouble(INT_FIELD).orElseThrow());
        assertNotEquals(3, testMap.tryGetPathAsDouble(INT_FIELD).orElseThrow());
        assertEquals(new BigDecimal(DOUBLE_PI_CONSTANT).doubleValue(), testMap.tryGetPathAsDouble(DOUBLE_FIELD).orElseThrow());
        assertTrue(() -> testMap.tryGetPathAsDouble(ENUM_FIELD).isEmpty());
    }

    @Test
    void test_try_get_path() {
        assertTrue(testMap.tryGetPath(INT_FIELD).isPresent());
        assertTrue(testMap.tryGetPath(NON_EXISTEND_FIELD).isEmpty());
    }

    @Test
    void test_try_get_path_as_number() {
        assertEquals(BigInteger.ONE, testMap.tryGetPathAsNumber(BIG_INT_FIELD).orElseThrow());
    }

    @Test
    void test_try_get_path_as_string() {
        assertEquals(TEST_STRING, testMap.tryGetPathAsString(STRING_FIELD).orElseThrow());
        assertTrue(testMap.tryGetPathAsString(ENUM_FIELD).isPresent());
        assertTrue(testMap.tryGetPathAsString(INT_FIELD).isEmpty());
        assertTrue(testMap.tryGetPathAsString(DOUBLE_FIELD).isEmpty());
    }

    @Test
    void test_try_get_path_as_big_int() {
        assertEquals(BigInteger.ONE, testMap.tryGetPathAsBigInt(BIG_INT_FIELD).orElseThrow());
        assertTrue(testMap.tryGetPathAsBigInt(ENUM_FIELD).isEmpty());
        assertTrue(testMap.tryGetPathAsBigInt(INT_FIELD).isPresent());
        assertTrue(testMap.tryGetPathAsBigInt(DOUBLE_FIELD).isEmpty());
        assertTrue(testMap.tryGetPathAsBigInt(STRING_FIELD).isEmpty());
    }

    @Test
    void test_try_get_path_as_big_dec() {
        assertEquals(BigDecimal.TEN, testMap.tryGetPathAsBigDec(BIG_DEC_FIELD).orElseThrow());
        assertTrue(testMap.tryGetPathAsBigDec(ENUM_FIELD).isEmpty());
        assertTrue(testMap.tryGetPathAsBigDec(STRING_FIELD).isEmpty());
        assertTrue(testMap.tryGetPathAsBigDec(INT_FIELD).isPresent());
        assertTrue(testMap.tryGetPathAsBigDec(DOUBLE_FIELD).isPresent());
    }

    @Test
    void test_try_get_path_as_bool() {
        assertEquals(BOOL_CONSTANT_TRUE, testMap.tryGetPathAsBool(BOOL_FIELD_TRUE).orElseThrow());
        assertEquals(BOOL_CONSTANT_FALSE, testMap.tryGetPathAsBool(BOOL_FIELD_FALSE).orElseThrow());
        assertTrue(testMap.tryGetPathAsBool(ENUM_FIELD).isEmpty());
        assertTrue(testMap.tryGetPathAsBool(STRING_FIELD).isEmpty());
        assertTrue(testMap.tryGetPathAsBool(DOUBLE_FIELD).isEmpty());
        assertTrue(testMap.tryGetPathAsBool(STRING_FIELD).isEmpty());
    }

    @Test
    void test_try_get_path_as_object() {
        assertEquals(testObjectInstance, testMap.tryGetPathAsObj(OBJ_FIELD, TestObject.class).orElseThrow());
    }

    @Test
    void test_try_get_path_as_stream_of() {
        assertEquals(INT_LIST, testMap.tryGetPathAsStreamOf(LIST_FIELD, Integer.class).orElseThrow().toList());
    }
    @Test
    void test_try_get_path_as_list_of() {
        assertEquals(INT_LIST, testMap.tryGetPathAsListOf(LIST_FIELD, Integer.class).orElseThrow());
    }
    @Test
    void test_try_get_path_as_set_of() {
        assertEquals(SET_CONSTANT, testMap.tryGetPathAsSetOf(SET_FIELD, Integer.class).orElseThrow());
        assertNotEquals(SET_CONSTANT, testMap.tryGetPathAsSetOf(SET_FIELD, Double.class).orElseThrow());

    }

    @Test
    void test_find_path() {
        assertTrue(testMap.findPath(STRING_FIELD));
    }



    @Test
    void test_remove_path() {
        testMap.removePath(STRING_FIELD);
        assertFalse(testMap.findPath(STRING_FIELD));
    }

}