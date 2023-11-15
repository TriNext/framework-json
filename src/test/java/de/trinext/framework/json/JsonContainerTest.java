package de.trinext.framework.json;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JsonContainerTest {

    private static final String TEST_STRING = "TRUE";
    private static final float FLOAT_CONSTANT_1 = 2.2f;
    private static final float FLOAT_CONSTANT_2 = -3.2f;
    private static final double DOUBLE_PI_CONSTANT = 3.141_59;
    private static final long LONG_CONSTANT_1 = 777_777L;
    private static final double ROUNDING_ERROR_MARGIN = 0.00000001;
    private static final boolean BOOL_CONSTANT = Boolean.TRUE;
    private final JsonMap testMap = new JsonMap();
    private static final Map<String, Integer> MAP_CONSTANT;

    static {
        MAP_CONSTANT = new HashMap<>();
        MAP_CONSTANT.put("One", 1);
        MAP_CONSTANT.put("TWO", 2);
        MAP_CONSTANT.put("THREE", 3);
    }

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
            BOOL_FIELD = "BOOL_FIELD",
            OBJ_FIELD = "OBJ_FIELD",
            MAP_FIELD = "MAP_FIELD";


    private record TestObject(
            int a,
            String b
    ) { }

    private static final TestObject testObjectInstance = new TestObject(1, "2");

    private static final List<Integer> testList = List.of(1, 2, 3);

    enum TestEnum {This, Is, A, Test}

    @BeforeEach
    void setUp() {
        testMap.add(STRING_FIELD, TEST_STRING);
        testMap.add(INT_FIELD, 2);
        testMap.add(ENUM_FIELD, TestEnum.Test);
        testMap.add(DOUBLE_FIELD, DOUBLE_PI_CONSTANT);
        testMap.add(LONG_FIELD, LONG_CONSTANT_1);
        testMap.add(FLOAT_FIELD, FLOAT_CONSTANT_1);
        testMap.add(BIG_INT_FIELD, BigInteger.ONE);
        testMap.add(BIG_DEC_FIELD, BigDecimal.TEN);
        testMap.add(BOOL_FIELD, BOOL_CONSTANT);
        testMap.add(OBJ_FIELD, testObjectInstance);
        testMap.add(LIST_FIELD, testList);
        testMap.add(MAP_FIELD,MAP_CONSTANT);
    }

    @Test
    void test_try_get_enum() {
        var jsonString = JsonString.from(TestEnum.Test.toString());
        assertEquals(TestEnum.Test, jsonString.tryGetEnum(TestEnum.class).orElseThrow());
        assertNotEquals(TestEnum.This, jsonString.tryGetEnum(TestEnum.class).orElseThrow());
        assertNotEquals(TestEnum.Is, jsonString.tryGetEnum(TestEnum.class).orElseThrow());
        assertNotEquals(TestEnum.A, jsonString.tryGetEnum(TestEnum.class).orElseThrow());
    }

    @Test
    void test_try_get_path_enum() {
        assertEquals(TestEnum.Test, testMap.tryGetPathAsEnum(ENUM_FIELD, TestEnum.class).orElseThrow());
        assertNotEquals(TestEnum.Is, testMap.tryGetPathAsEnum(ENUM_FIELD, TestEnum.class).orElseThrow());
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
        assertNotEquals(3, testMap.tryGetPathAsInt(INT_FIELD).orElseThrow());
        assertTrue(() -> testMap.tryGetPathAsInt(ENUM_FIELD).isEmpty());
    }

    @Test
    void test_try_get_path_as_long() {
        assertEquals(2, testMap.tryGetPathAsLong(INT_FIELD).orElseThrow());
        assertNotEquals(3, testMap.tryGetPathAsLong(INT_FIELD).orElseThrow());
        assertNotEquals(FLOAT_CONSTANT_2, testMap.tryGetPathAsLong(INT_FIELD).orElseThrow());
        assertTrue(testMap.tryGetPathAsLong(ENUM_FIELD).isEmpty());
        assertTrue(testMap.tryGetPathAsLong(STRING_FIELD).isEmpty());
        assertTrue(testMap.tryGetPathAsLong(DOUBLE_FIELD).isEmpty());
    }

    @Test
    void test_try_get_path_as_double() {
        assertEquals(2, testMap.tryGetPathAsDouble(INT_FIELD).orElseThrow());
        assertNotEquals(3, testMap.tryGetPathAsDouble(INT_FIELD).orElseThrow());
        // TODO: implement proper double precision testing without rounding error
        assertEquals(DOUBLE_PI_CONSTANT, testMap.tryGetPathAsDouble(DOUBLE_FIELD).orElseThrow(), ROUNDING_ERROR_MARGIN);
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
        assertNotEquals(1, testMap.tryGetPathAsNumber(BIG_INT_FIELD).orElseThrow());
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
        assertNotEquals(1, testMap.tryGetPathAsBigDec(BIG_DEC_FIELD).orElseThrow());
        assertTrue(testMap.tryGetPathAsBigDec(ENUM_FIELD).isEmpty());
        assertTrue(testMap.tryGetPathAsBigDec(INT_FIELD).isPresent());
        assertEquals(BigDecimal.TEN, testMap.tryGetPathAsBigDec(BIG_DEC_FIELD).orElseThrow());
        assertTrue(testMap.tryGetPathAsBigDec(DOUBLE_FIELD).isPresent());
        assertTrue(testMap.tryGetPathAsBigDec(STRING_FIELD).isEmpty());
    }

    @Test
    void test_try_get_path_as_bool() {
        assertEquals(BOOL_CONSTANT, testMap.tryGetPathAsBool(BOOL_FIELD).orElseThrow());
        assertTrue(testMap.tryGetPathAsBigDec(ENUM_FIELD).isEmpty());
        assertTrue(testMap.tryGetPathAsBigDec(STRING_FIELD).isEmpty());
        assertTrue(testMap.tryGetPathAsBool(DOUBLE_FIELD).isEmpty());
        assertTrue(testMap.tryGetPathAsBool(STRING_FIELD).isEmpty());
    }

    @Test
    void test_try_get_path_as_object() {
        assertEquals(testObjectInstance, testMap.tryGetPathAsObj(OBJ_FIELD, TestObject.class).orElseThrow());
    }

    @Test
    void test_try_get_path_as_stream_of() {
        assertEquals(testList, testMap.tryGetPathAsStreamOf(LIST_FIELD, Integer.class).orElseThrow().toList());
    }
    @Test
    void test_try_get_path_as_list_of() {
        assertEquals(testList, testMap.tryGetPathAsListOf(LIST_FIELD, Integer.class).orElseThrow());
    }
    @Test
    void test_try_get_path_as_set_of() {
        // TODO: Fix test
        assertEquals(MAP_CONSTANT, testMap.tryGetPathAsSetOf(MAP_FIELD, HashMap.class).orElseThrow());
    }

}
