package utilities;

import io.cucumber.java.Scenario;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static driver.BasicConstants.EXPLICIT_WAIT_TIMEOUT_GENERIC;
import static driver.BasicConstants.IMPLICIT_WAIT_TIMEOUT_GENERIC;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class ActionMethods {

    public static WebDriverWait wait;
    public static Actions actions;
    public static BiFunction<String, String, String> stringReplacer = (s, s2) -> String.format(s, s2);
    public static Function<String, By> locatorCreator = By::xpath;

    public static Boolean validatePageError(WebDriver driver) {
        String title = driver.getTitle();
        if (title.contains("404") || title.contains("500") || title.contains("Not") || title.contains("Error") || title.contains("wrong")) {
            return false;
        } else {
            return true;
        }
    }

    public static void switchToParent() {

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

    public Date getDate(String date) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
            Date OutDate = formatter.parse(date);
            System.out.println(OutDate);
            return OutDate;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static Boolean SyncAndVisible(WebDriver driver, WebElement element, Duration Timeout) {
        try {
            FluentWait<WebDriver> wait = new WebDriverWait(driver, Timeout).ignoring(StaleElementReferenceException.class).pollingEvery(Duration.ofSeconds(5)).withTimeout(Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void WaitforElementVisible(WebDriver driver, WebElement Elem, Duration Timeout) {
        try {
            wait = new WebDriverWait(driver, Timeout);
            wait.until(ExpectedConditions.visibilityOf(Elem));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void WaitforElementInVisible(WebDriver driver, By loc, Duration Timeout) {
        try {
            wait = new WebDriverWait(driver, Timeout);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(loc));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static Boolean checkClickIntercepted(WebElement elem) {
        try {
            elem.click();
            return false;
        } catch (ElementNotInteractableException e) {
            return true;
        }
    }

    public static Boolean syncClickable(WebDriver driver, WebElement element, Duration Timeout) {
        try {
            FluentWait<WebDriver> wait = (WebDriverWait) new WebDriverWait(driver, Timeout).ignoring(StaleElementReferenceException.class);
            wait.until(ExpectedConditions.elementToBeClickable(element));
            return true;
        } catch (ElementNotInteractableException e) {
            System.out.println("Not interactable");
            e.printStackTrace();
            return false;
        } catch (TimeoutException e) {
            System.out.println("Timeout");
            e.printStackTrace();
            return false;
        } catch (WebDriverException e) {
            System.out.println("WebDriver couldnot click");
            e.printStackTrace();
            return false;
        }

    }

    public static void click(WebDriver driver, WebElement element, Duration time) {
        wait = new WebDriverWait(driver, time);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }

    public void HighlighterOnElem(WebDriver driver, WebElement Element) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", Element);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void JavaScriptTextInput(WebDriver driver, WebElement elem, String text) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value='" + text + "';", elem);
    }

    public static void JavaScriptClick(WebDriver driver, WebElement Element) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", Element);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void OpenanewWindow(WebDriver driver) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.open('');");
        } catch (Exception e) {

        }
    }

    public static Boolean justClickable(WebDriver driver, WebElement element, Duration time) {
        try {
            wait = new WebDriverWait(driver, time);
            wait.until(ExpectedConditions.elementToBeClickable(element));
            return true;
        } catch (ElementNotInteractableException e) {
            System.out.println("Not interactable");
            return false;
        } catch (WebDriverException e) {
            System.out.println("WebDriver could not click");
            return false;
        }
    }

    public static void type(WebElement element, String str) {
        element.click();
        element.sendKeys(str);
    }

    public void clear(WebElement element) {
        element.clear();
    }

    public boolean verifyElement(Object element) {
        try {
            if (element instanceof WebElement) {
                if (((WebElement) element).isDisplayed()) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (NullPointerException e) {
            System.out.println("Object value null");
            e.printStackTrace();
            return false;
        } catch (NoSuchElementException e) {
            System.out.println("Object Not exists");
            e.printStackTrace();
            return false;
        }

    }

    public boolean isWebElementPresent(WebElement element, WebDriver driver, Duration time) {
        try {
            wait = new WebDriverWait(driver, time);
            wait.until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (NoSuchElementException e) {
            System.out.println("No Suh Elem");
            return false;
        }

    }

    public static Boolean ScrollIntoView(WebDriver driver, WebElement Element) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true);", Element);
            if (Element.isDisplayed()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static Boolean Scroll(WebDriver driver, WebElement Element, boolean check, String X, String Y) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            Object o = check == true ? js.executeScript("window.scrollBy(" + X + "," + Y + ")", "") : js.executeScript("window.scrollBy(-" + X + ",-" + Y + ")", "");
            if (Element.isDisplayed()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static Boolean ScrollTillDisplayed(WebDriver driver, WebElement Element, boolean check, String X, String Y) {
        try {
            int counter = 0;
            boolean flag = false;
            while (!flag) {
                counter++;
                if (counter > 20) {
                    Assert.fail("Failed while Scrolling to the element :: " + Element);
                }
                ActionMethods.Scroll(driver, Element, check, X, Y);
                Thread.sleep(2000);
                flag = Element.isDisplayed();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Boolean ScrollTillInteractive(WebDriver driver, WebElement Element, boolean check, String X, String Y) {
        try {
            int counter = 0;
            boolean flag = true;
            while (flag) {
                try {
                    counter++;
                    Element.click();
                    flag = false;
                } catch (ElementNotInteractableException e) {
                    if (counter > 20) {
                        Assert.fail("Failed while Scrolling to the element :: " + Element);
                    }
                    ActionMethods.Scroll(driver, Element, check, X, Y);
                    Thread.sleep(2000);
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Boolean ScrollTIllElementFound(WebDriver driver, By loc, Boolean check, String X, String Y) {
        try {
            int counter = 0;
            turnOffImplicitWaits(driver);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            boolean flag = true;
            while (flag) {
                try {
                    counter++;
                    WebElement element = driver.findElement(loc);
                    flag = false;
                } catch (NoSuchElementException e) {
                    if (counter > 20) {
                        Assert.fail("Failed while scrolling and finding LOC :: " + String.valueOf(loc));
                    }
                    Object o = check == true ? js.executeScript("window.scrollBy(" + X + "," + Y + ")", "") : js.executeScript("window.scrollBy(-" + X + ",-" + Y + ")", "");
                    Thread.sleep(2000);
                }
            }
            turnOnImplicitWaits(driver);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void setClipBoardData(String str) {
        StringSelection stringselection = new StringSelection(str);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringselection, null);
    }


    //Click on the Dropdown button and then Select the option from Visible Text
    //WebElement of the Dropdown, Text to chose from
    public void selectDropDownValueByVisibleText(WebDriver driver, By locator, String visibleText) {
        Select se = new Select(driver.findElement(locator));
        se.selectByVisibleText(visibleText);
    }

    //Click on the Dropdown button and then Select the choice based on index
    //WebDriver, WebElement of the Dropdown, Select class locator, Text to chose from
    public void selectElementFromDropDownbyIndex(WebDriver driver, WebElement element, By Dropdown, int index) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(Dropdown));
        WebElement Dropwdown_Elem = FindElement(Dropdown, driver, Duration.ofSeconds(5), Duration.ofSeconds(1));
        Select dropDown = new Select(Dropwdown_Elem);
        dropDown.selectByIndex(index);
    }

    //Click on the Dropdown button and then Select the choice based on value
    //WebDriver, WebElement of the Dropdown, Select class locator, Text to chose from
    public void selectElementFromDropDownbyValue(WebDriver driver, WebElement element, By Dropdown, String value) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(Dropdown));
        WebElement Dropwdown_Elem = FindElement(Dropdown, driver, Duration.ofSeconds(5), Duration.ofSeconds(1));
        Select dropDown = new Select(Dropwdown_Elem);
        dropDown.selectByValue(value);
    }

    //Click on the Dropdown button and then De-Select the choice based on Text
    //WebDriver, WebElement of the Dropdown, Select class locator, Text to chose from
    public void DeselectElementFromDropDownbyVisibleText(WebDriver driver, WebElement element, By Dropdown, String text) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(Dropdown));
        WebElement Dropwdown_Elem = FindElement(Dropdown, driver, Duration.ofSeconds(5), Duration.ofSeconds(1));
        Select dropDown = new Select(Dropwdown_Elem);
        dropDown.deselectByVisibleText(text);
    }

    //Click on the Dropdown button and then De-Select the choice based on Index
    //WebDriver, WebElement of the Dropdown, Select class locator, Text to chose from
    public void DeselectElementFromDropDownbyIndex(WebDriver driver, WebElement element, By Dropdown, int index) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(Dropdown));
        WebElement Dropwdown_Elem = FindElement(Dropdown, driver, Duration.ofSeconds(5), Duration.ofSeconds(1));
        Select dropDown = new Select(Dropwdown_Elem);
        dropDown.deselectByIndex(index);
    }

    //Click on the Dropdown button and then De-Select the choice based on Index
    //WebDriver, WebElement of the Dropdown, Select class locator, Text to chose from
    public void DeselectElementFromDropDownbyValue(WebDriver driver, WebElement element, By Dropdown, String Value) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(Dropdown));
        WebElement Dropwdown_Elem = FindElement(Dropdown, driver, Duration.ofSeconds(5), Duration.ofSeconds(1));
        Select dropDown = new Select(Dropwdown_Elem);
        dropDown.deselectByValue(Value);
    }

    //Scroll Down by certain given Pixel
    //Webdriver and pixel by which to scroll down
    public static void scrollDownBy(WebDriver driver, int val) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0," + val + ")");
    }

    //Scroll Up by certain given Pixel
    //WebDriver and pixel by which to scroll up
    public void scrollUpBy(WebDriver driver, int val) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, -" + val + ")");
    }

    //Scroll down to the bottom of the page
    //WebDriver
    public static void scrollDownPageBottom(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
    }

    //Scroll down to the bottom of the page
    //WebDriver
    public static void scrollUpPageTop(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0,0)");
    }


    //Match a text with the Driver Title
    //Param WebDriver, String Title to be matched
    public boolean checkTitle(WebDriver driver, String title) {
        try {
            if (driver.getTitle().equalsIgnoreCase(title)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public void waiting(long a) {
        try {
            Thread.sleep(a);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Select a checkbox
    //Param WebElement
    public void Checkbox_Select(WebElement element) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            if (element.isSelected()) {

            } else {
                element.click();
                Thread.sleep(2000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Un-Select a checkbox
    //Param WebElement
    public void checkboxUnselect(WebElement element, WebDriver driver) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            if (element.isSelected()) {
                element.click();
                assertThat(waitforDOMStability(driver)).isTrue();
            } else {
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Scroll Element in view
    //Param WebDriver and Element
    public void scrollToElement(WebDriver driver, WebElement element) {

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);

    }

    //Check for the Presence of Alert and Switch if needed
    //Param WeDriver and Boolean flag if true then accept else dismiss
    public boolean alertIsPresent(WebDriver driver, Boolean toDO) {
        try {
            wait.until((ExpectedConditions.alertIsPresent()));
            Alert alert = driver.switchTo().alert();
            if (toDO) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return true;
        } catch (NoAlertPresentException Ex) {
            return false;
        }
    }

    //Check for the Presence of Alert and Switch if needed
    //Param WeDriver and Boolean flag if true then accept else dismiss
    public boolean alertSendTextAccept(WebDriver driver, String text) {
        try {
            wait.until((ExpectedConditions.alertIsPresent()));
            Alert alert = driver.switchTo().alert();
            alert.sendKeys(text);
            alert.accept();
            return true;
        } catch (NoAlertPresentException Ex) {
            return false;
        }
    }

    //JavaScript Executor Click
    public void jsClick(WebDriver driver, WebElement element) {

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);

    }

    //Find the first or Default child Element from a List fo WebElement where a text matches
    //Param WebDriver, List of WebElements, by locator, String text
    //Returns WebElement Parent
    public WebElement matchTextFromChildOfListOfElementsReturnParentElement(WebDriver driver, List<WebElement> ListElem, By bylocator, String Sentdata) {
        WebElement ParentReturn = null;
        WebElement ChildReturn = null;
        for (WebElement Elem : ListElem) {
            ChildReturn = FindElement(bylocator, Elem, driver, Duration.ofSeconds(5), Duration.ofSeconds(1));
            String data = ChildReturn.getText();
            if (data.trim().contains(Sentdata)) {
                ParentReturn = Elem;
                break;
            }
        }
        return ParentReturn;
    }

    //Find the first or Default child Element from a List fo WebElement where a text matches
    //Param WebDriver, List of WebElements, by locator, String text
    //Returns WebElement Child
    public WebElement matchTextFromChildOfListOfElements(WebDriver driver, List<WebElement> ListElem, By bylocator, String Sentdata) {
        WebElement ChildReturn = null;
        for (WebElement Elem : ListElem) {
            ChildReturn = FindElement(bylocator, Elem, driver, Duration.ofSeconds(5), Duration.ofSeconds(1));
            String data = ChildReturn.getText();
            if (data.trim().contains(Sentdata)) {
                break;
            }
        }
        return ChildReturn;
    }

    /**
     * Return a single element first or default search result from a List of Element based on the Matched Text
     * *Param List of WebElement and Text Data
     * *Returns WebElement
     */
    public static WebElement returnWebElementFromList(List<WebElement> ListElem, String textData) {
        WebElement toFind = null;
        for (WebElement elem : ListElem) {
            if (elem.getText().contains(textData)) {
                toFind = elem;
                break;
            }
        }
        return toFind;
    }

    //Return count of occurence of a text from a series of WebElement
    //Param List of WebElement and Text Data
    //Returns Integer
    public int returnCountofOccurenceofText(List<WebElement> ListElem, String textdata) {
        int count = 0;
        for (WebElement elem : ListElem) {
            String ExtractedText = elem.getText();
            if (elem.getText().contains(textdata)) {
                count++;
            }
        }
        return count;
    }

    //Find the First Matching Child Element from a List of Element
    //Param Webdriver, List of WebElement, Locator, Timeout and Polltime
    //Returns WebElement
    public WebElement FindmatchingElementfromlist(WebDriver driver, List<WebElement> ListElem, By locator, Duration timeout, Duration polltime) {
        WebElement toReturn = null;
        turnOffImplicitWaits(driver);
        for (WebElement elem : ListElem) {
            if (toReturn == null) {
                toReturn = FindElement(locator, elem, driver, timeout, polltime);
            } else {
                break;
            }
        }
        turnOnImplicitWaits(driver);
        return toReturn;
    }

    //Find the count Matching Child Element from a List of Element
    //Param Webdriver, List of WebElement, Locator, Timeout and Polltime
    //Returns the Integer count
    public int FindcountofmatchingElementfromlist(WebDriver driver, List<WebElement> ListElem, By locator, Duration timeout, Duration polltime) {
        WebElement toReturn = null;
        int count = 0;
        turnOffImplicitWaits(driver);
        for (WebElement elem : ListElem) {
            toReturn = FindElement(locator, elem, driver, timeout, polltime);
            if (toReturn != null) {
                count++;
                toReturn = null;
            }
        }
        turnOnImplicitWaits(driver);
        return count;
    }

    //Embed Screenshots with Messages in Cucumber Report
    public static void embedScreenshot(WebDriver driver, Scenario result, String message) {
        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        result.attach(screenshot, "image/png", message);
        result.log(message);
    }

    public static Boolean Wait_Till_Element_Text_Changes(WebDriver driver, By loc, String Text, Duration time) {
        try {
            wait = new WebDriverWait(driver, time);
            return wait.until(ExpectedConditions.textToBe(loc, Text));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Boolean Wait_Till_Element_Text_Changes(WebDriver driver, By loc, Pattern pattern, Duration time) {
        try {
            wait = new WebDriverWait(driver, time);
            return wait.until(ExpectedConditions.textMatches(loc, pattern));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Boolean Wait_Till_Driver_URL_Changes(WebDriver driver, String tobeMatched, long time) {
        try {
            String currentUrl = driver.getCurrentUrl();
            int counter = 0;
            while (!currentUrl.equals(tobeMatched)) {
                counter++;
                Thread.sleep(time);
                currentUrl = driver.getCurrentUrl();
                if (counter >= 10) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Boolean Wait_Till_Driver_URL_Changes_without_Slash(WebDriver driver, String tobeMatched, long time) {
        try {
            String currentUrl = driver.getCurrentUrl();
            currentUrl = currentUrl.charAt(currentUrl.length() - 1) == '/' ? currentUrl.substring(0, currentUrl.length() - 1) : currentUrl;
            int counter = 0;
            while (!currentUrl.equals(tobeMatched)) {
                counter++;
                Thread.sleep(time);
                currentUrl = driver.getCurrentUrl();
                currentUrl = currentUrl.charAt(currentUrl.length() - 1) == '/' ? currentUrl.substring(0, currentUrl.length() - 1) : currentUrl;
                if (counter >= 10) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean Invisibility_of_Element(By loc, WebDriver driver, Duration time) {
        try {
            ActionMethods.turnOffImplicitWaits(driver);
            wait = new WebDriverWait(driver, time);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(loc));
            ActionMethods.turnOnImplicitWaits(driver);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //Embed Text Logs in Cucumber Report
    public static void embedText(Scenario result, String message) {
        result.log(message);
    }

    //Find Element with Fluent wait ignoring Exceptions
    //Param By Locator, WebElement, WebDriver, Timeout, polltime
    //Returns a WebElement or NUll in case of a Timeout
    public static WebElement FindElement(By Locator, WebDriver driver, Duration timeout, Duration polltime) {
        try {
            Wait<WebDriver> wait = new FluentWait<>(driver).withTimeout(timeout)
                    .pollingEvery(polltime).ignoring(StaleElementReferenceException.class).ignoring(NoSuchElementException.class);
            return wait.until(driver1 -> driver1.findElement(Locator));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    //Find list of Elements with Fluent wait ignoring Exceptions
    //Param By Locator, WebElement, WebDriver, Timeout, polltime
    //Returns List of WebElement or NUll in case of a Timeout
    public static List<WebElement> FindElements(By Locator, WebDriver driver, Duration timeout, Duration polltime) {
        try {
            Wait<WebDriver> wait = new FluentWait<>(driver).withTimeout(timeout)
                    .pollingEvery(polltime).ignoring(StaleElementReferenceException.class).ignoring(NoSuchElementException.class);
            return wait.until(driver1 -> driver1.findElements(Locator));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    //Find Element with Fluent wait ignoring Exceptions on Another Element
    //Param By Locator, WebElement, WebDriver, Timeout, polltime
    //Returns WebElement or NUll in case of a Timeout
    public static WebElement FindElement(By Locator, WebElement elem, WebDriver driver, Duration timeout, Duration polltime) {
        try {
            Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(timeout)

                    .pollingEvery(polltime).ignoring(StaleElementReferenceException.class).ignoring(NoSuchElementException.class);
            BiFunction<WebElement, By, WebElement> foo = (webElement, by) -> webElement.findElement(by);

            return wait.until(ExpectedConditions.visibilityOf(foo.apply(elem, Locator)));
        } catch (TimeoutException e) {
            return null;
        }
    }

    //Find Element with Fluent wait ignoring Exceptions on Another Element
    //Param By Locator, WebElement, WebDriver, Timeout, polltime
    //Returns WebElement or NUll in case of a Timeout
    public static List<WebElement> FindElements(By Locator, WebElement elem, WebDriver driver, Duration timeout, Duration polltime) {
        try {
            Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(timeout)

                    .pollingEvery(polltime).ignoring(StaleElementReferenceException.class).ignoring(NoSuchElementException.class);

            BiFunction<By, WebElement, List<WebElement>> foo = new BiFunction<By, WebElement, List<WebElement>>() {
                @Override
                public List<WebElement> apply(By by, WebElement webElement) {
                    return webElement.findElements(by);
                }
            };

            wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(elem, Locator));

            return foo.apply(Locator, elem);
        } catch (TimeoutException e) {
            return null;
        }
    }

    public static Boolean waitforDOMStability(WebDriver driver) {
        try {
            Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT_TIMEOUT_GENERIC));
            Boolean x = wait.until((ExpectedCondition<Boolean>) check -> {
                ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
                return true;
            });
            return x;
        } catch (TimeoutException e) {
            return false;
        }

    }

    //Turn off the implicit wait
    public static void turnOffImplicitWaits(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
    }

    //Turn on the implicit wait
    public static void turnOnImplicitWaits(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT_TIMEOUT_GENERIC));
    }

    //Assert the exact matching of a send in text with an Element Text
    //param is WebElement, and String text
    public void matchElementText(WebElement Elem, String text) {
        String extractedText = Elem.getText().trim();

        Assert.assertEquals(text, extractedText);
    }

    //Assert the whether a WebElement text contains the send in Text
    //param is WebElement, and String text
    public void containsElementText(WebElement Elem, String text) {
        String extractedText = Elem.getText().trim();
        Assert.assertTrue(extractedText.contains(text));
    }

    //Get WebElement text
    //param is WebElement
    //Returns WebElements text
    public String getElementText(WebElement elem) {
        String eleText = "";
        try {
            eleText = elem.getText().trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return eleText;
    }

    //Move To an Element
    //Param(true for clicking, False for not clicking), Webdriver, WebElement
    public static void MoveTo(WebDriver driver, boolean check, WebElement elem) {
        actions = new Actions(driver);
        if (check) {
            actions.moveToElement(elem).click().perform();
        } else {
            actions.moveToElement(elem).perform();
        }
    }


    public void waitForElementtoVisible(WebDriver driver, By locator, Duration time) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, time);
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
        }
    }

    public void dragAndDropElement(WebDriver driver, WebElement source, WebElement destination) {
        try {
            Actions action = new Actions(driver);
            action.dragAndDrop(source, destination).build().perform();
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
        }
    }


    public void uploadFile(String filePath) throws AWTException, InterruptedException {
        StringSelection stringSelection = new StringSelection(filePath);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);

        Robot robot = new Robot();

        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);

        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_V);

        Thread.sleep(1000);

        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }

    public static final boolean containsDigit(String s) {
        boolean containsDigit = false;

        if (s != null && !s.isEmpty()) {
            for (char c : s.toCharArray()) {
                if (containsDigit = Character.isDigit(c)) {
                    containsDigit = true;
                    break;
                }
            }
        }

        return containsDigit;
    }


    public static String findCurrencySymbol(String text) {
        String currency = "";
        // Regex to find any currency symbol in a text
        String regex = "\\p{Sc}";

        // Compile the ReGex
        Pattern p = Pattern.compile(regex);

        // Find match between the given string and the Regex using Pattern.matcher()
        Matcher m = p.matcher(text);
        // Find the next subsequence of the input subsequence that matches the pattern
        while (m.find()) {
            System.out.println(text.charAt(m.start()) + " - " + m.start());
            currency = text.charAt(m.start()) + " - " + m.start();
        }
        return currency;
    }

    public static void embedImage(Scenario result, String message, BufferedImage image) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            byte[] imageBytes = baos.toByteArray();
            result.attach(imageBytes, "image/png", message);
            result.log(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}