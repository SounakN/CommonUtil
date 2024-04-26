package utilities;

import java.util.Properties;


public class PropertyUtil {

    private static ThreadLocal<Properties> props = new ThreadLocal<>();

    public static void loadProperties(String env) {
        props.set(PropertyReader.loadAllProperties(env));
    }

    public static synchronized Properties getProperties() {
        return props.get();
    }

    public static String getProperty(String key) {
        return getProperties().getProperty(key);
    }

    public static void setProperty(String key, String value) {
        getProperties().setProperty(key, value);
    }
}