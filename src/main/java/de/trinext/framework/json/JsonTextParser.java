package de.trinext.framework.json;

import com.google.gson.JsonParser;

/**
 * @author Dennis Woithe
 */
@SuppressWarnings("ClassCanBeRecord")
public class JsonTextParser {

    private final String json;

    public JsonTextParser(String json) {
        this.json = json;
    }

    public JsonElement<?> parse() {
        return new JsonGsonParser(JsonParser.parseString(json)).parse();
    }

}