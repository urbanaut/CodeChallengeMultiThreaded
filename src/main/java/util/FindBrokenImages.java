package util;

import org.jsoup.Jsoup;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by bill.witt on 6/30/2016.
 */
public class FindBrokenImages extends TestBase {

    private static int responseCode;
    private static String brokenImgFile = "brokenImages_" + getDateTime() + ".txt";

    public static void checkImageLinks(String url) throws Exception {
        System.out.print("Checking for broken images...");
        try {
            String imgUrl;
            Jsoup.connect(url).userAgent("Chrome").get();
            List<WebElement> imageList = driver.findElements(By.tagName("img"));

            for (WebElement image : imageList) {
                 imgUrl = image.getAttribute("src").trim();
                if (!imgUrl.equals("")) {
                    responseCode = RetrieveResponseCode.getResponseCode(imgUrl);
                    if (responseCode != 200) {
                        System.out.println("\nResponse Code: " + responseCode);
                        System.out.println("Broken image found at URL: " + imgUrl);
                        WriteToFile.writeOutput(brokenImgFile, imgUrl);
                    }
                }
            }
        } catch (Exception e) {
            if (responseCode == 200) {
                System.out.println("No broken images found.");
            }
        }
    }
}
