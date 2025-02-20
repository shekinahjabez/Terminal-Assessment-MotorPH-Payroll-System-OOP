/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorph9_oop;

import java.io.IOException;
import java.util.List;

/**
 *
 * @author Shekinah Jabez
 */
public class SalaryDetailsReader {
    private static final String FILE_PATH = "src/data9/Salary.csv";

    public static double getBasicSalary(String employeeId) throws IOException {
        for (String[] data : CSVReader.readCSV(FILE_PATH)) {
            if (data[0].equals(employeeId)) {
                return Double.parseDouble(data[1]);
            }
        }
        return 0.0;
    }

    public static double getHourlyRate(String employeeId) throws IOException {
        for (String[] data : CSVReader.readCSV(FILE_PATH)) {
            if (data[0].equals(employeeId)) {
                return Double.parseDouble(data[2]);
            }
        }
        return 0.0;
    }

    public static void updateSalary(String employeeId, double newSalary) throws IOException {
        List<String[]> salaries = CSVReader.readCSV(FILE_PATH);
        for (String[] data : salaries) {
            if (data[0].equals(employeeId)) {
                data[1] = String.valueOf(newSalary);
                break;
            }
        }
        CSVReader.writeCSV(FILE_PATH, salaries);
    }
}
