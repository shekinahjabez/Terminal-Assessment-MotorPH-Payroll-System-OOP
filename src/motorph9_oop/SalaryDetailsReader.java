/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorph9_oop;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Shekinah Jabez
 */
public class SalaryDetailsReader {
    private static final String FILE_PATH = "src/data9/Salary.csv";

    public static double getBasicSalary(String employeeId) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            if (data.length > 1 && data[0].equals(employeeId)) {
                reader.close();
                return Double.parseDouble(data[1]);
            }
        }
        reader.close();
        return 0.0; // Default if not found
    }

    public static double getHourlyRate(String employeeId) throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
    String line;
    while ((line = reader.readLine()) != null) {
        String[] data = line.split(",");
        if (data[0].equals(employeeId)) {
            reader.close();
            return Double.parseDouble(data[2]); // Column 3: Hourly Rate
        }
    }
    reader.close();
    return 0.0; // Default if not found
}
    
    public static void updateSalary(String employeeId, double newSalary) throws IOException {
        List<String[]> salaries = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
        String line;
        boolean updated = false;

        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            if (data[0].equals(employeeId)) {
                data[1] = String.valueOf(newSalary);
                updated = true;
            }
            salaries.add(data);
        }
        reader.close();

        if (updated) {
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH));
            for (String[] data : salaries) {
                writer.write(String.join(",", data) + "\n");
            }
            writer.close();
        }
    }
}
