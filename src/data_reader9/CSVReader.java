package data_reader9;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CSVReader {
    public static List<String[]> readCSV(String filePath) throws IOException {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                data.add(line.split(",", -1)); // ✅ Use split(",", -1) for robust CSV parsing
            }
        }
        return data;
    }
    
    /*public static List<String[]> readCSV(String filePath, boolean skipHeader) throws IOException {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            if (skipHeader && (line = reader.readLine()) != null) {
                // Skip the header row
            }
            while ((line = reader.readLine()) != null) {
                data.add(line.split(",", -1));
            }
        } catch (java.io.FileNotFoundException e) {
            System.err.println("File not found: " + filePath);
            throw e;
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
            throw e;
        }
        return data;
    }*/

    public static void writeCSV(String filePath, List<String[]> data) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String[] row : data) {
                writer.write(String.join(",", row) + "\n");
            }
        }
    }
}