package util;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by bill.witt on 7/4/2016.
 */
public enum CrawlerOptions {
    NONE,
    TEXT,
    IMAGES,
    BOTH;

    private static List<String> conditions = new LinkedList<>();

    public static List<String> selectCrawlerOption(CrawlerOptions option) {
        try {
            switch (option) {
                case NONE:
                    conditions.add("false");
                    conditions.add("false");
                    return conditions;
                case TEXT:
                    conditions.add("true");
                    conditions.add("false");
                    return conditions;
                case IMAGES:
                    conditions.add("false");
                    conditions.add("true");
                    return conditions;
                case BOTH:
                    conditions.add("true");
                    conditions.add("true");
                    return conditions;
                default:
                    System.out.println("Invalid Crawler option selected.");
            }
        } catch (Exception e) {
            System.out.println("Error: Failed to select a valid crawler option.");
        }
        return conditions;
    }
}
