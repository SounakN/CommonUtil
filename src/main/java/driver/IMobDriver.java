package driver;


import io.appium.java_client.AppiumDriver;

import java.net.MalformedURLException;

public interface IMobDriver {

    void startDriver() throws Exception;

    void stopDriver() throws Exception;

    AppiumDriver get() throws Exception;

    void setOptions() throws MalformedURLException;
}
