package utilities;

import driver.MobileFactory;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.connection.ConnectionState;
import io.appium.java_client.android.connection.ConnectionStateBuilder;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static driver.BasicConstants.EXPLICIT_WAIT_TIMEOUT_GENERIC;
import static driver.BasicConstants.IMPLICIT_WAIT_TIMEOUT_GENERIC;
import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;
import static io.appium.java_client.touch.offset.PointOption.point;
import static java.time.Duration.ofMillis;
import static java.time.Duration.ofSeconds;

public class MobileActionMethods {


    private AppiumDriver driver;
    public Properties prop;

    public MobileActionMethods() {
        this.driver = MobileFactory.getDriverService();
        prop = PropertyUtil.getProperties();
    }

    /*
     *
     * Wait for Element method
     *
     */
    public boolean waitForElement(By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(MobileFactory.getDriverService(),
                    Duration.ofSeconds(EXPLICIT_WAIT_TIMEOUT_GENERIC));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (TimeoutException ex) {

            ex.printStackTrace();
            return false;
        }
    }

    public boolean waitForElement(By locator, long time) {
        try {
            WebDriverWait wait = new WebDriverWait(MobileFactory.getDriverService(),
                    Duration.ofSeconds(time));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (TimeoutException ex) {
            return false;
        }
    }

    public boolean waitForElementPresence(By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(MobileFactory.getDriverService(),
                    Duration.ofSeconds(EXPLICIT_WAIT_TIMEOUT_GENERIC));
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return true;
        } catch (TimeoutException ex) {

            ex.printStackTrace();
            return false;
        }
    }

