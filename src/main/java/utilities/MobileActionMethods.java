package utilities;

import driver.MobileFactory;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.connection.ConnectionState;
import io.appium.java_client.android.connection.ConnectionStateBuilder;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.touch.TapOptions;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.*;

import static driver.BasicConstants.EXPLICIT_WAIT_TIMEOUT_GENERIC;
import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;
import static io.appium.java_client.touch.offset.PointOption.point;
import static java.time.Duration.ofMillis;
import static java.time.Duration.ofSeconds;


@Slf4j
public class MobileActionMethods<T extends WebDriver> {
    public boolean waitForElementVisible(By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(MobileFactory.getDriverService(),
                    ofSeconds(EXPLICIT_WAIT_TIMEOUT_GENERIC));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean waitForPresenceOfElementInPage(By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(MobileFactory.getDriverService(),
                    ofSeconds(EXPLICIT_WAIT_TIMEOUT_GENERIC));
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return true;
        } catch (TimeoutException ex) {
            return false;
        }
    }

    public void waitForElementDisplay(By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(MobileFactory.getDriverService(), ofSeconds(EXPLICIT_WAIT_TIMEOUT_GENERIC));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException ex) {
            ex.printStackTrace();
        }
    }
    public boolean isElementDisplayed(By locate) {
        try {
            return waitForElementVisible(locate);
        } catch (Exception ex) {
            return false;
        }
    }
    public void clickElement(By locator) {
        tapByElement(locator);
    }

    public void clickElement(WebElement Element) {
        tapByElement(Element);
    }
    

    public void clickElementFromDropDown(String text, By locator) {
        List<WebElement> elements = MobileFactory.getDriverService().findElements(locator);
        for (WebElement element : elements) {
            if (element.getAttribute("text").equalsIgnoreCase(text)) {
                // element.click();
                new TouchAction((PerformsTouchActions) MobileFactory.getDriverService())
                        .tap(TapOptions.tapOptions().withElement(element(element)))
                        .waitAction(waitOptions(ofMillis(250))).perform();
                break;
            }
        }
    }



    public Boolean TapByPoint(WebElement elem, double x, double y) {
        try {
            Rectangle elRect = elem.getRect();
            int x1 = (int) (elRect.x * x);
            int y1 = (int) (elRect.y * y);
       /*     int Height = elRect.height;
            int width = elRect.width;
            Point x2 = elem.getLocation();
            int x3 = x2.x;
            int y2 = x2.y;*/


            new TouchAction((PerformsTouchActions) MobileFactory.getDriverService())
                    .tap(TapOptions.tapOptions().withPosition(point(x1, y1))).waitAction(waitOptions(ofMillis(250)))
                    .perform();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }







    public void androidWifiOn() {
        ((AndroidDriver) MobileFactory.getDriverService()).setConnection(new ConnectionStateBuilder().withWiFiEnabled().withDataEnabled().build());
    }

    public void androidWifiOff() {
        ConnectionState state = ((AndroidDriver) MobileFactory.getDriverService()).setConnection(new ConnectionStateBuilder().withWiFiDisabled().build());

    }

    public void switchToWebViewAndroid() {
        Set<String> contexts = ((AndroidDriver) MobileFactory.getDriverService()).getContextHandles();
        for (String context : contexts) {
            if (!context.equals("NATIVE_APP")) {
                ((AndroidDriver) MobileFactory.getDriverService()).context((String) contexts.toArray()[1]);
                break;
            }
        }
    }

    public void switchToWebViewIosDriver() {
        Set<String> contexts = ((IOSDriver) MobileFactory.getDriverService()).getContextHandles();
        for (String context : contexts) {
            if (!context.equals("NATIVE_APP")) {
                ((IOSDriver) MobileFactory.getDriverService()).context((String) contexts.toArray()[1]);
                break;
            }
        }
    }






}