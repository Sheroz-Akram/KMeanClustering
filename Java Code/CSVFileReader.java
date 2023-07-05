import java.io.BufferedReader;
import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CSVFileReader {

    /**
     * This function reads a CSV file and returns its content as an n-dimensional array.
     *
     * @param fileName The path and name of the file that we want to read.
     * @return The content of the CSV file as an n-dimensional array.
     * @throws NumberFormatException If a non-float value is found in the CSV file.
     */
    public static List<List<Float>> readCSV(String fileName) throws NumberFormatException {
        List<List<Float>> csvData = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)))) {
            String line;
            while ((line = br.readLine()) != null) {
                List<Float> rowData = new ArrayList<>();
                String[] values = line.split(",");
                for (String value : values) {
                    try {
                        float floatValue = Float.parseFloat(value);
                        rowData.add(floatValue);
                    } catch (NumberFormatException e) {
                        throw new NumberFormatException("Non-float value found in the CSV file: " + value);
                    }
                }
                csvData.add(rowData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return csvData;
    }
}
