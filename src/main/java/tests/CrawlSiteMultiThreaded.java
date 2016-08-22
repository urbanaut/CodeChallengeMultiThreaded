package tests;

import org.testng.annotations.Test;
import pages.LandingPage;
import util.TestBase;
import util.WriteToFile;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by bill.witt on 8/19/2016.
 */
public class CrawlSiteMultiThreaded extends TestBase {

    private static String startingUrl = LandingPage.pageUrl;
    private static String validUrlsOutFile = "src/main/resources/LogFiles/ValidURLs/validUrls_" + getDateTime() + ".txt";
    private static int threadCount = 5;


    @Test
    public static void startCrawl() {
        try {
            crawlSite(startingUrl);
        } catch (Exception e) {
            System.out.println("Failed to initiate Web crawl.");
            e.printStackTrace();
        }
    }

    private static void crawlSite(String initialUrl) throws Exception {
        HashSet<String> crawledList = new HashSet<>();
        Queue<String> toCrawlList = new LinkedList<>();
        ConcurrentLinkedQueue<Future<List<String>>> futures = new ConcurrentLinkedQueue<>();
        ExecutorService threadPool = Executors.newFixedThreadPool(threadCount);
        int lineNumber = 1;

        Crawl crawl = new Crawl(initialUrl);
        crawledList.add(initialUrl);
        futures.add(threadPool.submit(crawl));
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
            System.out.println("Visited URLs: " + crawledList.size());
            System.out.println("URLs to visit: " + futures.size());
            futures.removeAll(completedFutures);
            while ( !toCrawlList.isEmpty() ) {
                String urlToCrawl = toCrawlList.poll();
                futures.add(threadPool.submit(new Crawl(urlToCrawl)));
                crawledList.add(urlToCrawl);
            }
            Thread.sleep(300);
        }
    }
}
