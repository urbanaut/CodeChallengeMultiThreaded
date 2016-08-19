package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import util.TestBase;

import java.util.List;

/**
 * Created by bill.witt on 6/20/2016.
 */
public class SearchResultsPage extends TestBase {

    private static By resultItems = By.xpath("//div[@class='ListingResults-item']//*/h3/a");

    public static void readAndPostResults() {
        List<WebElement> resultRows = driver.findElements(resultItems);
        System.out.println("\nSearch Results:");
        for (WebElement row : resultRows) {
            System.out.println(row.getAttribute("innerHTML"));
        }
    }
}
