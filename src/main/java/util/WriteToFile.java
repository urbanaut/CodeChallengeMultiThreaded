package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * Created by bill.witt on 7/1/2016.
 */
public class WriteToFile extends TestBase {

    public static void writeOutput(String fileName, String output) {
        try {
            File outputFile = new File(fileName);
            FileWriter fileWriter = new FileWriter(outputFile, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(output);
            bufferedWriter.flush();
            bufferedWriter.close();
        }
        catch (Exception e) {
            System.err.println("Failed to write data to file, error: " + e.getMessage());
        }
    }
}
