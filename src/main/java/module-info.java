import de.trinext.framework.json.Json;

/**
 * Currently a wrapper for com.google.gson
 *
 * @see Json
 */
@SuppressWarnings("MissingJavadoc") //
module framework.json {
    exports de.trinext.framework.json;
    requires com.google.gson;
    requires java.net.http;
}