package driver;

import io.appium.java_client.AppiumDriver;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MobileFactory {
    public static ThreadLocal<IMobDriver> DriverService = new ThreadLocal<>();
    private static Mobiles MobilesDriver;

    @SneakyThrows
    public void setMobileType(String BrowserType) throws Exception {
        MobilesDriver = Mobiles.get(BrowserType);
        System.out.println("Driver to be set is: " + MobilesDriver);
        if (DriverService.get() == null) {
            switch (MobilesDriver) {
                case ANDROID_WEB -> DriverService.set(new AndroidDriverInterface.AndroidMobileWeb());
                case ANDROID_NATIVE -> DriverService.set(new AndroidDriverInterface.AndroidNative());
                default -> {
                    log.info("Wrong choice of browser so quiting the Run");
                }
            }
        }
    }

    @SneakyThrows
    public synchronized AppiumDriver getDriverService() {
        return DriverService.get().get();
    }

    public void quit() throws Exception {
        DriverService.get().stopDriver();
    }

}
