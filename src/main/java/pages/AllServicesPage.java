package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import util.TestBase;

/**
 * Created by bill.witt on 6/17/2016.
 */
public class AllServicesPage extends TestBase {

    private static By searchWhatSelector = By.name("filter-category-select");
    private static By searchByResortSelector = By.name("filter-resort-select");
    private static By searchSubCategorySelector = By.name("filter-sub-category-select");
    private static By okBtn = By.name("filter-form-submit");

    public static void searchForService(String what, String resort, String sub) {
        LandingPage.openTabbedSection(LandingPage.Tabs.PLAN_YOUR_TRIP);
        WebElement okBtnElem = driver.findElement(okBtn);
        selectWhat(what);
        selectResort(resort);
        selectSubCategory(sub);
        okBtnElem.click();
    }

    private static void selectWhat(String what) {
        WebElement searchWhatSelectorElem = driver.findElement(searchWhatSelector);
        searchWhatSelectorElem.click();
        WebElement whatElem = driver.findElement(By.xpath("//option[@data-select-chain='" + what + "']"));
        whatElem.click();
        action.sendKeys(Keys.ESCAPE).perform();
    }

    private static void selectResort(String resort) {
        WebElement searchByResortSelectorElem = driver.findElement(searchByResortSelector);
        searchByResortSelectorElem.click();
        WebElement resortElem = driver.findElement(By.xpath("//option[text()='" + resort + "']"));
        resortElem.click();
        action.sendKeys(Keys.ESCAPE).perform();
    }

    private static void selectSubCategory(String sub) {
        WebElement searchSubCategorySelectorElem = driver.findElement(searchSubCategorySelector);
        searchSubCategorySelectorElem.click();
        WebElement subElem = driver.findElement(By.xpath("//option[text()='" + sub + "']"));
        subElem.click();
        action.sendKeys(Keys.ESCAPE).perform();
    }

}
