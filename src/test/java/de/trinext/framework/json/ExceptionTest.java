package de.trinext.framework.json;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ExceptionTest {
    @Test
    void test_json_field_does_not_exist_exception(){
        assertThrows(JsonFieldDoesNotExistException.class, () -> {
            throw new JsonFieldDoesNotExistException("test");
        });
    }
}
