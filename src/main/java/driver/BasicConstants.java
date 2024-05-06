package driver;


import utilities.PropertyUtil;

public class BasicConstants {

    public static final int PAGE_LOAD_TIME_OUT = 120;
    public static final int SCRIPT_LOAD_TIME_OUT = 120;
    public static final int FIREFOX_PAGE_LOAD_TIME_OUT = 120;
    public static final int IMPLICIT_WAIT_TIMEOUT_GENERIC = 60;
    public static final int EXPLICIT_WAIT_TIMEOUT_GENERIC = 30;
    public static final int APPIUM_COMMAND_TIMEOUT = 30;
    public static final String ANDROID_PLATFORM_NAME = "Android";
    public static final String ANDROID_AUTOMATION_NAME = "UiAutomator2";

    public static final String EXCEL_FOLDER_NAME = "ExcelFiles";
    public static final String DATA_CONFIG = "DataConfig";
    public static final String MAVEN_PROPERTIES_FILE = "mavenProperties.properties";

    public static String isMaximized = (System.getProperty("isMaximized") != null) ? System.getProperty("isMaximized") : PropertyUtil.getProperties().getProperty("isMaximized");
    public static String incognito = (System.getProperty("incognito") != null) ? System.getProperty("incognito") : PropertyUtil.getProperties().getProperty("incognito");
    public static String headless = (System.getProperty("headless") != null) ? System.getProperty("headless") : PropertyUtil.getProperties().getProperty("headless");
    public static String IsRemote = (System.getProperty("IsRemote") != null) ? System.getProperty("IsRemote") : PropertyUtil.getProperties().getProperty("IsRemote");


    //Required for Mobile properties for Android
    public static String mobileDeviceAndroid = (System.getProperty("Mobile_device_android") != null) ? System.getProperty("Mobile_device_android") : PropertyUtil.getProperties().getProperty("Mobile_device_android");
    public static String mobileBrowserAndroid = (System.getProperty("Browser_mobile_android") != null) ? System.getProperty("Browser_mobile_android") : PropertyUtil.getProperties().getProperty("Browser_mobile_android");
    public static String mobileDeviceAndroidVersion = (System.getProperty("Mobile_device_android_version") != null) ? System.getProperty("Mobile_device_android_version") : PropertyUtil.getProperties().getProperty("Mobile_device_android_version");
  /*  public static String AppWaitActivity = "com.upgrad.student.unified.ui.dashboard.activities.UnifiedDashboardActivity";*/
    public static String AppActivity = "com.cheq.retail.ui.splash.SplashActivity";
     public static String AppPackage = "com.cheq.retail.uat";

    //Required for iOS Mobile driver
    public static String mobileDeviceIos = (System.getProperty("Mobile_device_ios") != null) ? System.getProperty("Mobile_device_ios") : PropertyUtil.getProperties().getProperty("Mobile_device_ios");
    public static String mobileBrowserIos = (System.getProperty("Browser_mobile_ios") != null) ? System.getProperty("Browser_mobile_ios") : PropertyUtil.getProperties().getProperty("Browser_mobile_ios");

    // Required if we have chosen Browserstack as our driver
    public static String browserStackSwitch = (System.getProperty("Browserstack_switch") != null) ? System.getProperty("Browserstack_switch") : PropertyUtil.getProperties().getProperty("Browserstack_switch");
    public static String browserStackOs = (System.getProperty("os_BS") != null) ? System.getProperty("os_BS") : PropertyUtil.getProperties().getProperty("os_BS");
    public static String browserStackOsVersion = (System.getProperty("os_version_BS") != null) ? System.getProperty("os_version_BS") : PropertyUtil.getProperties().getProperty("os_version_BS");
    public static String browserStackBrowserVersionChrome = (System.getProperty("browser_version_chrome") != null) ? System.getProperty("browser_version_chrome") : PropertyUtil.getProperties().getProperty("browser_version_chrome");
    public static String browserStackBrowserVersionFireFox = (System.getProperty("browser_version_firefox") != null) ? System.getProperty("browser_version_firefox") : PropertyUtil.getProperties().getProperty("browser_version_firefox");
    public static String browserStackBrowserVersionEdge = (System.getProperty("browser_version_edge") != null) ? System.getProperty("browser_version_edge") : PropertyUtil.getProperties().getProperty("browser_version_edge");
    public static String browserStackLocal = (System.getProperty("Browserstack_local") != null) ? System.getProperty("Browserstack_local") : PropertyUtil.getProperties().getProperty("Browserstack_local");

    //Required for Mobile properties for Browserstack
    public static String browserStackMobileDevice = (System.getProperty("Mobile_device_BS") != null) ? System.getProperty("Mobile_device_BS") : PropertyUtil.getProperties().getProperty("Mobile_device_BS");
    public static String browserStackMobileOsVersion = (System.getProperty("os_version_mobile_BS") != null) ? System.getProperty("os_version_mobile_BS") : PropertyUtil.getProperties().getProperty("os_version_mobile_BS");
    public static String browserStackMobileBrowser = (System.getProperty("Browser_mobile") != null) ? System.getProperty("Browser_mobile") : PropertyUtil.getProperties().getProperty("Browser_mobile");

}
