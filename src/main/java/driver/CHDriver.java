package driver;

import io.cucumber.java.Scenario;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import utilities.PropertyUtil;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


@SuppressWarnings("unused")
public
class CHDriver implements IDriver {


    private WebDriver driver = null;
    private Properties prop = null;
    private String AUTOMATE_USERNAME = null;
    private String AUTOMATE_ACCESS_KEY = null;
    private String URL = null;
    /*private  Local l;*/
    private String Timestampidentifier = null;
    private HashMap var = new HashMap<String, String>();
    private Scenario scenario;

    public CHDriver(Scenario sc) {
        this.scenario = sc;
        prop = PropertyUtil.getProperties();
        AUTOMATE_USERNAME = prop.getProperty("Browserstack_username");
        AUTOMATE_ACCESS_KEY = prop.getProperty("Browserstack_password");
        URL = "https://" + AUTOMATE_USERNAME + ":" + AUTOMATE_ACCESS_KEY + "@hub.browserstack.com/wd/hub";
    }

    public CHDriver() {
        prop = PropertyUtil.getProperties();
       /* AUTOMATE_USERNAME = prop.getProperty("Browserstack_username");
        AUTOMATE_ACCESS_KEY = prop.getProperty("Browserstack_password");
        URL = "https://" + AUTOMATE_USERNAME + ":" + AUTOMATE_ACCESS_KEY + "@hub.browserstack.com/wd/hub";*/
    }


    public void startDriver() {
        try {
            if (BasicConstants.browserstack_local.equalsIgnoreCase("true") && BasicConstants.Browserstack_switch.equals("true")) {

				/*if(l==null)
				{
					var.put("key", AUTOMATE_ACCESS_KEY);
					Timestampidentifier = System.getenv("BROWSERSTACK_LOCAL_IDENTIFIER")+"_"+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy.HH.MM.ss"));
					var.put("localIdentifier", Timestampidentifier);
				}
				l = new Local();
				l.start(var);*/
            } else {
                /*WebDriverManager.chromedriver().setup();*/
            }
            setOptions();
            driver.manage().timeouts().pageLoadTimeout(BasicConstants.PAGE_LOAD_TIME_OUT, TimeUnit.SECONDS);
            driver.manage().timeouts().implicitlyWait(BasicConstants.IMPLICIT_WAIT_TIMEOUT_GENERIC, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setOptions() throws MalformedURLException {


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
        if (BasicConstants.incognito.equals("true")) {
            options.addArguments("--incognito");
        }

        if (BasicConstants.headless.equals("true")) {
            options.addArguments("--headless");
        }


        if (BasicConstants.IsRemote.equals("true")) {
            try {
                driver = new RemoteWebDriver(new
                        URL(prop.getProperty("RemoteURL")), options);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (BasicConstants.Browserstack_switch.equals("true")) {
            DesiredCapabilities cap = new DesiredCapabilities();
            cap.setCapability("browser_version", BasicConstants.browser_version_chrome);
            cap.setCapability("build", System.getenv("BROWSERSTACK_BUILD_NAME"));
            cap.setCapability("name", scenario.getName());
            cap.setCapability("os", BasicConstants.os);
            cap.setCapability("os_version", BasicConstants.os_version);
            cap.setCapability("browserstack.local", BasicConstants.browserstack_local);
            cap.setCapability("browserstack.console", "info");
            HashMap<String, Boolean> networkLogsOptions = new HashMap<>();
            networkLogsOptions.put("captureContent", true);
            cap.setCapability("browserstack.networkLogs", true);
            cap.setCapability("browserstack.networkLogsOptions", networkLogsOptions);
            cap.setCapability("browserstack.localIdentifier", Timestampidentifier);

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

    // To stop the driver
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

    // To start the driver

    public WebDriver get() {

        if (null == driver) {
            this.startDriver();
        }
        return driver;
    }

}