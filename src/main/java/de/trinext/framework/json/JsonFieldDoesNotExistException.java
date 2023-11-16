package de.trinext.framework.json;

/**
 * @author Dennis Woithe
 */
public class JsonFieldDoesNotExistException extends RuntimeException {

    JsonFieldDoesNotExistException(String fieldName) {
        super(fieldName + " doesn't exist in " + JsonMap.class.getSimpleName() + "!");
    }

}