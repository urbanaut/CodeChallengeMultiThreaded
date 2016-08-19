package tests;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.testng.annotations.Test;
import pages.LandingPage;
import util.FindBrokenImages;
import util.TestBase;
import util.WriteToFile;

import java.util.*;

/**
 * Created by bill.witt on 6/20/2016.
 */
public class CrawlSite extends TestBase {

    private static boolean showInBrowser = false;
    private static boolean getPageText;
    private static boolean checkImages;

    private static String startingUrl = LandingPage.pageUrl;
    private static String validUrlsOutFile = "validUrls_" + getDateTime() + ".txt";
    private static String extractedTextOutFile = "dictionary_" + getDateTime() + ".txt";


    @Test
    public static void startCrawl(boolean extractText, boolean checkBrokenImgs) {
        try {
            getPageText = extractText;
            checkImages = checkBrokenImgs;
            crawlSite(startingUrl);
        } catch (Exception e) {
            System.out.println("Failed to initiate Web crawl.");
        }
    }

    private static List<String> crawl(String url) throws Exception {
        if (showInBrowser) {
            driver.navigate().to(url);
        }

        List<String> newUrls = new ArrayList<>();

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

        return newUrls;
    }

    private static void crawlSite(String initialUrl) throws Exception {
        HashSet<String> crawledList = new HashSet<>();
        Queue<String> toCrawlList = new LinkedList<>();
        int lineNumber = 1;

        toCrawlList.add(initialUrl);
        while(!toCrawlList.isEmpty()) {
            System.out.println("URLs to visit: " + toCrawlList.size());
            System.out.println("Visited URLs: " + crawledList.size());
            String url = toCrawlList.poll();
            try {
                List<String> newUrls = crawl(url);
                crawledList.add(url);
                for(String newUrl : newUrls) {
                    if (!toCrawlList.contains(newUrl) && !crawledList.contains(newUrl) && newUrl.contains(initialUrl)) {
                        System.out.println("New URL found: " + newUrl);
                        toCrawlList.add(newUrl);
                        WriteToFile.writeOutput(validUrlsOutFile, "\n[" + lineNumber + "] " + newUrl);
                        lineNumber++;
                    }
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                //e.printStackTrace();
            }
        }
    }

//    private static void crawlUrlList(Queue<String> queue) throws Exception {
//        int queueCount = queue.size();
//        String url;
//
//        try {
//            System.out.println("URLs to visit: " + queueCount);
//            System.out.println("Visited URLs: " + crawledList.size());
//            while(!queue.isEmpty()) {
//                url = queue.poll();
//                crawledList.add(url);
//                System.out.println("Visiting link: " + url);
//                crawlSite(url);
//            }
//        } catch (Exception e) {
//            System.out.println("Error encountered while crawling URL list.");
//        }
//    }

//    protected void finalize() throws Throwable {
//        try {
//            super.finalize();
//        } catch (Throwable t) {
//            throw t;
//        }
//    }
}
