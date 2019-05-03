package framework.base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import framework.listeners.CustomListeners;
import framework.utilities.BrowserType;
import framework.utilities.ExcelReader;
import framework.utilities.ExtentManager;
import framework.utilities.Utilities;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


public class KarFrame {

    /**
     * @author Karthikeyan
     */


    public static WebDriver driver;
    public static Properties config = new Properties();
    public static Properties OR = new Properties();
    public static FileInputStream fis;
    public static Logger log;
    public static ExcelReader excel;
    public static WebDriverWait wait;
    public static ExtentTest test;
    public static Actions actions;
    public static JavascriptExecutor js;
    //TODO dropdown by text
    static WebElement dropdown;
    static WebElement dropdown1;

    static {
        System.setProperty("myApp.log", System.getProperty("user.dir") + "/src/main/resources/logs/Application.log");
        System.setProperty("mySel.log", System.getProperty("user.dir") + "/src/main/resources/logs/Selenium.log");
        log = Logger.getLogger("devpinoyLogger");
        excel = new ExcelReader(System.getProperty("user.dir") + "/src/main/resources/excel/testdata.xlsx");
    }

    public ExtentReports rep = ExtentManager.getInstance();
    boolean videoRecord = false;

    //TODO assert verification
    public static void verifyEquals(String expected, String actual) throws IOException {

        try {
            Assert.assertEquals(actual, expected);
            log.debug(" Verification Passed : " + actual);

        } catch (Throwable t) {

            //  Utilities.captureScreenshot();
            // ReportNG
            Reporter.log("<br>" + "Verification failure : " + t.getMessage() + "<br>");
            Reporter.log("<a target=\"_blank\" href=" + Utilities.screenshotName + "><img src=" + Utilities.screenshotName
                    + " height=200 width=200></img></a>");
            Reporter.log("<br>");
            Reporter.log("<br>");
            // Extent Reports
            test.log(Status.FAIL, " Verification failed with exception : " + t.getMessage());
            //test.fail("Screen Shot :").addScreenCaptureFromPath(Utilities.screenshotName);
            log.debug(" Verification failed with exception : " + t.getMessage());

        }
    }

