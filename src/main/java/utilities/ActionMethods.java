package utilities;

import driver.MobileFactory;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.connection.ConnectionStateBuilder;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.touch.TapOptions;
import io.cucumber.java.Scenario;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.*;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.regex.Pattern;

import static driver.BasicConstants.EXPLICIT_WAIT_TIMEOUT_GENERIC;
import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;
import static io.appium.java_client.touch.offset.PointOption.point;
import static java.time.Duration.ofMillis;
import static java.time.Duration.ofSeconds;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@Slf4j
public class ActionMethods<T extends WebDriver> {

    public static WebDriverWait wait;
    public static Actions actions;
    public static BiFunction<String, String, String> stringReplacer = String::format;
    public MobileActions<AppiumDriver> mobileActions(){
        return  new MobileActions<AppiumDriver>();
    }
    public BrowserActions<T> browserActions(){
        return  new BrowserActions<T>();
    }
    public static Boolean validatePageError(WebDriver driver) {
        String title = driver.getTitle();
        return !title.contains("404") && !title.contains("500") && !title.contains("Not") && !title.contains("Error") && !title.contains("wrong");
    }

    public static int pageConnectionCheck(String url) throws URISyntaxException {
        HttpClient httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(15)).build();
        URI uri = new URI(url);
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder().uri(uri).build();
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            return httpResponse.statusCode();
        } catch (Exception e) {
            return 0;
        }
    }

    public Dimension getWindowSize(T driver) {
        Dimension dimension;
        try {
            dimension = driver.manage().window().getSize();
            return dimension;
        } catch (Exception e) {
            return null;
        }
    }
    public int getSize(T driver,By locator) {
        WebElement webElement=  findElement(locator,driver,Duration.ofSeconds(EXPLICIT_WAIT_TIMEOUT_GENERIC),Duration.ofSeconds(5));
        assertThat(webElement).isNotNull();
        return webElement.getSize().getHeight();
    }

    public Point getLocation(T driver,By locator) {
        WebElement webElement=  findElement(locator,driver,Duration.ofSeconds(EXPLICIT_WAIT_TIMEOUT_GENERIC),Duration.ofSeconds(5));
        assertThat(webElement).isNotNull();
        return webElement.getLocation();
    }
    public boolean waitTillElementVisible(T driver, WebElement webElement, Duration timeOut) {
        try {
            wait = new WebDriverWait(driver, timeOut);
           return  wait.until(ExpectedConditions.visibilityOf(webElement)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    public boolean waitTillElementVisible(T driver, WebElement webElement) {
        try {
            wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT_TIMEOUT_GENERIC));
            return  wait.until(ExpectedConditions.visibilityOf(webElement)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean waitTillElementInvisible(T driver, By locator, Duration timeOut) {
        try{
            wait = new WebDriverWait(driver, timeOut);
            return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        }catch(TimeoutException e){
            return false;
        }

    }
    public boolean waitTillElementInvisible(T driver, By locator) {
        try{
            wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT_TIMEOUT_GENERIC));
            return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        }catch(TimeoutException e){
            return false;
        }

    }


    public Boolean checkClickIntercepted(WebElement webElement) {
        try {
            webElement.click();
            return false;
        } catch (ElementNotInteractableException e) {
            return true;
        }
    }


    public Boolean isClickable(T driver, WebElement element) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT_TIMEOUT_GENERIC));
        return wait.until(ExpectedConditions.elementToBeClickable(element)).isDisplayed();
    }
    public Boolean isClickable(T driver, WebElement element,Duration timeOut) {
        wait = new WebDriverWait(driver, timeOut);
        return wait.until(ExpectedConditions.elementToBeClickable(element)).isDisplayed();
    }

    public void type(WebElement element, String value) {
        element.click();
        element.sendKeys(value);
    }
    public void clearAndType(WebElement element, String value) {
        element.clear();
        type(element,value);
    }
    public void click(T driver,WebElement element) {
        assertThat(isClickable(driver, element)).isTrue();
        element.click();
    }

    public static void setClipBoardData(String str) {
        StringSelection stringselection = new StringSelection(str);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringselection, null);
    }



    public void selectCheckBox(WebElement element, WebDriver driver) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT_TIMEOUT_GENERIC));
        wait.until(ExpectedConditions.elementToBeClickable(element));
        if (!element.isSelected()) {
            element.click();
        }

    }

    public void unselectCheckBox(WebElement element, WebDriver driver) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT_TIMEOUT_GENERIC));
        wait.until(ExpectedConditions.elementToBeClickable(element));
        if (element.isSelected()) {
            element.click();
        }
    }



    public void alertAcceptDismiss(WebDriver driver, Boolean accept) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT_TIMEOUT_GENERIC));
        wait.until((ExpectedConditions.alertIsPresent()));
        Alert alert = driver.switchTo().alert();
        if (accept) {
            alert.accept();
        } else {
            alert.dismiss();
        }

    }


    public void alertSendTextAccept(WebDriver driver, String text) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT_TIMEOUT_GENERIC));
        wait.until((ExpectedConditions.alertIsPresent()));
        Alert alert = driver.switchTo().alert();
        alert.sendKeys(text);
        alert.accept();
    }


    public WebElement matchTextFromChildOfListOfElementsAndReturnParentElement(T driver, List<WebElement> ListElem, By locator, String textValue) {
        WebElement ParentReturn = null;
        WebElement childElement;
        for (WebElement webElement : ListElem) {
            childElement = new ActionMethods<T>().findElement(locator, webElement, driver, Duration.ofSeconds(EXPLICIT_WAIT_TIMEOUT_GENERIC), Duration.ofSeconds(2));
            assertThat(childElement).isNotNull();
            String data = childElement.getText();
            if (data.trim().contains(textValue)) {
                ParentReturn = webElement;
                break;
            }
        }
        return ParentReturn;
    }

    public WebElement matchTextFromChildOfListOfElements(T driver, List<WebElement> ListElem, By locator, String textValue) {
        WebElement childRetrun = null;
        for (WebElement webElement : ListElem) {
            childRetrun = new ActionMethods<T>().findElement(locator, webElement, driver, Duration.ofSeconds(EXPLICIT_WAIT_TIMEOUT_GENERIC), Duration.ofSeconds(2));
            assertThat(childRetrun).isNotNull();
            String data = childRetrun.getText();
            if (data.trim().contains(textValue)) {
                break;
            }
        }
        return childRetrun;
    }
    /**
     * Return a single element first or default search result from a List of Element based on the Matched Text
     * *Param List of WebElement and Text Data
     * *Returns WebElement
     */
    public static WebElement returnWebElementFromList(List<WebElement> ListElem, String textData) {
        WebElement toFind = null;
        for (WebElement webElement : ListElem) {
            if (webElement.getText().contains(textData)) {
                toFind = webElement;
                break;
            }
        }
        return toFind;
    }


    public static void embedScreenshot(WebDriver driver, Scenario result, String message) {
        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        result.attach(screenshot, "image/png", message);
        result.log(message);
    }

    public static Boolean waitTillElementTextChanges(WebDriver driver, By loc, String Text, Duration time) {
        try {
            wait = new WebDriverWait(driver, time);
            return wait.until(ExpectedConditions.textToBe(loc, Text));
        } catch (Exception e) {
            return false;
        }
    }

    public static Boolean waitTillElementTextChanges(WebDriver driver, By loc, Pattern pattern, Duration time) {
        try {
            wait = new WebDriverWait(driver, time);
            return wait.until(ExpectedConditions.textMatches(loc, pattern));
        } catch (Exception e) {
            return false;
        }
    }


    public static void embedText(Scenario result, String message) {
        result.log(message);
    }

    public WebElement findElement(By locator, T driver, Duration timeout, Duration pollTime) {
        try {
            Wait<T> wait = new FluentWait<>(driver).withTimeout(timeout)
                    .pollingEvery(pollTime).ignoring(StaleElementReferenceException.class).ignoring(NoSuchElementException.class);
            return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (Exception e) {
            return null;
        }
    }

    public List<WebElement> findElements(By locator, T driver, Duration timeout, Duration pollTime) {
        try {
            Wait<T> wait = new FluentWait<>(driver).withTimeout(timeout)
                    .pollingEvery(pollTime).ignoring(StaleElementReferenceException.class).ignoring(NoSuchElementException.class);
            return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        } catch (Exception e) {
            return null;
        }
    }

    public WebElement findElement(By locator, WebElement webElement, T driver, Duration timeout, Duration pollTime) {
        try {
            Wait<T> wait = new FluentWait<>(driver).withTimeout(timeout)
                    .pollingEvery(pollTime).ignoring(StaleElementReferenceException.class).ignoring(NoSuchElementException.class);
            return wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(webElement,locator));
        } catch (TimeoutException e) {
            return null;
        }
    }




    public String getElementText(WebElement webElement) {
        return webElement.getText().trim();
    }

    public static void moveTo(WebDriver driver, WebElement webElement) {
        actions = new Actions(driver);
        actions.moveToElement(webElement).perform();
    }
    public static void moveToAndClick(WebDriver driver, WebElement webElement) {
        actions = new Actions(driver);
        actions.moveToElement(webElement).click().perform();
    }

    public void dragAndDropElement(WebDriver driver, WebElement source, WebElement destination) {
        actions = new Actions(driver);
        actions.dragAndDrop(source, destination).build().perform();
    }


    public static class MobileActions<U extends AppiumDriver>{
        public ScrollMobile<U> scrollMobile(){
            return new ScrollMobile<U>();
        }
        public SwipeMobile<U> swipeMobile(){
            return new SwipeMobile<U>();
        }
        public DropdownActions<U> dropdownActions(){
            return new DropdownActions<U>();
        }
        public Tap<U> tap(){
            return new Tap<U>();
        }
        public Wifi<AndroidDriver> wifi(){
            return new Wifi<AndroidDriver>();
        }
        public SetContext<U> setContext(){
            return new SetContext<U>();
        }
        public static class SetContext<U extends AppiumDriver> {
            public void switchToWebViewAndroid(U driver) {
                Set<String> contexts = ((AndroidDriver)driver).getContextHandles();
                for (String context : contexts) {
                    if (!context.equals("NATIVE_APP")) {
                        ((AndroidDriver) MobileFactory.getDriverService()).context((String) contexts.toArray()[1]);
                        break;
                    }
                }
            }
            public void switchToWebViewIos(U driver) {
                Set<String> contexts = ((IOSDriver)driver).getContextHandles();
                for (String context : contexts) {
                    if (!context.equals("NATIVE_APP")) {
                        ((AndroidDriver) MobileFactory.getDriverService()).context((String) contexts.toArray()[1]);
                        break;
                    }
                }
            }
        }
        public static class Wifi<U extends AndroidDriver>{
            public void androidWifiOnWithToggle(U driver) {
                driver.toggleWifi();
            }
            public void androidWifiOn(U driver) {
                driver.setConnection(new ConnectionStateBuilder().withWiFiEnabled().withDataEnabled().build());
            }
            public void androidWifiOff(U driver) {
               driver.setConnection(new ConnectionStateBuilder().withWiFiDisabled().build());
            }
        }
        public static class Tap<U extends  AppiumDriver>{
            public void tapOnElementPoints(U driver, WebElement webElement, double x, double y) {
                    Rectangle elRect = webElement.getRect();
                    int newX = (int) (elRect.getX() * x);
                    int newY = (int) (elRect.getY() * y);
                    new TouchAction((PerformsTouchActions) driver)
                            .tap(TapOptions.tapOptions().withPosition(point(newX, newY))).waitAction(waitOptions(ofMillis(250)))
                            .perform();
            }
            public void tapElement(U driver, WebElement webElement){
                new TouchAction((PerformsTouchActions) driver)
                        .tap(TapOptions.tapOptions().withElement(element(webElement)))
                        .waitAction(waitOptions(ofMillis(250))).perform();
            }
        }
        public static class DropdownActions<U extends  AppiumDriver>{
            public void selectValueFromDropdownByText(U driver, String value, By locator) {
                List<WebElement> elements = new ActionMethods<U>().findElements(locator,driver,Duration.ofSeconds(EXPLICIT_WAIT_TIMEOUT_GENERIC),Duration.ofSeconds(2));
                for (WebElement element : elements) {
                    if (element.getAttribute("text").equalsIgnoreCase(value)) {
                        new MobileActions.Tap<>().tapElement(driver, element);
                        break;
                    }
                }
            }
            public void selectValueFromDropdownByText(U driver,List<WebElement> elements,String value) {
                for (WebElement element : elements) {
                    if (element.getAttribute("text").equalsIgnoreCase(value)) {
                        new MobileActions.Tap<>().tapElement(driver, element);
                        break;
                    }
                }
            }
            public void selectValueFromDropdownByAttributes(U driver, String value, By locator,String attributeName) {
                List<WebElement> elements = new ActionMethods<>().findElements(locator,driver,Duration.ofSeconds(EXPLICIT_WAIT_TIMEOUT_GENERIC),Duration.ofSeconds(2));
                for (WebElement element : elements) {
                    if (element.getAttribute(attributeName).equalsIgnoreCase(value)) {
                        new MobileActions.Tap<>().tapElement(driver, element);
                        break;
                    }
                }
            }
            public void selectValueFromDropdownByAttributes(U driver,List<WebElement> elements,String value,String attributeName) {
                for (WebElement element : elements) {
                    if (element.getAttribute(attributeName).equalsIgnoreCase(value)) {
                        new MobileActions.Tap<>().tapElement(driver, element);
                        break;
                    }
                }
            }
        }
        public static class ScrollMobile<U extends AppiumDriver>{
            public void scrollTo(U driver, int startX, int startY, int endX, int endY) {
                TouchAction touchAction = new TouchAction((PerformsTouchActions) driver);
                touchAction.press(point(startX, startY))
                        .waitAction(waitOptions(ofSeconds(1)))
                        .moveTo(point(endX, endY))
                        .release()
                        .perform();
            }
            @SneakyThrows
            public void scrollVerticallyTopToBottom(U driver){
                Dimension dimension = new ActionMethods<AppiumDriver>().getWindowSize(driver);
                int endY = (int) (dimension.getHeight() * 0.7);
                int startX = dimension.getWidth() / 2;
                int startY = (int) (dimension.getHeight() * 0.40);
                scrollTo(driver,startX, startY, startX, endY);
            }
            public void scrollVerticallyBottomToTop(U driver) throws IOException {
                Dimension dimension = new ActionMethods<AppiumDriver>().getWindowSize(driver);
                int endY = (int) (dimension.getHeight() * 0.4);
                int startX = dimension.getWidth() / 2;
                int startY = (int) (dimension.getHeight() * 0.7);
                scrollTo(driver,startX, startY, startX, endY);
            }
            public void scrollVerticallyTopToBottomLarge(U driver) {
                Dimension dimension = new ActionMethods<AppiumDriver>().getWindowSize(driver);
                int startY = (int) (dimension.getHeight() * 0.09);
                int startX = dimension.getWidth() / 2;
                int endY = (int) (dimension.getHeight() * 0.7);
                scrollTo(driver,startX, startY, startX, endY);
            }
            public  void scrollVerticallyBottomToTopLarge(U driver) throws IOException {
                Dimension dimension = new ActionMethods<AppiumDriver>().getWindowSize(driver);
                int startY = (int) (dimension.getHeight() * 0.7);
                int startX = dimension.getWidth() / 2;
                int endY = (int) (dimension.getHeight() * 0.09);
                scrollTo(driver,startX, startY, startX, endY);
            }
            @SneakyThrows
            public boolean scrollTillFoundUsingElement(U driver,WebElement element, By locator, int times, Boolean check){
                int counter = 0;
                while (element == null) {
                    try {
                        counter++;
                        if (counter <= times) {
                            if (check) {
                                scrollVerticallyTopToBottom(driver);
                            } else {
                                scrollVerticallyBottomToTop(driver);
                            }
                        } else {
                            Assert.fail("Did not find the element after scrolling :: "+counter+" no of times");
                        }
                        element = driver.findElement(locator);
                    } catch (NoSuchElementException e) {
                        log.info("Scrolled  for :: {} time", counter);
                    }
                }
                return element.isDisplayed();
            }
            public void scrollVerticallyWithCoordinate(U driver,double yStart, double yEnd){
                Dimension dimension = new ActionMethods<>().getWindowSize(driver);
                int startY = (int) (dimension.getHeight() * yStart);
                int startX = dimension.getWidth() / 2;
                int endY = (int) (dimension.getHeight() * yEnd);
                scrollTo(driver,startX, startY, startX, endY);
            }
            @SneakyThrows
            public boolean scrollVerticallyUntilSingleElementFound(U driver,By locator, int times) {
                boolean flag = false;
                int count = 0;
                while (new ActionMethods<AppiumDriver>().getSize(driver,locator) == 0) {
                    scrollVerticallyBottomToTopLarge(driver);
                    count++;
                    if (count > times) {
                        break;
                    }
                }
                if (new ActionMethods<AppiumDriver>().getSize(driver,locator) > 0) {
                    flag = true;
                }
                return flag;
            }

        }
        public static class SwipeMobile<U extends AppiumDriver>{
            public void swipeRightToLeftFromElement(U driver,By locator) throws IOException {
                Point point = new ActionMethods<>().getLocation(driver,locator);
                int startX = (int) (point.getX() * 0.95);
                int endX = (int) (point.getX() * 0.05);
                int startY = point.getY();
                new MobileActions.ScrollMobile<U>().scrollTo(driver,startX, startY, endX, startY);
            }
            public void swipeLeftToRightFromElement(U driver,By locator) throws IOException {
                Point point = new ActionMethods<AppiumDriver>().getLocation(driver,locator);
                int startX = (int) (point.getX() * 0.95);
                int endX = (int) (point.getX() * 0.05);
                int startY = point.getY();
                new MobileActions.ScrollMobile<U>().scrollTo(driver,endX, startY, startX, startY);
            }
            public void swipeRightToLeft(U driver) {
                Dimension size = driver.manage().window().getSize();
                int startY = (int) (size.height / 2);
                int startX = (int) (size.width * 0.60);
                int endX = (int) (size.width * 0.05);
                new MobileActions.ScrollMobile<U>().scrollTo(driver,startX, startY, endX, startY);
            }
            public void swipeLeftToRight(U driver) {
                Dimension size = driver.manage().window().getSize();
                int startY = (int) (size.height / 2);
                int startX = (int) (size.width * 0.05);
                int endX = (int) (size.width * 0.60);
                new MobileActions.ScrollMobile<U>().scrollTo(driver,startX, startY, endX, startY);
            }
        }
    }
    public static class BrowserActions<T extends WebDriver>{
        public ScrollBrowser<T> scrollBrowser(){
            return new ScrollBrowser<T>();
        }
        public JavaScriptExecutor<T> javaScriptExecutor(){
            return new JavaScriptExecutor<T>();
        }
        public DropdownActions<T> dropdownActions(){
            return new DropdownActions<T>();
        }
        public static class DropdownActions<T extends WebDriver>{
            public void selectElementFromDropDownByIndex(T driver, WebElement element, By Dropdown, int index,Duration timeOut) {
                wait = new WebDriverWait(driver, timeOut);
                wait.until(ExpectedConditions.elementToBeClickable(element));
                element.click();
                wait.until(ExpectedConditions.presenceOfElementLocated(Dropdown));
                WebElement dropdownElement = new ActionMethods<T>().findElement(Dropdown, driver, Duration.ofSeconds(10), Duration.ofSeconds(1));
                assertThat(dropdownElement).isNotNull();
                Select dropDown = new Select(dropdownElement);
                dropDown.selectByIndex(index);
            }
            public void selectElementFromDropDownByValue(T driver, WebElement element, By Dropdown, String value,Duration timeOut) {
                wait = new WebDriverWait(driver, timeOut);
                wait.until(ExpectedConditions.elementToBeClickable(element));
                element.click();
                wait.until(ExpectedConditions.presenceOfElementLocated(Dropdown));
                WebElement dropdownElement = new ActionMethods<>().findElement(Dropdown, driver, timeOut, Duration.ofSeconds(5));
                assertThat(dropdownElement).isNotNull();
                Select dropDown = new Select(dropdownElement);
                dropDown.selectByValue(value);
            }
            public void DeselectElementFromDropDownByVisibleText(T driver, WebElement element, By Dropdown, String text,Duration timeOut) {
                wait = new WebDriverWait(driver, timeOut);
                wait.until(ExpectedConditions.elementToBeClickable(element));
                element.click();
                wait.until(ExpectedConditions.presenceOfElementLocated(Dropdown));
                WebElement dropdownElement = new ActionMethods<>().findElement(Dropdown, driver, timeOut, Duration.ofSeconds(5));
                assertThat(dropdownElement).isNotNull();
                Select dropDown = new Select(dropdownElement);
                dropDown.deselectByVisibleText(text);
            }
            public void deselectElementFromDropDownByIndex(T driver, WebElement element, By Dropdown, int index,Duration timeOut) {
                wait = new WebDriverWait(driver, timeOut);
                wait.until(ExpectedConditions.elementToBeClickable(element));
                element.click();
                wait.until(ExpectedConditions.presenceOfElementLocated(Dropdown));
                WebElement dropdownElement =new ActionMethods<>().findElement(Dropdown, driver, timeOut, Duration.ofSeconds(5));
                assertThat(dropdownElement).isNotNull();
                Select dropDown = new Select(dropdownElement);
                dropDown.deselectByIndex(index);
            }
            public void deselectElementFromDropdownByValue(T driver, WebElement element, By Dropdown, String value,Duration timeOut) {
                wait = new WebDriverWait(driver, timeOut);
                wait.until(ExpectedConditions.elementToBeClickable(element));
                element.click();
                wait.until(ExpectedConditions.presenceOfElementLocated(Dropdown));
                WebElement dropdownElement = new ActionMethods<>().findElement(Dropdown, driver, timeOut, Duration.ofSeconds(5));
                assertThat(dropdownElement).isNotNull();
                Select dropDown = new Select(dropdownElement);
                dropDown.deselectByValue(value);
            }
        }
        public static class JavaScriptExecutor<T extends WebDriver> {
            public void highlightOnElement(T driver, WebElement Element) {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", Element);
            }
            public void textInput(T driver, WebElement webElement, String text) {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].value='" + text + "';", webElement);
            }
            public void click(T driver, WebElement Element) {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].click();", Element);
            }
            public void openNewWindow(T driver) {
                wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT_TIMEOUT_GENERIC));
                wait.until(ExpectedConditions.javaScriptThrowsNoExceptions("window.open('');"));
            }
            public boolean waitForJavaScriptToLoad(T driver) {
                wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT_TIMEOUT_GENERIC));
                return wait.until(ExpectedConditions.jsReturnsValue("return document.readyState")).toString().equals("complete");
            }
            public boolean waitForJQueryToLoad(T driver){
                wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT_TIMEOUT_GENERIC));
                return wait.until(ExpectedConditions.jsReturnsValue("return jQuery.active")).equals(0);
            }
        }
        public static class ScrollBrowser<T extends WebDriver>{
            public void scroll(T driver,boolean check, String X, String Y) {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                Object o = check ? js.executeScript("window.scrollBy(" + X + "," + Y + ")", "") : js.executeScript("window.scrollBy(-" + X + ",-" + Y + ")", "");
            }
            public void scrollDownBy(T driver, int val) {
                wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT_TIMEOUT_GENERIC));
                wait.until(ExpectedConditions.javaScriptThrowsNoExceptions("window.scrollBy(0, " + val + ")"));
            }
            public void scrollUpBy(T driver, int val) {
                wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT_TIMEOUT_GENERIC));
                wait.until(ExpectedConditions.javaScriptThrowsNoExceptions("window.scrollBy(0, -" + val + ")"));
            }
            public  void scrollDownToPageBottom(T driver) {
                wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT_TIMEOUT_GENERIC));
                wait.until(ExpectedConditions.javaScriptThrowsNoExceptions("window.scrollTo(0,document.body.scrollHeight)"));
            }
            public void scrollUpToPageTop(T driver) {
                wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT_TIMEOUT_GENERIC));
                wait.until(ExpectedConditions.javaScriptThrowsNoExceptions("window.scrollTo(0,0)"));
            }
            public void scrollToElement(T driver, WebElement element) {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].scrollIntoView(true);", element);
            }
            @SneakyThrows
            public boolean ScrollTillDisplayed(T driver, WebElement Element, boolean up, String X, String Y, int times) {
                int counter = 0;
                boolean flag = false;
                while (!flag) {
                    counter++;
                    if (counter > times) {
                        Assert.fail("Failed while Scrolling to the element :: " + Element);
                    }
                    scroll(driver, up, X, Y);
                    flag = Element.isDisplayed();
                }
                return flag;
            }

            public boolean ScrollTillInteractive(T driver, WebElement Element, boolean up, String X, String Y, int times) {
                int counter = 0;
                boolean flag = true;
                while (flag) {
                    try {
                        counter++;
                        Element.click();
                        flag = false;
                    } catch (ElementNotInteractableException e) {
                        if (counter > times) {
                            Assert.fail("Failed while Scrolling to the element :: " + Element);
                        }
                        scroll(driver, up, X, Y);
                    }
                }
                return flag;
            }

            public boolean scrollTillElementFound(T driver, By loc, Boolean up, String X, String Y,int times) {
                try {
                    int counter = 0;
                    boolean flag = true;
                    while (flag) {
                        try {
                            counter++;
                            driver.findElement(loc);
                            flag = false;
                        } catch (NoSuchElementException e) {
                            if (counter > times) {
                                Assert.fail("Failed while scrolling and finding LOC :: " + String.valueOf(loc));
                            }
                            scroll(driver, up, X, Y);
                        }
                    }
                    return true;
                } catch (Exception e) {
                    return false;
                }
            }
        }
    }
}