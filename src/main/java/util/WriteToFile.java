package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * Created by bill.witt on 7/1/2016.
 */
public class WriteToFile extends TestBase {

    public static void writeOutput(String fileName, String output) throws Exception {
        File outputFile = new File(fileName);
        if (!outputFile.exists())
            outputFile.createNewFile();
        FileWriter fileWriter = new FileWriter(outputFile.getName(),true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(output);
        bufferedWriter.flush();
        bufferedWriter.close();
    }
}
