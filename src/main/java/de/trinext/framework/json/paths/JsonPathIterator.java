package de.trinext.framework.json.paths;

import java.util.*;
import java.util.regex.Pattern;

import de.trinext.framework.json.*;

// INNER TYPES ========================================================>>

/**
 * @author Dennis Woithe
 */
class JsonPathFinder {

    private static final Pattern PATH_SEPARATOR = Pattern.compile("(?<!\\\\)\\.");
    private final List<String> pathElems;
    private JsonElement current;

    // ==== CONSTRUCTORS ===================================================== //

    JsonPathFinder(JsonElementIterable start, CharSequence jsonPath) {
        current = (JsonElement) start;
        pathElems = new ArrayList<>(jsonPath.isEmpty() ? Collections.EMPTY_LIST : List.of(PATH_SEPARATOR.split(jsonPath)));
    }


    // ==== METHODS ========================================================== //

    public Optional<JsonElement<?>> find() {
        if (pathElems.isEmpty())
            return Optional.of(current);
        while (!pathElems.isEmpty()) {
            var pathElem = pathElems.remove(0);
            var next = (switch (current) {
                case JsonObject jObj -> jObj.tryGet(pathElem);
                case JsonArray jArr -> jArr.tryGet(Integer.valueOf(pathElem));
                default -> Optional.ofNullable(pathElems.isEmpty() ? null : current);
            });
            if (next.isEmpty())
                return Optional.empty();
            current = next.get();
        }
        return Optional.of(current);
    }

}