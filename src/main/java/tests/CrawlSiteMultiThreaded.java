package tests;

import org.testng.annotations.Test;
import pages.LandingPage;
import util.TestBase;
import util.WriteToFile;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by bill.witt on 8/19/2016.
 */
public class CrawlSiteMultiThreaded extends TestBase {

    private static boolean showInBrowser = false;
    private static boolean getPageText;
    private static boolean checkImages;

    private static String startingUrl = LandingPage.pageUrl;
    private static String validUrlsOutFile = "validUrls_" + getDateTime() + ".txt";
    private static String extractedTextOutFile = "dictionary_" + getDateTime() + ".txt";
    private static int threadCount = 5;


    @Test
    public static void startCrawl(boolean extractText, boolean checkBrokenImgs) {
        try {
            getPageText = extractText;
            checkImages = checkBrokenImgs;
            crawlSite(startingUrl);
        } catch (Exception e) {
            System.out.println("Failed to initiate Web crawl.");
            e.printStackTrace();
        }
    }

    private static void crawlSite(String initialUrl) throws Exception {
        HashSet<String> crawledList = new HashSet<>();
        Queue<String> toCrawlList = new LinkedList<>();
        int lineNumber = 1;

        ExecutorService threadPool = Executors.newFixedThreadPool(threadCount);
        Crawl crawl = new Crawl(initialUrl);
        List<Future<List<String>>> futures = new ArrayList<>();

        crawledList.add(initialUrl);
        futures.add( threadPool.submit(crawl));
        while (!futures.isEmpty()) {
            List<Future<List<String>>> completedFutures = new ArrayList<>();
            for (Future<List<String>> future : futures) {
                if (future.isDone()) {
                    List<String> newUrls = future.get();
                    for(String newUrl : newUrls) {
                        if (!toCrawlList.contains(newUrl) && !crawledList.contains(newUrl) && newUrl.contains(initialUrl)) {
                            System.out.println("New URL found: " + newUrl);
                            toCrawlList.add(newUrl);
                            WriteToFile.writeOutput(validUrlsOutFile, "\n[" + lineNumber + "] " + newUrl);
                            lineNumber++;
                        }
                    }
                    completedFutures.add(future);
                }
            }
            futures.removeAll(completedFutures);
            while ( !toCrawlList.isEmpty() ) {
                String urlToCrawl = toCrawlList.poll();
                futures.add(threadPool.submit(new Crawl(urlToCrawl)));
                crawledList.add(urlToCrawl);
            }
            System.out.println("Visited URLs: " + crawledList.size());
            System.out.println("URLs to visit (remaing futures): " + futures.size());
            Thread.sleep(100);
        }
    }
}
