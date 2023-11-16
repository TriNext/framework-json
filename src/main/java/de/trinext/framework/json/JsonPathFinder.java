package de.trinext.framework.json;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @author Dennis Woithe
 */
class JsonPathFinder {

    private static final Pattern PATH_SEPARATOR = Pattern.compile("(?<!\\\\)\\.");
    private final List<String> pathElems;
    private JsonElement<?>[] discovered;
    private JsonElement<?> current;

    // ==== CONSTRUCTORS ===================================================== //

    @SuppressWarnings("TypeMayBeWeakened")
    JsonPathFinder(JsonContainer<?> start, CharSequence jsonPath) {
        current = start;
        pathElems = new ArrayList<>(jsonPath.isEmpty() ? Collections.emptyList() : List.of(PATH_SEPARATOR.split(jsonPath)));
    }


    // ==== METHODS ========================================================== //

    Optional<JsonElement<?>> find() {
        if (pathElems.isEmpty())
            return Optional.of(current);
        discovered = new JsonElement<?>[pathElems.size()];
        var index = 0;
        while (!pathElems.isEmpty()) {
            var pathElem = pathElems.remove(0);
            var next = (switch (current) {
                case JsonMap jObj -> jObj.tryGet(pathElem);
                case JsonList jArr -> jArr.tryGet(Integer.parseInt(pathElem));
                default -> Optional.ofNullable(pathElems.isEmpty() ? null : current);
            });
            if (next.isEmpty())
                return Optional.empty();
            discovered[index++] = current;
            current = next.get();
        }
        return Optional.of(current);
    }

    JsonElement<?>[] elemPath() {
        if (discovered == null)
            throw new IllegalStateException("No path was discovered yet!");
        return discovered.clone();
    }

    String[] stringPath() {
        return pathElems.toArray(String[]::new);
    }

}