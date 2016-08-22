package util;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by bill.witt on 8/22/2016.
 */
public class RetrieveResponseCode extends TestBase {

    public static int getResponseCode(String url) throws Exception {
        URL u = new URL(url);
        int responseCode;

        HttpURLConnection connection = (HttpURLConnection) u.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        responseCode = connection.getResponseCode();
        connection.disconnect();
        return responseCode;
    }
}
