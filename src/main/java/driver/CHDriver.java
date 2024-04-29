package driver;

import io.cucumber.java.Scenario;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import utilities.PropertyUtil;

import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Properties;

import static driver.BasicConstants.*;


@Slf4j
public class CHDriver implements IDriver {

    private WebDriver driver;
    private Properties prop;
    private String URL;
    private String timeStampIdentifier;
    /*  public Local local;*/
    private HashMap var = new HashMap<String, String>();
    private Scenario scenario;

    public CHDriver(Scenario sc) {
        this.scenario = sc;
        prop = PropertyUtil.getProperties();
        /*AUTOMATE_USERNAME = prop.getProperty("Browserstack_username");
        AUTOMATE_ACCESS_KEY = prop.getProperty("Browserstack_password");
        URL = "https://" + AUTOMATE_USERNAME + ":" + AUTOMATE_ACCESS_KEY + "@hub.browserstack.com/wd/hub";*/
    }

    public CHDriver() {
        prop = PropertyUtil.getProperties();
       /* AUTOMATE_USERNAME = prop.getProperty("Browserstack_username");
        AUTOMATE_ACCESS_KEY = prop.getProperty("Browserstack_password");
        URL = "https://" + AUTOMATE_USERNAME + ":" + AUTOMATE_ACCESS_KEY + "@hub.browserstack.com/wd/hub";*/
    }


    public void startDriver() {
        try {
            if (BasicConstants.browserStackLocal.equalsIgnoreCase("true") && BasicConstants.browserStackSwitch.equals("true")) {
                /*if (l == null) {
                    var.put("key", AUTOMATE_ACCESS_KEY);
                    timeStampIdentifier = System.getenv("BROWSERSTACK_LOCAL_IDENTIFIER") + "_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy.HH.MM.ss"));
                    var.put("localIdentifier", timeStampIdentifier);
                }
                l = new Local();
                l.start(var);*/
            }
            setOptions();
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(PAGE_LOAD_TIME_OUT));
            driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(SCRIPT_LOAD_TIME_OUT));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setOptions() {


        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("disable-popup-blocking");
        options.addArguments("--allow-running-insecure-content");
        options.addArguments("disable-extensions");
        options.addArguments("allow-running-insecure-content");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("allow-file-access-from-files");
        options.addArguments("use-fake-device-for-media-stream");
        options.addArguments("use-fake-ui-for-media-stream");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-gpu");
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        if (incognito.equals("true")) {
            options.addArguments("--incognito");
        }

        if (headless.equals("true")) {
            options.addArguments("--headless");
        }


        if (IsRemote.equals("true")) {
            try {
                driver = new RemoteWebDriver(new URL(prop.getProperty("RemoteURL")), options);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (browserStackSwitch.equals("true")) {
            DesiredCapabilities cap = new DesiredCapabilities();
            cap.setCapability("browser_version", browserStackBrowserVersionChrome);
            cap.setCapability("build", System.getenv("BROWSERSTACK_BUILD_NAME"));
            cap.setCapability("os", BasicConstants.browserStackOs);
            cap.setCapability("os_version", BasicConstants.browserStackOsVersion);
            cap.setCapability("browserstack.local", BasicConstants.browserStackLocal);
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
                driver = new ChromeDriver(options);
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


    public void stopDriver() {
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