package de.trinext.framework.json;

import com.google.gson.*;

/**
 * @author Dennis Woithe
 * @deprecated Gets removed when {@link com.google.gson} is not wrapped anymore.
 */
@Deprecated
class JsonGsonParser {

    private final com.google.gson.JsonElement rootElement;

    JsonGsonParser(com.google.gson.JsonElement gElem) {
        this.rootElement = gElem;
    }

    JsonElement<?> parse() {
        return parseElem(rootElement);
    }

    private JsonElement<?> parseElem(com.google.gson.JsonElement gElem) {
        return switch (gElem) {
            case JsonObject gObj -> parseMap(gObj);
            case JsonArray gArr -> parseList(gArr);
            case com.google.gson.JsonPrimitive gPrim -> parsePrimitive(gPrim);
            case com.google.gson.JsonNull ignored -> JsonNull.NULL;
            default -> throw new AssertionError("Unexpected value: " + gElem);
        };
    }

    private JsonMap parseMap(JsonObject gObj) {
        var map = new JsonMap();
        gObj.entrySet().forEach(entry -> map.add(
                entry.getKey(),
                parseElem(entry.getValue())
        ));
        return map;
    }

    @SuppressWarnings("TypeMayBeWeakened")
    private JsonList parseList(JsonArray gArr) {
        var list = new JsonList();
        gArr.forEach(elem -> list.add(parseElem(elem)));
        return list;
    }

    private JsonPrimitive<?> parsePrimitive(com.google.gson.JsonPrimitive gObj) {
        if(gObj.isNumber())
            return JsonNumber.from(gObj.getAsNumber());
        if(gObj.isBoolean())
            return JsonBool.from(gObj.getAsBoolean());
        if (gObj.isString())
            return JsonString.from(gObj.getAsString());
        throw new AssertionError("Unexpected value: " + gObj);
    }

}