package tests;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import util.FindBrokenImages;
import util.ScrapePageText;
import util.TestBase;
import util.WriteToFile;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by bill.witt on 8/19/2016.
 */
public class Crawl extends TestBase implements Callable {

    private static boolean showInBrowser = false;
    private static boolean logging = false;
    private static boolean getPageText = CodeChallenge.text;
    private static boolean checkImages = CodeChallenge.images;
    private static String error404LogFile = "src/main/resources/LogFiles/Error404/error404_" + getDateTime() + ".txt";
    private static String timeOutLogFile = "src/main/resources/LogFiles/TimeOuts/timeOut_" + getDateTime() + ".txt";
    private String url;

    Crawl(String url) {
        this.url = url;
    }

    @Override
    public List<String> call() throws Exception {
        if (showInBrowser) {
            driver.navigate().to(url);
        }

        List<String> newUrls = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(url)
                    .timeout(4000)
                    .ignoreContentType(true)
                    .userAgent("Chrome")
                    .get();
            Elements anchors = doc.select("a");
            for (Element anchor : anchors) {
                String discoveredUrl = anchor.attr("abs:href").toLowerCase();
                if (discoveredUrl.length() > 1
                        && !discoveredUrl.contains("@@")
                        && !discoveredUrl.contains("&")
                        && !discoveredUrl.contains("?")
                        && !discoveredUrl.contains("..")
                        && !discoveredUrl.contains(",")
                        && !discoveredUrl.contains("mobile")) {
                    newUrls.add(discoveredUrl);
                }
            }
            if (getPageText) {
                ScrapePageText.scrape(doc);
            }
            if (checkImages) {
                FindBrokenImages.checkImageLinks(url);
            }
        }
        catch (HttpStatusException e1) {
            System.err.println("Page not available, 404 error.");
            if (logging) WriteToFile.writeOutput(error404LogFile, "\n" + url);
        }
        catch (SocketTimeoutException e2) {
            System.err.println("SocketTimeoutException caught, error: " + e2.getLocalizedMessage());
            if (logging) WriteToFile.writeOutput(timeOutLogFile, "\n" + url);
        }
        catch (Exception e3) {
            System.err.println("Error: " + e3.getMessage());
            e3.printStackTrace();
        }
        return newUrls;
    }
}
