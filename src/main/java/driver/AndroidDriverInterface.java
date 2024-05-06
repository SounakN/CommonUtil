package driver;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.Setting;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.openqa.selenium.Platform;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import utilities.ActionMethods;
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
import static org.openqa.selenium.remote.CapabilityType.PLATFORM_NAME;
import static utilities.ActionMethods.MobileActions.appiumServiceBuilder;


public interface AndroidDriverInterface extends IMobDriver {


    void startDriver();

    void stopDriver() throws Exception;

    AppiumDriver get();

    void setOptions() throws MalformedURLException;


    @Slf4j
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
            try{
                if (browserStackSwitch.equals("false")) {
                    appiumServiceBuilder(service);
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
            }catch(Exception e){
                log.info("The test case is failing during driver appium service builder initiation/ BrowserStack local initiation {} : ",e.getMessage());
                Assert.fail();
            }
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
                caps.setCapability("browser", browserStackMobileBrowser);
                caps.setCapability("browserstack.networkProfile", "4g-lte-good");
                caps.setCapability("browserstack.idleTimeout", "120");
                caps.setCapability("browserstack.local", browserStackLocal);
                caps.setCapability("browserstack.acceptInsecureCerts", "true");
                caps.setCapability("browserstack.appium_version", "1.22.0");
                /*caps.setCapability("browserstack.localIdentifier", Timestampidentifier);*/
                try {
                    driver = new AppiumDriver(new URL(URL), caps);
                } catch (Exception e) {
                    log.info("The test case is failing during driver initiation {} : ",e.getMessage());
                    Assert.fail();
                }

            } else {
                DesiredCapabilities capabilities = new DesiredCapabilities();
                capabilities.setCapability(PLATFORM_NAME, ANDROID_PLATFORM_NAME);
                capabilities.setCapability("platformVersion", mobileDeviceAndroidVersion);
                capabilities.setCapability("deviceName", mobileDeviceAndroid);
                capabilities.setCapability("automationName", ANDROID_AUTOMATION_NAME);
                capabilities.setCapability("noReset", "false");
                capabilities.setCapability("autoGrantPermissions", true);
                capabilities.setCapability("newCommandTimeout", APPIUM_COMMAND_TIMEOUT);
                capabilities.setCapability("browserName", mobileBrowserAndroid);
                capabilities.setCapability("nativeWebScreenshot", "true");
                try {
                    driver = new AndroidDriver(service.getUrl(), capabilities);
                    driver.setSetting(Setting.WAIT_FOR_IDLE_TIMEOUT, 100);
                } catch (Exception e) {
                    log.info("The test case is failing during driver initiation {} : ",e.getMessage());
                    Assert.fail();
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
                log.info("The test case has failed during driver quitting {} : ",e.getMessage());
                Assert.fail();
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

    @Slf4j
    class AndroidNative implements AndroidDriverInterface {

        public static String AUTOMATE_USERNAME;
        public static String AUTOMATE_ACCESS_KEY;
        public static String URL;
        /*    private Local l;*/
        private String timeStampIdentifier;
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
                    service = appiumServiceBuilder(service);
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
                log.info("The test case is failing during driver appium service builder initiation/ BrowserStack local initiation {} : ",e.getMessage());
                Assert.fail();
            }
        }

        @Override
        public void setOptions()  {

            if (browserStackSwitch.equals("true")) {
                DesiredCapabilities caps = new DesiredCapabilities();

                caps.setCapability("os_version", browserStackMobileOsVersion);
                caps.setCapability("device", browserStackMobileDevice);
                caps.setCapability("build", System.getenv("BROWSERSTACK_BUILD_NAME"));
                caps.setCapability("real_mobile", "true");
                caps.setCapability("browserstack.console", "errors");
                caps.setCapability("browserstack.networkLogs", "true");
                caps.setCapability("app", prop.getProperty("Browserstack_Android_APP_Hashcode"));
                caps.setCapability("browserstack.idleTimeout", "120");
                caps.setCapability("browserstack.networkProfile", "4g-lte-good");
                caps.setCapability("browserstack.local", browserStackLocal);
                caps.setCapability("browserstack.acceptInsecureCerts", "true");
                caps.setCapability("autoGrantPermissions", "true");
                /*  caps.setCapability("browserstack.localIdentifier", timeStampIdentifier);*/
                caps.setCapability("browserstack.appium_version", "1.22.0");

                try {
                    driver = new AppiumDriver(new URL(URL), caps);
                } catch (Exception e) {
                    log.info("The test case is failing during driver initiation {} : ",e.getMessage());
                    Assert.fail();
                }

            } else {
                Path pathRoot = new File(Objects.requireNonNull(AndroidDriverInterface.class.getClassLoader().getResource("")).getFile()).getParentFile().getParentFile().toPath();
                Path pathToApp = Paths.get(pathRoot.toString(), "src/test/resources/App",ANDROID_PLATFORM_NAME ,prop.getProperty("apkType"), prop.getProperty("Mobile_app_name_android"));
                log.info("The path to the App : {}",pathToApp);
                DesiredCapabilities capabilities = new DesiredCapabilities();
                capabilities.setCapability(PLATFORM_NAME, ANDROID_PLATFORM_NAME);
                capabilities.setCapability("deviceName", mobileDeviceAndroid);
                capabilities.setCapability("automationName", ANDROID_AUTOMATION_NAME);
                capabilities.setCapability("noReset", "false");
                capabilities.setCapability("autoGrantPermissions", true);
                capabilities.setCapability("newCommandTimeout", APPIUM_COMMAND_TIMEOUT);
                //Specific to the applications
                capabilities.setCapability("app", pathToApp.toString());
                capabilities.setCapability("appPackage", BasicConstants.AppPackage);
                capabilities.setCapability("appActivity", AppActivity);
                try {
                    driver = new AndroidDriver(service.getUrl(), capabilities);
                    driver.setSetting(Setting.WAIT_FOR_IDLE_TIMEOUT, 100);
                } catch (Exception e) {
                    log.info("The test case is failing during driver initiation {} : ",e.getMessage());
                    Assert.fail();
                }
            }
        }

        public void stopDriver() throws Exception {
            try {
                driver.quit();
                if (browserStackSwitch.equalsIgnoreCase("false")) {
                    service.stop();
                }
            } catch (Exception e) {
                log.info("The test case has failed during driver quitting {} : ",e.getMessage());
                Assert.fail();
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
