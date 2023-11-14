package de.trinext.framework.json;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

/**
 * @author Dennis Woithe
 */
@SuppressWarnings("ClassCanBeRecord")
public class JsonObjectParser {

    private final Object object;

    public JsonObjectParser(Object object) {
        this.object = object;
    }

    public JsonElement<?> parse() {
        return new JsonGsonParser(new Gson().toJsonTree(object)).parse();
    }

}