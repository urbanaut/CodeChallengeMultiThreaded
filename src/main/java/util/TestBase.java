package util;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by bill.witt on 6/14/2016.
 */
public class TestBase {

    protected static WebDriver driver;
    protected static Actions action;
    protected static JavascriptExecutor jsEx;
    protected static WebDriverWait wait;
    private static String driverPath = "src\\main\\resources\\drivers\\chromedriver.exe";


    @BeforeClass
    public static void initialize() {
        System.setProperty("webdriver.chrome.driver",driverPath);
        driver = new ChromeDriver();
        action = new Actions(driver);
        jsEx = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, 20);

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }


    protected static void waitInSeconds(int seconds) throws InterruptedException {
        int milliseconds = seconds * 1000;
        Thread.sleep(milliseconds);
    }

    protected static void waitForElement(WebElement element) throws InterruptedException {
        while (!element.isDisplayed()) {
            waitInSeconds(2);
        }
    }

    protected static String getDateTime() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        return dateFormat.format(date);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null)
            driver.quit();
    }

}
