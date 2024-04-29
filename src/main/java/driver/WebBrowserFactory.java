package driver;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;

@Slf4j
public class WebBrowserFactory {

    public static ThreadLocal<IDriver> DriverService = new ThreadLocal<>();
    private static Browsers browsers;

    @SneakyThrows
    public void setBrowserType(String BrowserType) {
        browsers = Browsers.get(BrowserType);
        if (DriverService.get() == null) {
            switch (browsers) {
                case CHROME -> DriverService.set(new CHDriver());
                case FIREFOX -> DriverService.set(new FFDriver());
                case EDGE -> DriverService.set(new EDGEDriver());
                default -> {
                    log.info("Wrong choice of mobile Type so quiting the Run");
                }
            }
        }
    }

    public synchronized WebDriver getDriverService() {
        return DriverService.get().get();
    }

    public void quit() throws Exception {
        DriverService.get().stopDriver();
        log.info("Driver stopped");
    }
}