    public void waitForElementDisplay(By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(MobileFactory.getDriverService(), Duration.ofSeconds(EXPLICIT_WAIT_TIMEOUT_GENERIC));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException ex) {
            ex.printStackTrace();
        }
    }


    public void setImplicitWait(int time) {
        MobileFactory.getDriverService().manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
    }

    /*
     *
     * is element displayed
     *
     */
    public boolean isElementDisplayed(By locate) {
        try {
            return waitForElement(locate);
        } catch (Exception ex) {
            return false;
        }
    }

    /*
     *
     * Click Element
     *
     */
    public void clickElement(By locator) {
        tapByElement(locator);
    }

    public void clickElement(WebElement Element) {
        tapByElement(Element);
    }

    public void selectAllElements(By locator) {
        List<WebElement> element = MobileFactory.getDriverService().findElements(locator);
        int count = element.size() - 1;
        while (element.size() > 0) {
            element.get(count).click();
            count--;
        }

    }


    /*
     *
     * get Attribute
     *
     */
    public String getAttribute(By locator, String attribute) {
        try {
            waitForElement(locator);
            return MobileFactory.getDriverService().findElement(locator).getAttribute(attribute);
        } catch (Exception ex) {

            return String.format("Unable to get text of webelement %s", locator);

        }
    }


    /*
     *
     * get Text of locator
     *
     */
    public String getText(By locator) {
        try {
            return MobileFactory.getDriverService().findElement(locator).getText();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public int getSize(By locate) {
        return MobileFactory.getDriverService().findElements(locate).size();
    }


    /*
     * To check if the alert is present or not
     *
     *
     * */
    public boolean isAlertPresent() throws IOException {
        boolean foundAlert;
        WebDriverWait wait = new WebDriverWait(MobileFactory.getDriverService(),
                Duration.ofSeconds(EXPLICIT_WAIT_TIMEOUT_GENERIC));
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

    public List<String> InnertextofIcons(By locator) {
        List<WebElement> elements = MobileFactory.getDriverService().findElements(locator);
        List<String> lst = new ArrayList<>();
        for (WebElement element : elements) {
            lst.add(element.getAttribute("content-desc"));

        }
        return lst;
    }

    public boolean compareString(List<String> strs, By locator) {
        boolean flag = false;
        List<String> str_Elements = InnertextofIcons(locator);
        for (int i = 0; i < strs.size(); i++) {
            if (str_Elements.get(i).equals(strs.get(i))) {
                flag = true;
            }

        }
        return flag;
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

    public Point getLocationfromListOfElements(List<WebElement> elements, int id) {
        Point point;
        try {
            point = elements.get(id).getLocation();
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


    //Converting set to list
    public List<String> convertSetToList(Set<String> str) {
        List<String> listString = new LinkedList<>();
        for (String st : str) {
            listString.add(st);
        }

        return listString;
    }

    //Compare two list of strings having same numbers and size at same index
    public boolean compareString(List<String> strs, List<String> strs1) {
        boolean flag = false;
        try {

            for (int i = 0; i < strs.size(); i++) {
                if (strs.size() != strs1.size()) {
                    flag = false;
                    break;
                }
                if (strs1.get(i).trim().equals(strs.get(i).trim())) {
                    flag = true;
                } else {
                    flag = false;
                    break;
                }
            }
            return flag;

        } catch (Exception ex) {
            return false;
        }
    }


    //Method to store all unique webelement text, works for android mobile
    public Set<String> storeUniqueTextOfElements(By locator) {
        Set<String> element_text = new LinkedHashSet<>();
        try {
            List<WebElement> elements = MobileFactory.getDriverService().findElements(locator);
            for (WebElement element : elements) {
                element_text.add(element.getAttribute("content-desc"));

            }
            return element_text;

        } catch (Exception ex) {
            ex.printStackTrace();
            return element_text;
        }
    }

    public List<String> storeTextOfElements(By locator) {
        List<String> element_text = new LinkedList<>();
        try {
            List<WebElement> elements = MobileFactory.getDriverService().findElements(locator);
            for (WebElement element : elements) {
                element_text.add(element.getAttribute("text"));
            }
            return element_text;
        } catch (Exception ex) {
            ex.printStackTrace();
            return element_text;
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

    public Boolean clickElementFromDropDown_usingElement(List<WebElement> elem, String text) {
        try {

            WebElement element = elem.stream().filter(s -> s.getText().equalsIgnoreCase(text)).findFirst().orElse(null);

            new TouchAction((PerformsTouchActions) MobileFactory.getDriverService())
                    .tap(TapOptions.tapOptions().withElement(element(element)))
                    .waitAction(waitOptions(ofMillis(250))).perform();

            return true;
        } catch (Exception e) {
            return false;
        }

    }


    public Boolean TapByPoint(WebElement elem, double x, double y) {
        try {
            Rectangle elRect = elem.getRect();
            int x1 = (int) (elRect.x * x);
            int y1 = (int) (elRect.y * y);
            /*int Height = elRect.height;
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


    //Swiping right to left from webelement
    public void swipeRightToLeftFromElement(By locator) throws IOException {
        Point point = getLocation(locator);
        int startX = (int) (point.getX() * 0.95);
        int endX = (int) (point.getX() * 0.05);
        int startY = point.getY();
        scroll(startX, startY, endX, startY);
    }

    //Swiping right to left from webelement
    public void swipeLeftToRightFromElement(By locator) throws IOException {
        Point point = getLocation(locator);
        int startX = (int) (point.getX() * 0.95);
        int endX = (int) (point.getX() * 0.05);
        int startY = point.getY();
        scroll(endX, startY, startX, startY);
    }

    //Swiping right to left from webelement

    public void swipeRightToLeftFromElementFromListOfWebElements(By locator, int id) throws IOException {
        List<WebElement> elements = elementsReturn(locator);
        Point point = getLocationfromListOfElements(elements, id);
        int startX = (int) (point.getX() * 0.95);
        int endX = (int) (point.getX() * 0.05);
        int startY = point.getY();
        scroll(startX, startY, endX, startY);

    }

    public void swipeRightToLeft() throws IOException {
        Dimension size = driver.manage().window().getSize();
        int startY = (int) (size.height / 2);
        int startX = (int) (size.width * 0.60);
        int endX = (int) (size.width * 0.05);
        scroll(startX, startY, endX, startY);
    }

    //Scroll method via x,y cordinates
    public void scroll(int startx, int starty, int endx, int endy) throws IOException {
        TouchAction touchAction = new TouchAction((PerformsTouchActions) MobileFactory.getDriverService());
        touchAction.press(PointOption.point(startx, starty))
                .waitAction(waitOptions(ofSeconds(1)))
                .moveTo(PointOption.point(endx, endy))
                .release()
                .perform();
    }


    public void scrollVertically() throws IOException {
        Dimension dimension = getSizeWindows();
        int startY = (int) (dimension.getHeight() * 0.6);
        int startX = dimension.getWidth() / 2;
        int endY = (int) (dimension.getHeight() * 0.40);
        scroll(startX, startY, startX, endY);

    }

    public void scrollHorizontally() throws IOException {
        Dimension dimension = getSizeWindows();
        int startY = (int) (dimension.getHeight() * 0.6);
        int startX = dimension.getWidth() / 2;
        int endY = (int) (dimension.getHeight() * 0.40);
        //scroll(startX, startY, startX, endY);
        scroll(startX, endY, startX, startY);

    }

    public void scrollVerticallyTopToBottom() throws IOException {

        Dimension dimension = getSizeWindows();
        int endY = (int) (dimension.getHeight() * 0.7);
        int startX = dimension.getWidth() / 2;
        int startY = (int) (dimension.getHeight() * 0.40);
        scroll(startX, startY, startX, endY);

    }

    public void scrollVerticallyBottomToTop_1() throws IOException {

        Dimension dimension = getSizeWindows();
        int endY = (int) (dimension.getHeight() * 0.4);
        int startX = dimension.getWidth() / 2;
        int startY = (int) (dimension.getHeight() * 0.7);
        scroll(startX, startY, startX, endY);

    }

    //Pass webElement, By Locator, Count of scrolling and true if you want to Scroll Up or false if you want to scroll Down
    public Boolean scrollTillFoundUsingElement(WebElement elem, By loc, int scrollcount, Boolean Check) throws InterruptedException {
        int counter = 0;
        while (elem == null) {
            try {
                counter++;
                if (counter <= scrollcount) {

                    if (Check == true) {
                        scrollVerticallyTopToBottom();
                    } else {
                        scrollVerticallyBottomToTop_1();
                    }
                    Thread.sleep(1000);
                } else {
                    break;
                }
                elem = driver.findElement(loc);
            } catch (org.openqa.selenium.NoSuchElementException e) {
                continue;
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }

        return elem.isDisplayed();
    }


    public void scrollVerticallyBottomToTop() throws IOException {

        Dimension dimension = getSizeWindows();
        int endY = (int) (dimension.getHeight() * 0.70);
        int startY = (int) (dimension.getHeight() * 0.40);
        int startX = dimension.getWidth() / 2;
        scroll(startX, startY, startX, endY);
    }

    public void scrollVerticallyWithCoordinate(double y_start, double y_end) throws IOException {

        Dimension dimension = getSizeWindows();
        int startY = (int) (dimension.getHeight() * y_start);
        int startX = dimension.getWidth() / 2;
        int endY = (int) (dimension.getHeight() * y_end);
        scroll(startX, startY, startX, endY);
    }

    public void scrollVerticallyTopToBottomLarge() throws IOException {

        Dimension dimension = getSizeWindows();
        int startY = (int) (dimension.getHeight() * 0.4);
        int startX = dimension.getWidth() / 2;
        int endY = (int) (dimension.getHeight() * 0.9);
        scroll(startX, startY, startX, endY);
    }

    public void scrollVerticallyLarge() throws IOException {

        Dimension dimension = getSizeWindows();
        int startY = (int) (dimension.getHeight() * 0.7);
        int startX = dimension.getWidth() / 2;
        int endY = (int) (dimension.getHeight() * 0.09);
        scroll(startX, startY, startX, endY);
    }


    public Map<String, String> searchScrollClickAndMapElement(By locator, By locatorVisibleInView, By carosualTitle) throws
            IOException {
        setImplicitWait(0);
        int count = 0;
        Map<String, String> map = new LinkedHashMap<>();
        while (!isElementDisplayed(locator)) {
            swipeRightToLeftFromElement(locatorVisibleInView);
            count++;
            if (count > 20) {
                break;
            }
        }
        clickElement(locator);
        map.put(getAttribute(locator, "content-desc"), getText(carosualTitle));
        setImplicitWait(IMPLICIT_WAIT_TIMEOUT_GENERIC);
        return map;

    }

    public boolean scrollVerticallyUntilElementfound(By locator, By locator1) throws IOException {

        boolean flag = false;
        setImplicitWait(0);
        int count = 0;
        while (getSize(locator) == 0 && getSize(locator1) == 0) {
            scrollVerticallyLarge();
            count++;
            if (count > 20) {
                break;
            }
        }
        if (getSize(locator) > 0 && getSize(locator1) > 0) {
            flag = true;
        }
        setImplicitWait(IMPLICIT_WAIT_TIMEOUT_GENERIC);
        return flag;
    }

    public boolean scrollVerticallyUntilSingleElementfound(By locator) throws IOException {

        boolean flag = false;
        setImplicitWait(0);
        int count = 0;
        while (getSize(locator) == 0) {
            scrollVerticallyLarge();
            count++;
            if (count > 20) {
                break;
            }
        }

        if (getSize(locator) > 0) {
            flag = true;
        }
        setImplicitWait(IMPLICIT_WAIT_TIMEOUT_GENERIC);
        return flag;
    }


    public boolean swipeHorizontallyUntilElementFound(By swipeElementFromLocator, By findLocator) throws
            IOException {

        boolean flag = false;
        int count = 0;
        setImplicitWait(0);
        while (getSize(findLocator) == 0) {
            swipeRightToLeftFromElement(swipeElementFromLocator);
            if (count > 20) {
                break;
            }
        }

        if (getSize(findLocator) > 0) {
            flag = true;
        }
        setImplicitWait(IMPLICIT_WAIT_TIMEOUT_GENERIC);
        return flag;
    }


    public String getPageSource() {
        return MobileFactory.getDriverService().getPageSource();
    }


    public void navigateBack() {
        MobileFactory.getDriverService().navigate().back();
    }

    public List<String> storeElementsText(By locator) {
        List<String> element_text = new LinkedList<>();
        try {
            List<WebElement> elements = MobileFactory.getDriverService().findElements(locator);
            for (WebElement element : elements) {
                element_text.add(element.getAttribute("text"));
            }
            return element_text;
        } catch (Exception ex) {
            return element_text;
        }
    }

    public Map<String, String> mapElementWithElement(By locator, By locator1) {
        Map<String, String> element_text = new LinkedHashMap<>();
        try {
            List<String> elements_String = storeElementsText(locator);
            List<String> elements_String1 = storeElementsText(locator1);
            for (int i = 0; i < elements_String.size(); i++) {
                element_text.put(elements_String.get(i), elements_String1.get(i));
            }
            return element_text;

        } catch (Exception ex) {
            return element_text;
        }
    }

    public List<String> convertMapValueInList(Map<String, String> items) {
        List<String> list = new LinkedList<>();
        try {
            for (Map.Entry<String, String> entry : items.entrySet()) {
                list.add(entry.getValue());
            }
            return list;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<WebElement> elementsReturn(By locator) {
        List<WebElement> elements = MobileFactory.getDriverService().findElements(locator);
        return elements;
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

    public void sendKeysValueFromProp(By locator, String key) {
        clickElement(locator);
        MobileFactory.getDriverService().findElement(locator).sendKeys(prop.getProperty(key));
    }

    public void clearText(By locator) {
        clickElement(locator);
        MobileFactory.getDriverService().findElement(locator).clear();
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

    public WebElement getElement(By locator) {
        WebElement element = MobileFactory.getDriverService().findElement(locator);
        return element;
    }

    public void tapExtremeRightOfElement(By locator) {
        waitForElement(locator);
        Point point = getLocation(locator);
        int xCordinate = point.x + 900;
        int yCordinate = point.y + getElement(locator).getSize().getHeight() - 1;
        new TouchAction((PerformsTouchActions) MobileFactory.getDriverService()).tap(PointOption.point(xCordinate, yCordinate)).perform();
    }


    public List<WebElement> returnWebElement(By locator) {
        List<WebElement> elements = MobileFactory.getDriverService().findElements(locator);
        return elements;
    }

    public void clickWebelementFromList(int index, By locator_elements) {
        List<WebElement> elements = returnWebElement(locator_elements);
        elements.get(index).click();
    }

    public String getAttributeFromListOfElements(By locator, int index) {
        List<WebElement> elements = MobileFactory.getDriverService().findElements(locator);
        {
            return elements.get(index).getAttribute("text");
        }
    }


    public long generateRandomMobileNumberStartsWith1() {
        return (long) (Math.random() * 100000 + 1234500000L);
    }

    public void switchFrame(By locator) {
        MobileFactory.getDriverService().switchTo().frame(getElement(locator));
    }

    public void switchToDefaultFrame() {
        MobileFactory.getDriverService().switchTo().defaultContent();
    }

    public void waitForToast(By locator) throws IOException {
        WebDriverWait waitForToast = new WebDriverWait(MobileFactory.getDriverService(),
                Duration.ofSeconds(EXPLICIT_WAIT_TIMEOUT_GENERIC));
        waitForToast.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public String getToastMessage(By locator) throws IOException {
        waitForToast(locator);
        return getText(locator);
    }

    public String getURLFromString(String str) {
        String getUrl = "";
        List<String> list
                = new ArrayList<>();
        String regex
                = "\\b((?:https?|ftp|file):"
                + "//[-a-zA-Z0-9+&@#/%?="
                + "~_|!:, .;]*[-a-zA-Z0-9+"
                + "&@#/%=~_|])";

        Pattern p = Pattern.compile(
                regex,
                Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(str);
        while (m.find()) {
            getUrl = str.substring(
                    m.start(0), m.end(0));
        }
        if (list.size() == 0) {
            return "";
        }
        return getUrl;
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
        WebElement element1 = ActionMethods.FindElement(locator, MobileFactory.getDriverService(), Duration.ofSeconds(10), Duration.ofSeconds(1));
        if (element1 != null) {
            new TouchAction((PerformsTouchActions) MobileFactory.getDriverService())
                    .tap(TapOptions.tapOptions().withElement(element(element1)))
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
        waitForElement(locator);
        Point point = getLocation(locator);
        new TouchAction((PerformsTouchActions) MobileFactory.getDriverService()).tap(PointOption.point(point.x, point.y)).perform();
    }

    public void tapByCoordinates(Point point) {
        new TouchAction((PerformsTouchActions) MobileFactory.getDriverService()).tap(PointOption.point(point.x, point.y)).perform();
    }

    public boolean clickElementFromListOfElementsByText(By locator, String text) {
        try {
            boolean flag = false;
            List<WebElement> elements = MobileFactory.getDriverService().findElements(locator);
            Iterator<WebElement> iterator = elements.iterator();
            while (iterator.hasNext()) {
                WebElement ele = iterator.next();
                if (ele.getText().toString().equals(text)) {
                    ele.click();
                    flag = true;
                    break;
                }
            }
            return flag;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean scrollBottomToTopUntilSingleElementFound(By locator) throws IOException {

        boolean flag = false;
        setImplicitWait(0);
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
        setImplicitWait(IMPLICIT_WAIT_TIMEOUT_GENERIC);
        return flag;
    }

    public boolean scrollToptoBottomUntilSingleElementFound(By locator) throws IOException {

        boolean flag = false;
        setImplicitWait(0);
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
        setImplicitWait(IMPLICIT_WAIT_TIMEOUT_GENERIC);
        return flag;
    }

    public void Close_session_popUp(WebDriver driver) {
        ActionMethods.turnOffImplicitWaits(driver);
        By Close_Session_popup_Close_button = By.xpath("//android.widget.TextView[@resource-id='com.upgrad.student.test:id/tv_close']");
        WebElement Close_Session = ActionMethods.FindElement(Close_Session_popup_Close_button, driver, Duration.ofSeconds(5), Duration.ofSeconds(1));
        if (Close_Session != null) {
            tapByElement(Close_Session);
        }
        ActionMethods.turnOnImplicitWaits(driver);
    }
}