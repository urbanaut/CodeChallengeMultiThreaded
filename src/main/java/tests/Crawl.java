package tests;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import util.FindBrokenImages;
import util.TestBase;
import util.WriteToFile;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by bill.witt on 8/19/2016.
 */
public class Crawl extends TestBase implements Callable {

    private static boolean showInBrowser = false;
    private static boolean getPageText;
    private static boolean checkImages;

    private static String extractedTextOutFile = "dictionary_" + getDateTime() + ".txt";
    private String url;

    public Crawl(String url) {
        this.url = url;
    }

    @Override
    public List<String> call() {

        if (showInBrowser) {
            driver.navigate().to(url);
        }

        List<String> newUrls = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(url).userAgent("Chrome").get();
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
            System.out.println("Extracting page text...");
            String pageText = doc.body().text();
            pageText.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
            WriteToFile.writeOutput(extractedTextOutFile, "\n" + pageText + "\n");
        }

        if (checkImages) {
            System.out.print("Checking for broken images...");
            FindBrokenImages.checkImageLinks(url);
        }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        return newUrls;
    }
}