    //TODO page wait until the element present
    public static void wait(By waitForElementBy) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(waitForElementBy)));
        log.debug("Wait untill for :" + driver.findElement(waitForElementBy).getText());
    }

    //TODO page wait until the element present
    public static void wait(int time) throws InterruptedException {
        Thread.sleep(time * 1000);
        log.debug("Wait for : " + time + " Seconds");
    }

    //TODO Runmode Y/N (Execute Overall testSuite in Excel) (method name should be same with testcase)
    public static void runMode() {

        if (!(Utilities.isTestRunnable(CustomListeners.methodName, excel))) {   //Default select the Current Class Name as in Excel Sheet
            log.debug("Skipping the test Overall_Runner as the Run mode is NO");
            throw new SkipException("Skipping the test Overall_Runner as the Run mode is NO");
        }
    }

    //TODO Runmode Y/N (Execute Overall testSuite in Excel) (method name should be same with testcase)
    public static void runMode(String ExcelSheetName) {
        if (!(Utilities.isTestRunnable(ExcelSheetName, excel))) {               //Select the Given Excel Sheet Name
            log.debug("Skipping the test " + ExcelSheetName.toUpperCase() + " as the Run mode is NO");
            throw new SkipException("Skipping the test " + ExcelSheetName + " as the Run mode is NO");
        }
    }

    //TODO Runmode Y/N (Executes only data in a method) (method name should be same with testcase)
    public static void runMode(Hashtable<String, String> data) {

        if (!data.get("Runmode").equalsIgnoreCase("Y")) {
            log.debug("Skipping the test case as the Run mode for data set is NO");
            throw new SkipException("Skipping the test case as the Run mode for data set is NO");
        }
    }

    @BeforeSuite
    public void setUp() {

        if (driver == null) {

            try {
                fis = new FileInputStream(
                        System.getProperty("user.dir") + "/src/main/resources/properties/Config.properties");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                config.load(fis);
                log.debug("Config file loaded !!!");
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                fis = new FileInputStream(
                        System.getProperty("user.dir") + "/src/main/resources/properties/OR.properties");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                OR.load(fis);
                log.debug("OR file loaded !!!");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void openBrowser(BrowserType type, String URL, boolean VideoRecording) throws Exception {

        switch (type) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                driver.manage().window().maximize();
                break;

            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                driver.manage().window().maximize();
                break;

            case HEADLESS:
                WebDriverManager.phantomjs().setup();
                driver = new PhantomJSDriver();
                driver.manage().window().maximize();
                break;

            case IE:
                WebDriverManager.iedriver().setup();
                driver = new InternetExplorerDriver();
                driver.manage().window().maximize();
                break;

            case OPERA:
                WebDriverManager.operadriver().setup();
                driver = new OperaDriver();
                driver.manage().window().maximize();
                break;

            default:
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                driver.manage().window().maximize();
                break;

        }
        log.debug(type.toString() + " Launched !!!");
        driverSetup(URL, VideoRecording);
    }

    public void driverSetup(String URL, boolean VideoRecording) throws Exception {
        if (VideoRecording == true) {
            Utilities.startRecording();
            log.debug("Video Recording is ON and Started");
            videoRecord = true;

        } else {
            log.debug("Video Recording is OFF");
        }

        driver.get(URL);

        log.debug("Navigated to : " + URL);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")),
                TimeUnit.SECONDS);
        js = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, 10);
    }

    public void openBrowser(BrowserType type, String deviceName, String URL, boolean VideoRecording) throws Exception {
        switch (type) {
            case RESPONSIVE:
                WebDriverManager.chromedriver().setup();
                Map<String, String> mobileEmulation = new HashMap<>();
                mobileEmulation.put("deviceName", deviceName);
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
                driver = new ChromeDriver(chromeOptions);
                driver.manage().window().maximize();
                log.debug(type.toString() + " View of " + deviceName + " Launched !!!");
                break;

            default:
                throw new InvalidSelectorException("Device Name is only available for Responsive view");
        }

        driverSetup(URL, VideoRecording);

    }

    //TODO click
    public void click(By locator) {

        driver.findElement(locator).click();

        log.debug("Clicking on : " + driver.findElement(locator).getText());
        test.log(Status.INFO, "Clicking on : " + driver.findElement(locator).getText());
    }

    public void click(By locator, int getvalue) {

        driver.findElements(locator).get(getvalue).click();

        log.debug("Clicking on : " + driver.findElement(locator).getText());
        test.log(Status.INFO, "Clicking on : " + driver.findElement(locator).getText());
    }

    //TODO type
    public void type(By locator, Keys value) throws InterruptedException {
        driver.findElement(locator).click();
        driver.findElement(locator).clear();
        driver.findElement(locator).sendKeys(value);

        log.debug("Typing in : " + driver.findElement(locator).getText() + " entered value as " + value.name());
        test.log(Status.INFO, "Typing in : " + driver.findElement(locator).getText() + " entered value as " + value.name());

    }

    public void type(By locator, String value) throws InterruptedException {
        driver.findElement(locator).click();
        driver.findElement(locator).clear();
        driver.findElement(locator).sendKeys(value);

        log.debug("Typing in : " + driver.findElement(locator).getText() + " entered value as " + value);
        test.log(Status.INFO, "Typing in : " + driver.findElement(locator).getText() + " entered value as " + value);
        Thread.sleep(1000);
    }

    public void type(By locator, int getvalue, String value) throws InterruptedException {
        driver.findElements(locator).get(getvalue).click();
        driver.findElements(locator).get(getvalue).clear();
        driver.findElements(locator).get(getvalue).sendKeys(value);

        log.debug("Typing in : " + driver.findElement(locator).getText() + " entered value as " + value);
        test.log(Status.INFO, "Typing in : " + driver.findElement(locator).getText() + " entered value as " + value);
        Thread.sleep(1000);
    }

    public void type(By locator, int getvalue, Keys value) {
        driver.findElements(locator).get(getvalue).click();
        driver.findElements(locator).get(getvalue).clear();
        driver.findElements(locator).get(getvalue).sendKeys(value);

        log.debug("Typing in : " + driver.findElement(locator).getText() + " entered value as " + value.name());
        test.log(Status.INFO, "Typing in : " + driver.findElement(locator).getText() + " entered value as " + value.name());

    }

    public void dropdownSelect(By locator, String value) {

        dropdown = driver.findElement(locator);
        Select select = new Select(dropdown);
        select.selectByVisibleText(value);

        log.debug("Selecting from dropdown : " + driver.findElement(locator).getText() + " value as " + value);
        test.log(Status.INFO, "Selecting from dropdown : " + locator + " value as " + value);

    }

    public void dropdownSelect(By locator, int getValue, String value) {

        dropdown = driver.findElements(locator).get(getValue);
        Select select = new Select(dropdown);
        select.selectByVisibleText(value);

        log.debug("Selecting from dropdown : " + driver.findElement(locator).getText() + " value as " + value);
        test.log(Status.INFO, "Selecting from dropdown : " + locator + " value as " + value);

    }

    //TODO dropdown by index
    public void dropdownSelect(By locator, int index) {

        dropdown1 = driver.findElement(locator);
        Select select = new Select(dropdown1);
        select.selectByIndex(index);
        log.debug("Selecting from dropdown : " + driver.findElement(locator).getText() + " value as " + index);
        test.log(Status.INFO, "Selecting from dropdown : " + locator + " index as " + index);

    }

    public void dropdownSelect(By locator, int getValue, int index) {

        dropdown1 = driver.findElements(locator).get(getValue);
        Select select = new Select(dropdown1);
        select.selectByIndex(index);
        log.debug("Selecting from dropdown : " + driver.findElement(locator).getText() + " value as " + index);
        test.log(Status.INFO, "Selecting from dropdown : " + locator + " index as " + index);

    }

    //TODO element present or not
    public void isElementPresent(By by) {

        try {
            driver.findElement(by);
            log.debug(by.toString() + " is Present");
            Assert.assertTrue(true);

        } catch (NoSuchElementException e) {
            log.debug(by.toString() + " is Absent");
            Assert.assertTrue(false);
        }
    }

    //TODO 1 key stroke
    public void keyboardAction(int KeyEvent1) throws AWTException {
        Robot robot = new Robot();
        robot.keyPress(KeyEvent1);
        robot.keyRelease(KeyEvent1);
        log.debug("Keyboard Actions : " + KeyEvent.getKeyText(KeyEvent1).toUpperCase());
        test.log(Status.INFO, "Keyboard Actions : " + KeyEvent.getKeyText(KeyEvent1).toUpperCase());
    }

    //TODO 2 key stroke
    public void keyboardAction(int KeyEvent1, int KeyEvent2) throws AWTException {
        Robot robot = new Robot();
        robot.keyPress(KeyEvent1);
        robot.keyPress(KeyEvent2);
        robot.keyRelease(KeyEvent2);
        robot.keyRelease(KeyEvent1);

        log.debug("Keyboard Actions : " + KeyEvent.getKeyText(KeyEvent1).toUpperCase() + "+" + KeyEvent.getKeyText(KeyEvent2).toUpperCase());
        test.log(Status.INFO, "Keyboard Actions : " + KeyEvent.getKeyText(KeyEvent1).toUpperCase() + "+" + KeyEvent.getKeyText(KeyEvent2).toUpperCase());
    }

    //TODO 3 key stroke
    public void keyboardAction(int KeyEvent1, int KeyEvent2, int KeyEvent3) throws AWTException {
        Robot robot = new Robot();
        robot.keyPress(KeyEvent1);
        robot.keyPress(KeyEvent2);
        robot.keyPress(KeyEvent3);
        robot.keyRelease(KeyEvent3);
        robot.keyRelease(KeyEvent2);
        robot.keyRelease(KeyEvent1);

        log.debug("Keyboard Actions : " + KeyEvent.getKeyText(KeyEvent1).toUpperCase() + "+" + KeyEvent.getKeyText(KeyEvent2).toUpperCase() + "+" + KeyEvent.getKeyText(KeyEvent3).toUpperCase());
        test.log(Status.INFO, "Keyboard Actions : " + KeyEvent.getKeyText(KeyEvent1).toUpperCase() + "+" + KeyEvent.getKeyText(KeyEvent2).toUpperCase() + "+" + KeyEvent.getKeyText(KeyEvent3).toUpperCase());
    }

    //TODO mousehover click (stale click)
    public void mouseHoverClick(By destinationBy) throws AWTException, InterruptedException {

        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(destinationBy)).click().build().perform();
        Thread.sleep(1000);
        log.debug("Mouse Hover Click Actions : " + driver.findElement(destinationBy).getText());
        test.log(Status.INFO, "Mouse Hover Click Actions : " + driver.findElement(destinationBy).getText());
    }

    public void mouseHoverClick(By destinationBy, int getValue) throws AWTException, InterruptedException {

        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElements(destinationBy).get(getValue)).click().build().perform();
        log.debug("Mouse Hover Click Actions : " + driver.findElement(destinationBy).getText());
        test.log(Status.INFO, "Mouse Hover Click Actions : " + driver.findElement(destinationBy).getText());
    }

    //TODO double click
    public void doubleClick(By destinationBy) throws AWTException, InterruptedException {

        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(destinationBy)).doubleClick().build().perform();
        log.debug("Mouse Hover Doubleclick Actions : " + driver.findElement(destinationBy).getText());
        test.log(Status.INFO, "Mouse Hover Doubleclick Actions : " + driver.findElement(destinationBy).getText());

    }

    public void doubleClick(By destinationBy, int getValue) throws AWTException, InterruptedException {

        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElements(destinationBy).get(getValue)).doubleClick().build().perform();
        log.debug("Mouse Hover Doubleclick Actions : " + driver.findElement(destinationBy).getText());
        test.log(Status.INFO, "Mouse Hover Doubleclick Actions : " + driver.findElement(destinationBy).getText());

    }

    //TODO page scroll
    public void scroll(int getValue) throws AWTException, InterruptedException {

        js.executeScript("scroll(0," + getValue + ")");
        log.debug("Scroll Actions : " + getValue + " Pixels ");

    }

    //TODO page scroll into view
    public void scroll(By destinationBy) throws AWTException, InterruptedException {

        js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(destinationBy));
        log.debug("Scroll Actions into the View of " + driver.findElement(destinationBy).getText());
    }

    @AfterSuite
    public void tearDown() throws Exception {
        try {
            if (driver != null) {
                driver.quit();
            }
            if (videoRecord == true) {
                log.debug("Video Recording is Stopped");
            }
            log.debug("Browser Closed !!!");
        } catch (Exception e) {
        }
    }
}
