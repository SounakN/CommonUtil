package driver;


import utilities.PropertyUtil;

public class BasicConstants {

    public static final int PAGE_LOAD_TIME_OUT = 120;
    public static final int FIREFOX_PAGE_LOAD_TIME_OUT = 120;
    public static final int IMPLICIT_WAIT_TIMEOUT_GENERIC = 60;
    public static final int EXPLICIT_WAIT_TIMEOUT_GENERIC = 30;
    

    public static String isMaximized = (System.getProperty("isMaximized") != null) ? System.getProperty("isMaximized") : PropertyUtil.getProperties().getProperty("isMaximized");
    public static String incognito = (System.getProperty("incognito") != null) ? System.getProperty("incognito") : PropertyUtil.getProperties().getProperty("incognito");
    public static String headless = (System.getProperty("headless") != null) ? System.getProperty("headless") : PropertyUtil.getProperties().getProperty("headless");
    public static String IsRemote = (System.getProperty("IsRemote") != null) ? System.getProperty("IsRemote") : PropertyUtil.getProperties().getProperty("IsRemote");

    // Required if we have chosen Browserstack as our driver
    public static String Browserstack_switch = (System.getProperty("Browserstack_switch") != null) ? System.getProperty("Browserstack_switch") : PropertyUtil.getProperties().getProperty("Browserstack_switch");
    public static String os = (System.getProperty("os") != null) ? System.getProperty("os") : PropertyUtil.getProperties().getProperty("os");
    public static String os_version = (System.getProperty("os_version") != null) ? System.getProperty("os_version") : PropertyUtil.getProperties().getProperty("os_version");
    public static String Browser = (System.getProperty("Browser") != null) ? System.getProperty("Browser") : PropertyUtil.getProperties().getProperty("Browser");
    public static String browser_version_chrome = (System.getProperty("browser_version_chrome") != null) ? System.getProperty("browser_version_chrome") : PropertyUtil.getProperties().getProperty("browser_version_chrome");
    public static String browser_version_firefox = (System.getProperty("browser_version_firefox") != null) ? System.getProperty("browser_version_firefox") : PropertyUtil.getProperties().getProperty("browser_version_firefox");
    public static String browser_version_edge = (System.getProperty("browser_version_edge") != null) ? System.getProperty("browser_version_edge") : PropertyUtil.getProperties().getProperty("browser_version_edge");
    public static String browserstack_local = (System.getProperty("Browserstack_local") != null) ? System.getProperty("Browserstack_local") : PropertyUtil.getProperties().getProperty("Browserstack_local");


}
