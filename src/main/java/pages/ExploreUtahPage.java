package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import util.TestBase;

/**
 * Created by bill.witt on 6/17/2016.
 */
public class ExploreUtahPage extends TestBase {

    private static By exploreUtahBtn = By.xpath("//div/h1/span[contains(text(),'Explore Utah')]");

    public static void compareResortAirportTimes(String resort) throws InterruptedException {
        try {
            LandingPage.returnToLandingPage();
            WebElement exploreUtahBtnElem = driver.findElement(exploreUtahBtn);
            waitForElement(exploreUtahBtnElem);
            action.moveToElement(exploreUtahBtnElem).click(exploreUtahBtnElem).perform();
            String distance = getResortDistance(resort);
            System.out.println("The distance from the closest airport to " + resort + " resort is " + distance + " miles.");
        } catch (Exception e) {
            System.out.println("Error: unable to find distance from submitted resort.  Check resort name spelling.");
        }
    }

    private static String getResortDistance(String resort) {
        WebElement resortDistanceElem = driver.findElement(By.xpath("//div//label/span[@class='map-Area-shortName' and text()='"
                + resort + "']/following-sibling::span[@class='map-Area-shortValue map-Area-shortValue--distance']"));
        return resortDistanceElem.getAttribute("innerHTML");
    }
}
