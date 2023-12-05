package de.trinext.framework.json.element;

import de.trinext.framework.json.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Dennis Woithe
 */
final class JsonElementClassEnumTest {

    @SuppressWarnings("unused")
    static class TestClass {
        // TODO: Implement me!
    }

    enum TestEnum {
        A, B, C
    }

    // ------------------------------------------------------------------------ //

    @Test
    void test_try_get_as_object() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_object_function() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_as_enum() {
        for (var enumConstant : TestEnum.values()) {
            var jsonEnumConstant = JsonString.from(enumConstant.name());
            var optDeserialized = jsonEnumConstant.tryGetAsEnum(TestEnum.class);
            assertTrue(optDeserialized.isPresent());
            assertEquals(enumConstant, optDeserialized.get());
        }
    }

    // ------------------------------------------------------------------------ //

    @Test
    void test_try_get_path_as_object_class() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_path_as_object_function() {
        // TODO: Implement me!
    }

    @Test
    void test_try_get_path_as_enum() {
        var map = new JsonMap();
        for (var enumConstant : TestEnum.values())
            map.add(enumConstant.name(), enumConstant);
        for (var enumConstant : TestEnum.values()) {
            var optDeserialized = map.tryGetPathAsEnum(enumConstant.name(), TestEnum.class);
            assertTrue(optDeserialized.isPresent());
            assertEquals(enumConstant, optDeserialized.get());
        }
    }

}