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
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                data.add(values);
            }
        }
        return data;
    }
    
    public static void writeCSV(String filePath, List<String[]> data) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String[] row : data) {
                writer.write(String.join(",", row) + "\n");
            }
        }
    }

    public static void writeCSVWithHeader(String filePath, List<String[]> data) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("employeeNum,username,roleName,password,changePassword\n");
            for (int i = 0; i < data.size(); i++) {
                writer.write(String.join(",", data.get(i)) + "\n");
            }
        }
    }
}