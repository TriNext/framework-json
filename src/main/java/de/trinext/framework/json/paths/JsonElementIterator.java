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
    public boolean hasNext() {
        return current instanceof JsonElementIterable;
    }

    @Override
    public JsonElement<?> next() {
        if (!path.hasNext())
            throw new NoSuchElementException("Reached the end of path.");
        var last = current;
        var currentPathElem = path.next();
        if (current instanceof JsonElementIterable jIter) {
            current = jIter.tryGet(currentPathElem)
                    .orElseThrow(() -> new NoSuchElementException(
                            "Couldn't find " + currentPathElem + " in " + path
                    ));
        } else throw new NoSuchElementException("Reached a leaf in the object tree.");
        return last;
    }

}