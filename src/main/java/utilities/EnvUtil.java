package utilities;

import java.util.Properties;


public class EnvUtil {

    private static ThreadLocal<Properties> props = new ThreadLocal<>();

    // To get and set all properties from property reader

    public static void loadProperties(String env) {
        props.set(PropertyReader.loadAllProperties(env));
    }

    public static synchronized Properties getProperties() {
        return props.get();
    }

    @SuppressWarnings("finally")
    public static String getProperty(String key) {
        String keyvalue = null;
        try {
            keyvalue = getProperties().getProperty(key);
        } catch (Exception e) {
            //log.fatal("Exception Occured while getting the property value\n" + e.getMessage());
        } finally {
            return keyvalue;
        }
    }

    public static void setProperty(String key, String value) {
        getProperties().setProperty(key, value);
    }
}