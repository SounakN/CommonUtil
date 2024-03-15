package driver;


import utilities.EnvUtil;

public class BasicConstants {

	public static final int PAGE_LOAD_TIME_OUT = 120;
	public static final int FIREFOX_PAGE_LOAD_TIME_OUT = 120;
	public static final int IMPLICIT_WAIT_TIMEOUT = 60;
	public static final int EXPLICIT_WAIT_TIMEOUT = 30;

	public static String isMaximized = (System.getProperty("isMaximized")!=null)?System.getProperty("isMaximized"): EnvUtil.getProperties().getProperty("isMaximized");
	public static String incognito = (System.getProperty("incognito")!=null)?System.getProperty("incognito"):EnvUtil.getProperties().getProperty("incognito");
	public static String headless = (System.getProperty("headless")!=null)?System.getProperty("headless"):EnvUtil.getProperties().getProperty("headless");
	public static String IsRemote = (System.getProperty("IsRemote")!=null)?System.getProperty("IsRemote"):EnvUtil.getProperties().getProperty("IsRemote");

	// Required if we have chosen Browserstack as our driver
	public static String Browserstack_switch = (System.getProperty("Browserstack_switch")!=null)?System.getProperty("Browserstack_switch"):EnvUtil.getProperties().getProperty("Browserstack_switch");
	public static String os = (System.getProperty("os")!=null)?System.getProperty("os"):EnvUtil.getProperties().getProperty("os");
	public static String os_version = (System.getProperty("os_version")!=null)?System.getProperty("os_version"):EnvUtil.getProperties().getProperty("os_version");
	public static String Browser = (System.getProperty("Browser")!=null)?System.getProperty("Browser"):EnvUtil.getProperties().getProperty("Browser");
	public static String browser_version_chrome = (System.getProperty("browser_version_chrome")!=null)?System.getProperty("browser_version_chrome"):EnvUtil.getProperties().getProperty("browser_version_chrome");
	public static String browser_version_firefox = (System.getProperty("browser_version_firefox")!=null)?System.getProperty("browser_version_firefox"):EnvUtil.getProperties().getProperty("browser_version_firefox");
	public static String browser_version_edge = (System.getProperty("browser_version_edge")!=null)?System.getProperty("browser_version_edge"):EnvUtil.getProperties().getProperty("browser_version_edge");
	public static String browserstack_local = (System.getProperty("Browserstack_local")!=null)?System.getProperty("Browserstack_local"):EnvUtil.getProperties().getProperty("Browserstack_local");


}
