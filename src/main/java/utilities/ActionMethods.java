package utilities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.cucumber.java.Scenario;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.Actions;
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
import java.util.function.BiFunction;
import java.util.regex.Pattern;

import static driver.BasicConstants.EXPLICIT_WAIT_TIMEOUT_GENERIC;
import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.PointOption.point;
import static java.time.Duration.ofSeconds;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static utilities.ActionMethods.Scroll.ScrollMobile.scrollTo;


@Slf4j
public class ActionMethods<T extends WebDriver> {

    public static WebDriverWait wait;
    public static Actions actions;
    public static BiFunction<String, String, String> stringReplacer = String::format;
    public Scroll scroll(){
        return  new Scroll();
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

    public void type(WebElement element, String str) {
        element.click();
        element.sendKeys(str);
    }
    public void clearAndType(WebElement element, String str) {
        element.clear();
        type(element,str);
    }
    public void click(T driver,WebElement element) {
        assertThat(isClickable(driver, element)).isTrue();
        element.click();
    }




    public static void setClipBoardData(String str) {
        StringSelection stringselection = new StringSelection(str);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringselection, null);
    }

    public static void selectElementFromDropDownByIndex(WebDriver driver, WebElement element, By Dropdown, int index,Duration timeOut) {
        wait = new WebDriverWait(driver, timeOut);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(Dropdown));
        WebElement dropdownElement = findElement(Dropdown, driver, Duration.ofSeconds(10), Duration.ofSeconds(1));
        assertThat(dropdownElement).isNotNull();
        Select dropDown = new Select(dropdownElement);
        dropDown.selectByIndex(index);
    }

    public static void selectElementFromDropDownByValue(WebDriver driver, WebElement element, By Dropdown, String value,Duration timeOut) {
        wait = new WebDriverWait(driver, timeOut);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(Dropdown));
        WebElement dropdownElement = findElement(Dropdown, driver, Duration.ofSeconds(10), Duration.ofSeconds(1));
        assertThat(dropdownElement).isNotNull();
        Select dropDown = new Select(dropdownElement);
        dropDown.selectByValue(value);
    }


    public static void DeselectElementFromDropDownByVisibleText(WebDriver driver, WebElement element, By Dropdown, String text) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(Dropdown));
        WebElement dropdownElement = findElement(Dropdown, driver, Duration.ofSeconds(10), Duration.ofSeconds(1));
        assertThat(dropdownElement).isNotNull();
        Select dropDown = new Select(dropdownElement);
        dropDown.deselectByVisibleText(text);
    }

    public static void deselectElementFromDropDownByIndex(WebDriver driver, WebElement element, By Dropdown, int index) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(Dropdown));
        WebElement dropdownElement = findElement(Dropdown, driver, Duration.ofSeconds(10), Duration.ofSeconds(1));
        assertThat(dropdownElement).isNotNull();
        Select dropDown = new Select(dropdownElement);
        dropDown.deselectByIndex(index);
    }

    public static void deselectElementFromDropdownByValue(WebDriver driver, WebElement element, By Dropdown, String Value) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(Dropdown));
        WebElement dropdownElement = findElement(Dropdown, driver, Duration.ofSeconds(5), Duration.ofSeconds(1));
        assertThat(dropdownElement).isNotNull();
        Select dropDown = new Select(dropdownElement);
        dropDown.deselectByValue(Value);
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

    public void scrollToElement(WebDriver driver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
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


    public WebElement matchTextFromChildOfListOfElementsAndReturnParentElement(WebDriver driver, List<WebElement> ListElem, By locator, String textValue) {
        WebElement ParentReturn = null;
        WebElement childElement;
        for (WebElement webElement : ListElem) {
            childElement = findElement(locator, webElement, driver, Duration.ofSeconds(EXPLICIT_WAIT_TIMEOUT_GENERIC), Duration.ofSeconds(2));
            assertThat(childElement).isNotNull();
            String data = childElement.getText();
            if (data.trim().contains(textValue)) {
                ParentReturn = webElement;
                break;
            }
        }
        return ParentReturn;
    }

    public WebElement matchTextFromChildOfListOfElements(WebDriver driver, List<WebElement> ListElem, By locator, String textValue) {
        WebElement childRetrun = null;
        for (WebElement webElement : ListElem) {
            childRetrun = findElement(locator, webElement, driver, Duration.ofSeconds(EXPLICIT_WAIT_TIMEOUT_GENERIC), Duration.ofSeconds(2));
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

    public static WebElement findElement(By locator, WebDriver driver, Duration timeout, Duration pollTime) {
        try {
            Wait<WebDriver> wait = new FluentWait<>(driver).withTimeout(timeout)
                    .pollingEvery(pollTime).ignoring(StaleElementReferenceException.class).ignoring(NoSuchElementException.class);
            return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (Exception e) {
            return null;
        }
    }

    public static List<WebElement> findElements(By locator, WebDriver driver, Duration timeout, Duration pollTime) {
        try {
            Wait<WebDriver> wait = new FluentWait<>(driver).withTimeout(timeout)
                    .pollingEvery(pollTime).ignoring(StaleElementReferenceException.class).ignoring(NoSuchElementException.class);
            return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        } catch (Exception e) {
            return null;
        }
    }

    public static WebElement findElement(By locator, WebElement webElement, WebDriver driver, Duration timeout, Duration pollTime) {
        try {
            Wait<WebDriver> wait = new FluentWait<>(driver).withTimeout(timeout)
                    .pollingEvery(pollTime).ignoring(StaleElementReferenceException.class).ignoring(NoSuchElementException.class);
            return wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(webElement,locator));
        } catch (TimeoutException e) {
            return null;
        }
    }


    public static boolean waitForJavaScriptToLoad(WebDriver driver) {
            wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT_TIMEOUT_GENERIC));
            return wait.until(ExpectedConditions.jsReturnsValue("return document.readyState")).toString().equals("complete");
    }

    public static boolean waitForJQueryToLoad(WebDriver driver){
        wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT_TIMEOUT_GENERIC));
        return wait.until(ExpectedConditions.jsReturnsValue("return jQuery.active")).equals(0);
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

    public static class JavaScriptUsage{
        public void HighlighterOnElem(WebDriver driver, WebElement Element) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", Element);
        }

        public static void JavaScriptTextInput(WebDriver driver, WebElement webElement, String text) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].value='" + text + "';", webElement);
        }

        public static void javaScriptClick(WebDriver driver, WebElement Element) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", Element);
        }

        public void openNewWindowUsingJavaScript(WebDriver driver) {
            wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT_TIMEOUT_GENERIC));
            wait.until(ExpectedConditions.javaScriptThrowsNoExceptions("window.open('');"));
        }

    }

    public static  class Scroll{
        public ScrollBrowser scrollBrowser(){
            return new ScrollBrowser();
        }
        public ScrollMobile scrollMobile(){
            return new ScrollMobile();
        }
        public SwipeMobile swipeMobile(){
            return new SwipeMobile();
        }
        public static class ScrollBrowser{
            public void scroll(WebDriver driver,boolean check, String X, String Y) {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                Object o = check ? js.executeScript("window.scrollBy(" + X + "," + Y + ")", "") : js.executeScript("window.scrollBy(-" + X + ",-" + Y + ")", "");
            }

            public static void scrollDownBy(WebDriver driver, int val) {
                wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT_TIMEOUT_GENERIC));
                wait.until(ExpectedConditions.javaScriptThrowsNoExceptions("window.scrollBy(0, " + val + ")"));
            }


            public void scrollUpBy(WebDriver driver, int val) {
                wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT_TIMEOUT_GENERIC));
                wait.until(ExpectedConditions.javaScriptThrowsNoExceptions("window.scrollBy(0, -" + val + ")"));
            }

            public static void scrollDownPageBottom(WebDriver driver) {
                wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT_TIMEOUT_GENERIC));
                wait.until(ExpectedConditions.javaScriptThrowsNoExceptions("window.scrollTo(0,document.body.scrollHeight)"));
            }
            public static void scrollUpPageTop(WebDriver driver) {
                wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT_TIMEOUT_GENERIC));
                wait.until(ExpectedConditions.javaScriptThrowsNoExceptions("window.scrollTo(0,0)"));
            }
            @SneakyThrows
            public boolean ScrollTillDisplayed(WebDriver driver, WebElement Element, boolean up, String X, String Y, int times) {
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

            public boolean ScrollTillInteractive(WebDriver driver, WebElement Element, boolean up, String X, String Y, int times) {
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

            public boolean scrollTillElementFound(WebDriver driver, By loc, Boolean up, String X, String Y,int times) {
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
        public static class ScrollMobile{
            public static void scrollTo(AppiumDriver driver, int startX, int startY, int endX, int endY) {
                TouchAction touchAction = new TouchAction((PerformsTouchActions) driver);
                touchAction.press(point(startX, startY))
                        .waitAction(waitOptions(ofSeconds(1)))
                        .moveTo(point(endX, endY))
                        .release()
                        .perform();
            }


            @SneakyThrows
            public static void scrollVerticallyTopToBottom(AppiumDriver driver){
                Dimension dimension = new ActionMethods<AppiumDriver>().getWindowSize(driver);
                int endY = (int) (dimension.getHeight() * 0.7);
                int startX = dimension.getWidth() / 2;
                int startY = (int) (dimension.getHeight() * 0.40);
                scrollTo(driver,startX, startY, startX, endY);

            }

            public static void scrollVerticallyBottomToTop(AppiumDriver driver) throws IOException {
                Dimension dimension = new ActionMethods<AppiumDriver>().getWindowSize(driver);
                int endY = (int) (dimension.getHeight() * 0.4);
                int startX = dimension.getWidth() / 2;
                int startY = (int) (dimension.getHeight() * 0.7);
                scrollTo(driver,startX, startY, startX, endY);

            }
            public static void scrollVerticallyTopToBottomLarge(AppiumDriver driver) {
                Dimension dimension = new ActionMethods<AppiumDriver>().getWindowSize(driver);
                int startY = (int) (dimension.getHeight() * 0.09);
                int startX = dimension.getWidth() / 2;
                int endY = (int) (dimension.getHeight() * 0.7);
                scrollTo(driver,startX, startY, startX, endY);
            }

            public  static void scrollVerticallyBottomToTopLarge(AppiumDriver driver) throws IOException {
                Dimension dimension = new ActionMethods<AppiumDriver>().getWindowSize(driver);
                int startY = (int) (dimension.getHeight() * 0.7);
                int startX = dimension.getWidth() / 2;
                int endY = (int) (dimension.getHeight() * 0.09);
                scrollTo(driver,startX, startY, startX, endY);
            }

            @SneakyThrows
            public static boolean scrollTillFoundUsingElement(AppiumDriver driver,WebElement element, By locator, int times, Boolean check){
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

            public static void scrollVerticallyWithCoordinate(AppiumDriver driver,double yStart, double yEnd){
                Dimension dimension = new ActionMethods<AppiumDriver>().getWindowSize(driver);
                int startY = (int) (dimension.getHeight() * yStart);
                int startX = dimension.getWidth() / 2;
                int endY = (int) (dimension.getHeight() * yEnd);
                scrollTo(driver,startX, startY, startX, endY);
            }



            @SneakyThrows
            public static boolean scrollVerticallyUntilSingleElementFound(AppiumDriver driver,By locator, int times) {
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
        public static class SwipeMobile{
            public void swipeRightToLeftFromElement(AppiumDriver driver,By locator) throws IOException {
                Point point = new ActionMethods<AppiumDriver>().getLocation(driver,locator);
                int startX = (int) (point.getX() * 0.95);
                int endX = (int) (point.getX() * 0.05);
                int startY = point.getY();
                scrollTo(driver,startX, startY, endX, startY);
            }

            public void swipeLeftToRightFromElement(AppiumDriver driver,By locator) throws IOException {
                Point point = new ActionMethods<AppiumDriver>().getLocation(driver,locator);
                int startX = (int) (point.getX() * 0.95);
                int endX = (int) (point.getX() * 0.05);
                int startY = point.getY();
                scrollTo(driver,endX, startY, startX, startY);
            }


            public void swipeRightToLeft(AppiumDriver driver) {
                Dimension size = driver.manage().window().getSize();
                int startY = (int) (size.height / 2);
                int startX = (int) (size.width * 0.60);
                int endX = (int) (size.width * 0.05);
                scrollTo(driver,startX, startY, endX, startY);
            }
            public void swipeLeftToRight(AppiumDriver driver) {
                Dimension size = driver.manage().window().getSize();
                int startY = (int) (size.height / 2);
                int startX = (int) (size.width * 0.05);
                int endX = (int) (size.width * 0.60);
                scrollTo(driver,startX, startY, endX, startY);
            }

        }

    }

}