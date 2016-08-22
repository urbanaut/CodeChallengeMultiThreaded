package util;

import org.jsoup.nodes.Document;

/**
 * Created by bill.witt on 8/22/2016.
 */
public class ScrapePageText extends TestBase {

    private static String extractedTextOutFile = "dictionary_" + getDateTime() + ".txt";

    public static void scrape(Document doc) {
        System.out.println("Extracting page text...");
        String pageText = doc.body().text();
        pageText.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
        try {
            WriteToFile.writeOutput(extractedTextOutFile, "\n" + pageText + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
