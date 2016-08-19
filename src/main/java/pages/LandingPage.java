package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import util.TestBase;

/**
 * Created by bill.witt on 6/14/2016.
 */
public class LandingPage extends TestBase {

    public static String pageUrl = "https://www.skiutah.com";
    private static String validationText = "Ski Utah";
    private static By titleVerification = By.xpath("//title[contains(text(),'" + validationText + "')]");
    private static String tabValidationText;
    private static By skiUtahBtn = By.className("HeaderMain-logoImg");

    // Tabs
    private static By planTab = By.xpath("//a[@title='Plan Your Trip']");
    private static By resortsTab = By.xpath("//a[@title='Resorts & Snow']");
    private static By storiesTab = By.xpath("//a[@title='Stories']");
    private static By dealsTab = By.xpath("//a[@title='Deals']");
    private static By passesTab = By.xpath("//a[@title='Passes']");
    private static By exploreTab = By.xpath("//a[@title='Explore']");

    public enum Tabs {
        PLAN_YOUR_TRIP,
        RESORTS_AND_SNOW,
        STORIES,
        DEALS,
        PASSES,
        EXPLORE
    }

    public static void navigateToPage() {
        driver.navigate().to(pageUrl);
    }

    public static void getPageTitle() throws InterruptedException {
        String pageTitle = driver.getTitle();
        WebElement titleVerificationElem = driver.findElement(titleVerification);
        if (titleVerificationElem.isEnabled())
            System.out.println("Landing page validation text '" + validationText + "' found.");
        Assert.assertTrue(pageTitle.contains(validationText), "Verification text did not match page title.");
    }

    private static WebElement selectTab(Tabs tab) {
        try {
            switch (tab) {
                case PLAN_YOUR_TRIP:
                    WebElement planTabElem = driver.findElement(planTab);
                    tabValidationText = "All Services";
                    return planTabElem;
                case RESORTS_AND_SNOW:
                    WebElement resortsTabElem = driver.findElement(resortsTab);
                    tabValidationText = "Get the Official Utah Snow Report";
                    return resortsTabElem;
                case STORIES:
                    WebElement storiesTabElem = driver.findElement(storiesTab);
                    tabValidationText = "Read About the Latest Happenings on the Slopes";
                    return storiesTabElem;
                case DEALS:
                    WebElement dealsTabElem = driver.findElement(dealsTab);
                    tabValidationText = "Ski and Snowboard The Greatest Snow on Earth";
                    return dealsTabElem;
                case PASSES:
                    WebElement passesTabElem = driver.findElement(passesTab);
                    tabValidationText = "Season Passes";
                    return passesTabElem;
                case EXPLORE:
                    WebElement exploreTabElem = driver.findElement(exploreTab);
                    tabValidationText = "Utah Areas 101";
                    return exploreTabElem;
                default:
                    System.out.println("Invalid tab selected.");
                    return null;
            }
        } catch (Exception e) {
            System.out.println("Error: Tab selection failed.");
        }
        return null;
    }

    public static void openTabbedSection(Tabs tab) {
        try {
            WebElement activeTab = selectTab(tab);
            if (activeTab != null && activeTab.isDisplayed())
                activeTab.click();
            WebElement assertionTextElem = driver.findElement(By.xpath("//title[contains(text(),'" + tabValidationText + "')]"));
            if (assertionTextElem.isEnabled())
                System.out.println("New tab validation text '" + tabValidationText + "' found.");
            Assert.assertTrue(assertionTextElem.isEnabled(), "Assertion Failed: Tab title text not found.");
        } catch (Exception e) {
            System.out.println("Error: Failed to open specified tab.");
        }
    }

    public static void selectSubMenuItem(Tabs tab, String item) {
        try {
            WebElement activeTab = selectTab(tab);
            if (activeTab != null && activeTab.isDisplayed())
                action.moveToElement(activeTab).perform();
            WebElement menuItem = driver.findElement(By.linkText(item));
            action.moveToElement(menuItem).click(menuItem).perform();
        } catch (Exception e) {
            System.out.println("Error: Unable to select sub-menu item.");
        }
    }

    public static void returnToLandingPage() {
        WebElement skiUtahBtnElem = driver.findElement(skiUtahBtn);
        skiUtahBtnElem.click();
    }

}
