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
    

    public int getSize(By locate) {
        return MobileFactory.getDriverService().findElements(locate).size();
    }
    
    public boolean isAlertPresent() throws IOException {
        boolean foundAlert;
        WebDriverWait wait = new WebDriverWait(MobileFactory.getDriverService(),
                ofSeconds(EXPLICIT_WAIT_TIMEOUT_GENERIC));
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            foundAlert = true;
        } catch (TimeoutException eTO) {
            foundAlert = false;
        }
        return foundAlert;

    }

    public void acceptAlert() throws IOException {
        if (isAlertPresent()) {
            MobileFactory.getDriverService().switchTo().alert().accept();
        }
    }


    public void dismissAlert() throws IOException {
        if (isAlertPresent()) {
            MobileFactory.getDriverService().switchTo().alert().dismiss();
        }
    }

    //method to get x,y of webelement
    public Point getLocation(By locator) {
        Point point;
        try {
            point = MobileFactory.getDriverService().findElement(locator).getLocation();
            return point;
        } catch (ElementNotInteractableException | NoSuchElementException ex) {
            ex.printStackTrace();
            return null;

        }
    }
    

    //method to get dimension of device
    public Dimension getSizeWindows() {
        Dimension dimension;
        try {
            dimension = MobileFactory.getDriverService().manage().window().getSize();
            return dimension;

        } catch (ElementNotInteractableException | NoSuchElementException ex) {
            ex.printStackTrace();
            return null;
        }
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


    public void swipeRightToLeftFromElement(By locator) throws IOException {
        Point point = getLocation(locator);
        int startX = (int) (point.getX() * 0.95);
        int endX = (int) (point.getX() * 0.05);
        int startY = point.getY();
        scroll(startX, startY, endX, startY);
    }

    public void swipeLeftToRightFromElement(By locator) throws IOException {
        Point point = getLocation(locator);
        int startX = (int) (point.getX() * 0.95);
        int endX = (int) (point.getX() * 0.05);
        int startY = point.getY();
        scroll(endX, startY, startX, startY);
    }


    public void swipeRightToLeft(T driver) {
        Dimension size = driver.manage().window().getSize();
        int startY = (int) (size.height / 2);
        int startX = (int) (size.width * 0.60);
        int endX = (int) (size.width * 0.05);
        scroll(startX, startY, endX, startY);
    }

    public void scroll(int startX, int startY, int endX, int endY) {
        TouchAction touchAction = new TouchAction((PerformsTouchActions) MobileFactory.getDriverService());
        touchAction.press(point(startX, startY))
                .waitAction(waitOptions(ofSeconds(1)))
                .moveTo(point(endX, endY))
                .release()
                .perform();
    }
    

    @SneakyThrows
    public void scrollVerticallyTopToBottom(){
        Dimension dimension = getSizeWindows();
        int endY = (int) (dimension.getHeight() * 0.7);
        int startX = dimension.getWidth() / 2;
        int startY = (int) (dimension.getHeight() * 0.40);
        scroll(startX, startY, startX, endY);

    }

    public void scrollVerticallyBottomToTop() throws IOException {
        Dimension dimension = getSizeWindows();
        int endY = (int) (dimension.getHeight() * 0.4);
        int startX = dimension.getWidth() / 2;
        int startY = (int) (dimension.getHeight() * 0.7);
        scroll(startX, startY, startX, endY);

    }

    //Pass webElement, By Locator, Count of scrolling and true if you want to Scroll Up or false if you want to scroll Down
    @SneakyThrows
    public Boolean scrollTillFoundUsingElement(WebElement elem, By loc, int times, Boolean check,T driver){
        int counter = 0;
        while (elem == null) {
            try {
                counter++;
                if (counter <= times) {
                    if (check) {
                        scrollVerticallyTopToBottom();
                    } else {
                        scrollVerticallyBottomToTop();
                    }
                    Thread.sleep(1000);
                } else {
                    break;
                }
                elem = driver.findElement(loc);
            } catch (org.openqa.selenium.NoSuchElementException e) {
                log.info("Scrolled  for :: "+counter+" time");
            }
        }

        return elem.isDisplayed();
    }

    public void scrollVerticallyWithCoordinate(double yStart, double y_end){
        Dimension dimension = getSizeWindows();
        int startY = (int) (dimension.getHeight() * yStart);
        int startX = dimension.getWidth() / 2;
        int endY = (int) (dimension.getHeight() * y_end);
        scroll(startX, startY, startX, endY);
    }

    public void scrollVerticallyTopToBottomLarge() {
        Dimension dimension = getSizeWindows();
        int startY = (int) (dimension.getHeight() * 0.09);
        int startX = dimension.getWidth() / 2;
        int endY = (int) (dimension.getHeight() * 0.7);
        scroll(startX, startY, startX, endY);
    }

    public void scrollVerticallyBottomToTopLarge() throws IOException {

        Dimension dimension = getSizeWindows();
        int startY = (int) (dimension.getHeight() * 0.7);
        int startX = dimension.getWidth() / 2;
        int endY = (int) (dimension.getHeight() * 0.09);
        scroll(startX, startY, startX, endY);
    }

    @SneakyThrows
    public boolean scrollVerticallyUntilSingleElementFound(By locator, int times) {
        boolean flag = false;
        int count = 0;
        while (getSize(locator) == 0) {
            scrollVerticallyBottomToTopLarge();
            count++;
            if (count > times) {
                break;
            }
        }

        if (getSize(locator) > 0) {
            flag = true;
        }
        return flag;
    }


    public void androidWifiOn() {
        ((AndroidDriver) MobileFactory.getDriverService()).setConnection(new ConnectionStateBuilder().withWiFiEnabled().withDataEnabled().build());
    }

    public void androidWifiOff() {
        ConnectionState state = ((AndroidDriver) MobileFactory.getDriverService()).setConnection(new ConnectionStateBuilder().withWiFiDisabled().build());

    }

    public void sendKeys(By locator, String key) {
        clickElement(locator);
        MobileFactory.getDriverService().findElement(locator).sendKeys(key);
    }

    public void sendKeys(WebElement Elem, String key) {
        tapByElement(Elem);
        Elem.sendKeys(key);
    }

    public List<Integer> convertOTPTOList(String OTP) {
        List<Integer> otp = new LinkedList<>();
        int otpconvertor = Integer.parseInt(OTP);
        int digit = 0;
        while (otpconvertor > 0) {
            digit = otpconvertor % 10;
            otp.add(digit);
            otpconvertor = otpconvertor / 10;
        }
        return otp;
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

    public void tapByElement(By locator) {
        waitForElementDisplay(locator);
        WebElement element = ActionMethods.findElement(locator, MobileFactory.getDriverService(), ofSeconds(10), ofSeconds(1));
        if (element != null) {
            new TouchAction((PerformsTouchActions) MobileFactory.getDriverService())
                    .tap(TapOptions.tapOptions().withElement(element(element)))
                    .waitAction(waitOptions(ofMillis(250))).perform();
        } else {
            throw new NoSuchElementException("While tapping we couldnot locate the element");
        }

    }

    public void tapByElement(WebElement Elem) {
        new TouchAction((PerformsTouchActions) MobileFactory.getDriverService())
                .tap(TapOptions.tapOptions().withElement(element(Elem)))
                .waitAction(waitOptions(ofMillis(250))).perform();
    }

    public void tapByCoordinates(By locator) {
        waitForElementVisible(locator);
        Point point = getLocation(locator);
        new TouchAction((PerformsTouchActions) MobileFactory.getDriverService()).tap(point(point.x, point.y)).perform();
    }


    public boolean scrollBottomToTopUntilSingleElementFound(By locator) throws IOException {
        boolean flag = false;
        int count = 0;
        while (getSize(locator) == 0) {
            scrollVerticallyBottomToTop();
            count++;
            if (count > 50) {
                break;
            }
        }

        if (getSize(locator) > 0) {
            flag = true;
        }
        return flag;
    }

    public boolean scrollTopToBottomUntilSingleElementFound(By locator){
        boolean flag = false;
        int count = 0;
        while (getSize(locator) == 0) {
            scrollVerticallyTopToBottom();
            count++;
            if (count > 50) {
                break;
            }
        }

        if (getSize(locator) > 0) {
            flag = true;
        }
        return flag;
    }

}