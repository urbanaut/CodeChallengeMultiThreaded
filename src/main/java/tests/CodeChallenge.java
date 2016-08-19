package tests;

import org.testng.annotations.Test;
import pages.LandingPage;
import util.CrawlerOptions;
import util.TestBase;

/**
 * Created by bill.witt on 6/15/2016.
 */
public class CodeChallenge extends TestBase {

    private static CrawlerOptions option = CrawlerOptions.NONE;
    private static LandingPage.Tabs tab = LandingPage.Tabs.DEALS;
    private static String subMenuItem = "Beginner";
    private static String resortName = "Park City";
    private static String searchWhat = "Ski School";
    private static String searchByResort = "Brighton";
    private static String searchSubCategory = "Family and Kids";
    private static boolean text, images;

    @Test
    public static void runAutomation() throws InterruptedException {
        // Navigate to page
        LandingPage.navigateToPage();

        // Challenge #1: Get page title and verify
        LandingPage.getPageTitle();
//
//        // Challenge #2: Select navigation tab
//        LandingPage.openTabbedSection(tab);
//
//        // Challenge #3: Navigate through sub-pages and menus
//        LandingPage.selectSubMenuItem(tab,subMenuItem);
//
//        // Challenge #4: Return resort travel times from airport
//        ExploreUtahPage.compareResortAirportTimes(resortName);
//
//        // Challenge #5: Pass in search parameters
//        AllServicesPage.searchForService(searchWhat,searchByResort,searchSubCategory);
//        SearchResultsPage.readAndPostResults();
//        waitInSeconds(3);

        //// Challenge #6: Crawl every page on the site
            // Use CrawlerOption NONE
        //// Challenge #7: Get all page's text from Crawler
            // Use CrawlerOption TEXT
        //// Challenge #8: Find all page's broken images
            // Use CrawlerOption IMAGES

        text = Boolean.parseBoolean(CrawlerOptions.selectCrawlerOption(option).get(0));
        images = Boolean.parseBoolean(CrawlerOptions.selectCrawlerOption(option).get(1));
        CrawlSiteMultiThreaded.startCrawl(text, images);

    }
}
