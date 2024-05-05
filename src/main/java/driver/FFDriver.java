package driver;


import io.cucumber.java.Scenario;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import utilities.PropertyUtil;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Properties;

import static driver.BasicConstants.*;


@SuppressWarnings("unused")
public class FFDriver implements IDriver {

    public WebDriver driver;
    public Properties prop;
    public String URL = null;
    /*private  Local l;*/
    private HashMap var = new HashMap<String, String>();
    private String timeStampIdentifier = null;
    private Scenario scenario;

    public FFDriver(Scenario sc) {
        this.scenario = sc;
        prop = PropertyUtil.getProperties();
        /*AUTOMATE_USERNAME = prop.getProperty("Browserstack_username");
        AUTOMATE_ACCESS_KEY = prop.getProperty("Browserstack_password");
        URL = "https://" + AUTOMATE_USERNAME + ":" + AUTOMATE_ACCESS_KEY + "@hub.browserstack.com/wd/hub";*/
    }

    public FFDriver() {
        prop = PropertyUtil.getProperties();
       /* AUTOMATE_USERNAME = prop.getProperty("Browserstack_username");
        AUTOMATE_ACCESS_KEY = prop.getProperty("Browserstack_password");
        URL = "https://" + AUTOMATE_USERNAME + ":" + AUTOMATE_ACCESS_KEY + "@hub.browserstack.com/wd/hub";*/
    }

    public void startDriver() {
        try {
            /*if (BasicConstants.browserstack_local.equalsIgnoreCase("true") && BasicConstants.Browserstack_switch.equals("true")) {
                if (l == null) {
                    var.put("key", AUTOMATE_ACCESS_KEY);
                    timeStampIdentifier = System.getenv("BROWSERSTACK_LOCAL_IDENTIFIER") + "_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy.HH.MM.ss"));
                    var.put("localIdentifier", timeStampIndentifier);
                }
                l = new Local();
                l.start(var);
            }*/
            setOptions();
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(PAGE_LOAD_TIME_OUT));
            driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(SCRIPT_LOAD_TIME_OUT));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setOptions() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("marionette", true);
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addPreference("permissions.default.microphone", 1);
        options.addPreference("permissions.default.camera", 1);
        if (BasicConstants.incognito.equals("true")) {
            options.addArguments("-private");
        }
       /* if (BasicConstants.headless.equals("true")) {
            options.setHeadless(true);
        }*/
        if (BasicConstants.IsRemote.equals("true")) {
            capabilities.setBrowserName("firefox");
            options.merge(capabilities);
            try {
                driver = new RemoteWebDriver(new
                        URL(prop.getProperty("RemoteURL")), options);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (BasicConstants.browserStackSwitch.equals("true")) {
            DesiredCapabilities cap = new DesiredCapabilities();
            cap.setCapability("browser_version", browserStackBrowserVersionFireFox);
            cap.setCapability("build", System.getenv("BROWSERSTACK_BUILD_NAME"));
            cap.setCapability("name", scenario.getName());
            cap.setCapability("os", browserStackOs);
            cap.setCapability("os_version", browserStackOsVersion);
            cap.setCapability("browserstack.local", browserStackLocal);
            cap.setCapability("browserstack.console", "info");
            HashMap<String, Boolean> networkLogsOptions = new HashMap<>();
            networkLogsOptions.put("captureContent", true);
            cap.setCapability("browserstack.networkLogs", true);
            cap.setCapability("browserstack.networkLogsOptions", networkLogsOptions);
            cap.setCapability("browserstack.localIdentifier", timeStampIdentifier);
            options.merge(cap);
            try {
                driver = new RemoteWebDriver(new URL(URL), options);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                driver = new FirefoxDriver(options);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        if (BasicConstants.isMaximized.equals("true")) {
            if (BasicConstants.headless.equals("true")) {
                driver.manage().window().setSize(new Dimension(1440, 900));
            } else {
                driver.manage().window().maximize();
            }
        }
    }

    public void stopDriver() throws Exception {
        try {
            driver.quit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            driver = null;
			/*if (BasicConstants.browserstack_local.equalsIgnoreCase("true") && BasicConstants.Browserstack_switch.equals("true")) {
				l.stop();
			}*/

        }
    }

    public WebDriver get() {

        if (null == driver) {
            this.startDriver();
        }
        return driver;
    }

}