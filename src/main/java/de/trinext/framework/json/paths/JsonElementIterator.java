package de.trinext.framework.json.paths;

import java.util.Iterator;
import java.util.NoSuchElementException;

import de.trinext.framework.json.JsonElement;

/**
 * @author Dennis Woithe
 */
public class JsonElementIterator implements Iterator<JsonElement<?>> {

    private final JsonPathIterator path;
    private JsonElement<?> current;

    // ==== CONSTRUCTORS ===================================================== //

    JsonElementIterator(JsonElementIterable jElem, String path) {
        current = (JsonElement<?>) jElem;
        this.path = new JsonPathIterator(path);
    }


    // ==== METHODS ========================================================== //

    @Override
    public String toString() {
        return path.toString();
    }

    @Override
    public boolean hasNext() {
        return current instanceof JsonElementIterable && path.hasNext();
    }

    @Override
    public JsonElement<?> next() {
        if (!hasNext())
            throw new NoSuchElementException("Reached the end of path at: " + current);
        var jIter = (JsonElementIterable) current;
        var currentPathElem = path.next();
        current = jIter.tryGet(currentPathElem)
                .orElseThrow(() -> new NoSuchElementException(
                        "Couldn't find " + currentPathElem + " in " + current
                ));
        return current;
    }


}