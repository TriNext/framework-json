package de.trinext.framework.json;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static test.util.TestConstants.*;

/**
 * @author Erik Bannasch
 */

class JsonElementTest {
    private final JsonMap testMap = new JsonMap();
    private static final Set<Integer> SET_CONSTANT = Set.of(1, 2, 3);
    private static final JsonMap nestedMap = new JsonMap();


    private record TestObject(
            int a,
            String b
    ) { }

    private static final TestObject TEST_OBJECT_INSTANCE = new TestObject(1, "2");

    private static final List<Integer> INT_LIST = List.of(1, 2, 3);

    enum TestEnum {TEST}

    @BeforeEach
    void setUp() {
        testMap.add(STRING_FIELD, STRING_CONSTANT);
        testMap.add(INT_FIELD, 2);
        testMap.add(ENUM_FIELD, TestEnum.TEST);
        testMap.add(DOUBLE_FIELD, DOUBLE_PI_CONSTANT);
        testMap.add(BIG_INT_FIELD, BigInteger.ONE);
        testMap.add(BIG_DEC_FIELD, BigDecimal.TEN);
        testMap.add(BOOL_FIELD_TRUE, BOOL_CONSTANT_TRUE);
        testMap.add(BOOL_FIELD_FALSE, BOOL_CONSTANT_FALSE);
        testMap.add(OBJ_FIELD, TEST_OBJECT_INSTANCE);
        testMap.add(LIST_FIELD, INT_LIST);
        testMap.add(SET_FIELD,SET_CONSTANT);
        //nestedMap.add(STRING_FIELD, TEST_STRING);
        //nestedMap.add(BOOL_FIELD_TRUE, BOOL_CONSTANT_TRUE);
        testMap.add(NESTED_FIELD, nestedMap);
    }

    @Test
    void test_try_get_enum() {
        var jsonString = JsonString.from(TestEnum.TEST.toString());
        assertEquals(TestEnum.TEST, jsonString.tryGetAsEnum(TestEnum.class).orElseThrow());
    }

    @Test
    void test_try_get_path_enum() {
        assertEquals(TestEnum.TEST, testMap.tryGetPathAsEnum(ENUM_FIELD, TestEnum.class).orElseThrow());
        assertTrue(testMap.tryGetPathAsEnum(INT_FIELD, TestEnum.class).isEmpty());
    }

    @Test
    void test_try_enum_empty() {
        var nonJStr = JsonInteger.from(0);
        assertTrue(nonJStr.tryGetAsEnum(TestEnum.class).isEmpty());
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
    void test_try_get_path_as_number() {
        assertEquals(BigInteger.ONE, testMap.tryGetPathAsNumber(BIG_INT_FIELD).orElseThrow());
    }

    @Test
    void test_try_get_path_as_string() {
        assertEquals(STRING_CONSTANT, testMap.tryGetPathAsString(STRING_FIELD).orElseThrow());
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
        assertEquals(TEST_OBJECT_INSTANCE, testMap.tryGetPathAsObj(OBJ_FIELD, TestObject.class).orElseThrow());
        assertEquals(TEST_OBJECT_INSTANCE, testMap.tryGetPathAsObj(OBJ_FIELD, jsonElement -> new TestObject(
                jsonElement.tryGetPathAsInt(A_CONSTANT).orElseThrow(),
                jsonElement.tryGetPathAsString(B_CONSTANT).orElseThrow()
        )).orElseThrow());
    }
    @Test
    void test_try_get_path_as_object_empty() {
        var jsonInteger = JsonInteger.from(0);
        assertTrue(jsonInteger.tryGetPathAsObj(INT_FIELD, JsonElement::tryGetAsInt).isEmpty());
        //noinspection ReturnOfNull
        assertTrue(testMap.tryGetPathAsObj(INT_FIELD, jsonElement -> null).isEmpty());
    }

    @Test
    void test_try_get_path_as_stream_of() {
        assertEquals(INT_LIST, testMap.tryGetPathAsStreamOf(LIST_FIELD, Integer.class).orElseThrow().toList());
        assertTrue(testMap.tryGetPathAsStreamOf(NON_EXISTEND_FIELD, Integer.class).isEmpty());
    }
    @Test
    void test_try_get_path_as_list_of() {
        assertEquals(INT_LIST, testMap.tryGetPathAsListOf(LIST_FIELD, Integer.class).orElseThrow());
        assertEquals(List.of("1","2","3") , testMap.tryGetPathAsListOf(LIST_FIELD, JsonElement::toString).orElseThrow());
        assertTrue(testMap.tryGetPathAsListOf(NON_EXISTEND_FIELD, JsonElement::toString).isEmpty());
    }
    @Test
    void test_try_get_path_as_set_of() {
        assertEquals(SET_CONSTANT, testMap.tryGetPathAsSetOf(SET_FIELD, Integer.class).orElseThrow());
        assertNotEquals(SET_CONSTANT, testMap.tryGetPathAsSetOf(SET_FIELD, Double.class).orElseThrow());
        assertEquals(Set.of("1","2","3") , testMap.tryGetPathAsSetOf(SET_FIELD, JsonElement::toString).orElseThrow());
        assertTrue(testMap.tryGetPathAsSetOf(NON_EXISTEND_FIELD, JsonElement::toString).isEmpty());
    }

    @Test
    void test_try_get_object() {
        assertEquals(TEST_OBJECT_INSTANCE, Json.treeFromInstance(TEST_OBJECT_INSTANCE).tryGetAsObj(TestObject.class).orElseThrow());
        assertTrue(Json.treeFromInstance(TEST_OBJECT_INSTANCE).tryGetAsObj(Integer.class).isEmpty());
    }

    @Test
    void test_try_get_set_of() {
        assertEquals(SET_CONSTANT, Json.treeFromInstance(SET_CONSTANT).tryGetAsSetOf(Integer.class).orElseThrow());
    }
    @Test
    void test_try_get_set_of_mapper() {
        assertTrue(SET_CONSTANT.containsAll(
                Json.treeFromInstance(SET_CONSTANT).
                        tryGetAsSetOf(jsonElement -> jsonElement.tryGetAsInt().orElseThrow())
                        .orElseThrow()));
    }

    @Test
    void test_try_get_stream_of_empty() {
        var nonJStream = JsonInteger.from(0);
        assertTrue(nonJStream.tryGetAsStreamOf(jsonElement -> jsonElement.tryGetAsInt().orElseThrow()).isEmpty());
    }

    @Test
    void test_try_get_path_as_stream_of_empty() {
        var jsonInteger = JsonInteger.from(0);
        assertTrue(jsonInteger.tryGetPathAsStreamOf(INT_FIELD, jsonElement -> jsonElement.tryGetAsInt().orElseThrow()).isEmpty());
    }
}
