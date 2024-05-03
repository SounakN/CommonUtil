package utilities;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.util.Properties;

public class DownloadManager {

    Properties prop = PropertyUtil.getProperties();

    public String verifyDownloadInChrome(WebDriver driver) throws InterruptedException {


        if (prop.getProperty("Driver").equalsIgnoreCase("chrome")) {


            // Store the current window handle
            String mainWindow = driver.getWindowHandle();

            // open a new tab
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.open()");
            // switch to new tab
            // Switch to new window opened
            for (String winHandle : driver.getWindowHandles()) {
                driver.switchTo().window(winHandle);
            }
            // navigate to chrome downloads
            driver.get("chrome://downloads");

            JavascriptExecutor js1 = (JavascriptExecutor) driver;
            // wait until the file is downloaded
            Long percentage = (long) 0;
            while (percentage != 100) {
                try {
                    percentage = (Long) js1.executeScript("return document.querySelector('downloads-manager').shadowRoot.querySelector('#downloadsList downloads-item').shadowRoot.querySelector('#progress').value");
                    //System.out.println(percentage);
                } catch (Exception e) {
                    // Nothing to do just wait
                }
                Thread.sleep(1000);
            }
            // get the latest downloaded file name
            String fileName = (String) js1.executeScript("return document.querySelector('downloads-manager').shadowRoot.querySelector('#downloadsList downloads-item').shadowRoot.querySelector('div#content #file-link').text");
            // get the latest downloaded file url
            String sourceURL = (String) js1.executeScript("return document.querySelector('downloads-manager').shadowRoot.querySelector('#downloadsList downloads-item').shadowRoot.querySelector('div#content #file-link').href");
            // file downloaded location
            String donwloadedAt = (String) js1.executeScript("return document.querySelector('downloads-manager').shadowRoot.querySelector('#downloadsList downloads-item').shadowRoot.querySelector('div.is-active.focus-row-active #file-icon-wrapper img').src");
            System.out.println("Download deatils");
            System.out.println("File Name :-" + fileName);
            System.out.println("Donwloaded path :- " + donwloadedAt);
            System.out.println("Downloaded from url :- " + sourceURL);
            // print the details
            System.out.println(fileName);
            System.out.println(sourceURL);
            // close the downloads tab2
            driver.close();
            // switch back to main window
            driver.switchTo().window(mainWindow);
            return fileName;
        } else {
            System.out.println("Skipping Download Verification as browser is not chrome");
            return "";
        }
    }


}
