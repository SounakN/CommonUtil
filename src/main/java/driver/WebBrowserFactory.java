package driver;

import org.openqa.selenium.WebDriver;

public class WebBrowserFactory {

    public static ThreadLocal<IDriver> DriverService = new ThreadLocal<>();
    private static Browsers browsers;

    public void setBrowserType(String BrowserType) throws Exception {
        browsers = Browsers.get(BrowserType);
        if (DriverService.get() == null) {
            System.out.print("Thread id :: " + Thread.currentThread().getId() + " got in to the block");
            switch (browsers) {
                case CHROME:
                    DriverService.set(new CHDriver());
                    break;

                case FIREFOX:
                    DriverService.set(new FFDriver());
                    break;

                case EDGE:
                    DriverService.set(new EDGEDriver());
                    break;
                default:
                    break;
            }

        } else {
            System.out.print("Thread id :: " + Thread.currentThread().getId() + " DIDNOT got in to the block");
        }
    }

    public synchronized WebDriver getDriverService() {
        return DriverService.get().get();
    }

    // To stop the driver
    public void quit() throws Exception {
        /*getDriver().quit();*/
        DriverService.get().stopDriver();
    }
}