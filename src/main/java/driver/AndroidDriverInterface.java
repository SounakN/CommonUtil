package driver;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.Setting;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import lombok.SneakyThrows;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.remote.DesiredCapabilities;
import utilities.PropertyUtil;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.HashMap;
import java.util.Objects;
import java.util.Properties;

import static driver.BasicConstants.*;

public interface AndroidDriverInterface extends IMobDriver {


    void startDriver();

    void stopDriver() throws Exception;

    AppiumDriver get();

    void setOptions() throws MalformedURLException;


    class AndroidMobileWeb implements AndroidDriverInterface {
        public static String URL;
        /*private Local l;*/
        public AppiumDriver driver;
        public static AppiumDriverLocalService service;
        public static Properties prop;
        private HashMap var = new HashMap<String, String>();

        public AndroidMobileWeb() {
            prop = PropertyUtil.getProperties();
          /*  AUTOMATE_USERNAME = prop.getProperty("Browserstack_username");
            AUTOMATE_ACCESS_KEY = prop.getProperty("Browserstack_password");
            URL = "https://" + AUTOMATE_USERNAME + ":" + AUTOMATE_ACCESS_KEY + "@hub.browserstack.com/wd/hub";*/
        }


        @SneakyThrows
        public void startDriver() {

            if (BasicConstants.browserStackSwitch.equals("false")) {
                if (service == null) {
                    service = AppiumDriverLocalService.buildService(new AppiumServiceBuilder().usingAnyFreePort().withArgument(() -> "--allow-insecure", "chromedriver_autodownload"));
                }
                service.start();
            }/*else if(BasicConstants.browserstack_local.equalsIgnoreCase("true") && BasicConstants.Browserstack_switch.equals("true") ){

                    if(l==null)
                    {
                        var.put("key", AUTOMATE_ACCESS_KEY);
                        Timestampidentifier = System.getenv("BROWSERSTACK_LOCAL_IDENTIFIER")+"_"+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy.HH.MM.ss"));
                        var.put("localIdentifier", Timestampidentifier);

                    }
                    l = new Local();
                    l.start(var);
                }*/
            setOptions();
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(PAGE_LOAD_TIME_OUT));
            driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(SCRIPT_LOAD_TIME_OUT));

        }

        @Override
        public void setOptions() {
            if (BasicConstants.browserStackSwitch.equals("true")) {
                DesiredCapabilities caps = new DesiredCapabilities();
                caps.setCapability("os_version", browserStackMobileOsVersion);
                caps.setCapability("device", browserStackMobileDevice);
                caps.setCapability("build", System.getenv("BROWSERSTACK_BUILD_NAME"));
                caps.setCapability("real_mobile", "true");
                caps.setCapability("browserstack.console", "errors");
                caps.setCapability("browserstack.networkLogs", "true");
                caps.setCapability("browser", BasicConstants.browserStackMobileBrowser);
                caps.setCapability("browserstack.networkProfile", "4g-lte-good");
                caps.setCapability("browserstack.local", BasicConstants.browserStackLocal);
                /*caps.setCapability("browserstack.localIdentifier", Timestampidentifier);*/
                try {
                    driver = new AppiumDriver(new URL(URL), caps);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            } else {
                DesiredCapabilities capabilities = new DesiredCapabilities();
                capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, ANDROID_PLATFORM_NAME);
                capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, mobileDeviceAndroidVersion);
                capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, mobileDeviceAndroid);
                capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, ANDROID_AUTOMATION_NAME);
                capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, mobileBrowserAndroid);
                capabilities.setCapability("nativeWebScreenshot", "true");
                capabilities.setCapability("noReset", "true");
                capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, APPIUM_COMMAND_TIMEOUT);
                try {
                    driver = new AndroidDriver(AndroidMobileWeb.service.getUrl(), capabilities);
                   /* URL url =new URL("http://127.0.0.1:4723/wd/hub");
                    driver= new AndroidDriver(url,capabilities);*/
                    //driver.setSetting(Setting.WAIT_FOR_IDLE_TIMEOUT, 300);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }

        public void stopDriver() throws Exception {
            try {
                driver.quit();
                if (BasicConstants.browserStackSwitch.equalsIgnoreCase("false")) {
                    service.stop();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            } finally {
                driver = null;
               /* if (BasicConstants.browserstack_local.equalsIgnoreCase("true") && BasicConstants.Browserstack_switch.equals("true")) {
                    l.stop();
                }*/

            }
        }

        public AppiumDriver get() {

            if (null == driver) {
                this.startDriver();
            }
            return driver;
        }

    }

    class AndroidNative implements AndroidDriverInterface {

        public static String AUTOMATE_USERNAME;
        public static String AUTOMATE_ACCESS_KEY;
        public static String URL;
        /*    private Local l;*/
        private String timeStampIdentifier = null;
        private HashMap var = new HashMap<String, String>();
        public AppiumDriver driver;
        public static AppiumDriverLocalService service;
        public static Properties prop = null;

        public AndroidNative() {
            prop = PropertyUtil.getProperties();
           /* AUTOMATE_USERNAME = prop.getProperty("Browserstack_username");
            AUTOMATE_ACCESS_KEY = prop.getProperty("Browserstack_password");
            URL = "https://" + AUTOMATE_USERNAME + ":" + AUTOMATE_ACCESS_KEY + "@hub.browserstack.com/wd/hub";*/
        }


        public void startDriver() {
            try {
                if (browserStackSwitch.equals("false")) {
                    if (service == null) {
                        service = AppiumDriverLocalService.buildService(new AppiumServiceBuilder().usingAnyFreePort().withArgument(() -> "--allow-insecure", "chromedriver_autodownload"));
                    }
                    service.start();
                } /*else if (BasicConstants.browserstack_local.equalsIgnoreCase("true") && BasicConstants.Browserstack_switch.equals("true")) {
                    if (l == null) {
                        var.put("key", AUTOMATE_ACCESS_KEY);
                        Timestampidentifier = System.getenv("BROWSERSTACK_LOCAL_IDENTIFIER") + "_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy.HH.MM.ss"));
                        var.put("localIdentifier", Timestampidentifier);

                    }
                    l = new Local();
                    l.start(var);
                }*/
                setOptions();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void setOptions() throws MalformedURLException {

            if (BasicConstants.browserStackSwitch.equals("true")) {
                DesiredCapabilities caps = new DesiredCapabilities();

                //For Browserstack
                caps.setCapability("os_version", browserStackMobileOsVersion);
                caps.setCapability("device", BasicConstants.browserStackMobileDevice);
                caps.setCapability("build", System.getenv("BROWSERSTACK_BUILD_NAME"));
                caps.setCapability("real_mobile", "true");
                caps.setCapability("browserstack.console", "errors");
                caps.setCapability("browserstack.networkLogs", "true");
                caps.setCapability("app", prop.getProperty("Browserstack_Android_APP_Hashcode"));
                caps.setCapability("browserstack.idleTimeout", "120");
                caps.setCapability("browserstack.networkProfile", "4g-lte-good");
                caps.setCapability("browserstack.local", browserStackLocal);
                caps.setCapability("autoGrantPermissions", "true");
                caps.setCapability("browserstack.localIdentifier", timeStampIdentifier);
                caps.setCapability("browserstack.appium_version", "1.22.0");

                try {
                    driver = new AppiumDriver(new URL(URL), caps);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                DesiredCapabilities capabilities = new DesiredCapabilities();
                capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, mobileDeviceAndroid);
                capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, ANDROID_AUTOMATION_NAME);
                Path pathRoot = new File(Objects.requireNonNull(AndroidDriverInterface.class.getClassLoader().getResource("")).getFile()).toPath();
                Path pathToApp = Paths.get(pathRoot.toString(), "APK", prop.getProperty("apkType"), prop.getProperty("Mobile_app_name_android"));
                capabilities.setCapability(MobileCapabilityType.APP, pathToApp.toString());
                /*       capabilities.setCapability("appPackage", BasicConstants.AppPackage);*/
                //capabilities.setCapability("appWaitActivity",BasicConstants.AppWaitActivity);
                capabilities.setCapability(MobileCapabilityType.UNHANDLED_PROMPT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
                capabilities.setCapability("autoGrantPermissions", true);
                capabilities.setCapability("locationServiceAuthorized", true);
                try {
                    driver = new AndroidDriver(AndroidNative.service.getUrl(), capabilities);
                    driver.setSetting(Setting.WAIT_FOR_IDLE_TIMEOUT, 100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public void stopDriver() throws Exception {
            try {
                driver.quit();
                if (BasicConstants.browserStackSwitch.equalsIgnoreCase("false")) {
                    service.stop();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            } finally {
                driver = null;
               /* if (BasicConstants.browserstack_local.equalsIgnoreCase("true") && BasicConstants.Browserstack_switch.equals("true")) {
                    l.stop();
                }
*/
            }
        }

        public AppiumDriver get() {
            if (null == driver) {
                this.startDriver();
            }
            return driver;
        }
    }
}
