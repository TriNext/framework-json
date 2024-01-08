package de.trinext.framework.json;

import de.trinext.framework.json.JsonPathFinder.JsonPathIterator.JsonPathElem;

/**
 * @author Dennis Woithe
 */
@SuppressWarnings("serial")
public class InvalidJsonPathTargetException extends RuntimeException {

    InvalidJsonPathTargetException(JsonPathElem pathElem, JsonElement<?> parent) {
        super("Could not find \"" + pathElem + "\" in " + parent.getClass().getSimpleName() + " " + parent);
    }

    InvalidJsonPathTargetException(JsonPathElem pathElem, JsonList listSearchedWithStar, JsonContainer<?> containerInListSearchedWithStar) {
        super("Could not find \"" + pathElem + "\" in " + listSearchedWithStar + "\nSpecifically not in: " + containerInListSearchedWithStar);
    }

}