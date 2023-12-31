package de.trinext.framework.json;

/**
 * Gets thrown when someone tries to add a field to a {@link JsonMap},
 * which already has a field with the same name.
 *
 * @author Dennis Woithe
 */
@SuppressWarnings({"SerializableHasSerializationMethods", "UncheckedExceptionClass"})
public class JsonFieldAlreadyExistsException extends RuntimeException {

    JsonFieldAlreadyExistsException(String fieldName) {
        super(fieldName + " was already added to " + JsonMap.class.getSimpleName() + "!");
    }

}