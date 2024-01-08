import de.trinext.framework.json.Json;

/**
 * Json-(de-)serialization library
 *
 * @see Json
 */
@SuppressWarnings("MissingJavadoc") //
module framework.json {
    exports de.trinext.framework.json;
    // custom body handlers
    requires java.net.http;
    // object creation utility
    requires de.trinext.framework.util;
}