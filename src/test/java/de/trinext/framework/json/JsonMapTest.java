package de.trinext.framework.json;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Dennis Woithe
 */
class JsonMapTest {

    @Test
    void test_add_obj() {
        var jMap = new JsonMap();
        jMap.addObj("obj", obj -> obj.add("int", 1));
        var optInt = jMap.tryGetPathAsInt("obj.int");
        assertTrue(optInt.isPresent());
        assertEquals(1, optInt.getAsInt());
    }

    @Test
    void test_json_type_name() {
        assertEquals(JsonMap.class.getSimpleName(), new JsonMap().typeName());
    }

}