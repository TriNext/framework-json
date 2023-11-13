package de.trinext.framework.json;

import java.lang.reflect.Modifier;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Dennis Woithe
 */
class JsonTest {

    @Test
    void test_constructor() throws NoSuchMethodException {
        var constructor = Json.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        assertEquals(0, constructor.getParameterCount());
    }


}