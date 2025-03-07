/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data_reader9;

/**
 *
 * @author Shekinah Jabez
 */
import data_reader9.CSVReader;
import java.io.*;

public class AllowanceDetailsReader {
    private static final String FILE_PATH = "src/data9/Allowance.csv";

    public static double getRiceSubsidyAllowance(String employeeId) throws IOException {
        return getAllowance(employeeId, 1); // Column index for Rice Subsidy
    }

    public static double getPhoneAllowance(String employeeId) throws IOException {
        return getAllowance(employeeId, 2); // Column index for Phone Allowance
    }

    public static double getClothingAllowance(String employeeId) throws IOException {
        return getAllowance(employeeId, 3); // Column index for Clothing Allowance
    }

    private static double getAllowance(String employeeId, int columnIndex) throws IOException {
        for (String[] data : CSVReader.readCSV(FILE_PATH)) {
            if (data[0].equals(employeeId) && data.length > columnIndex) {
                return Double.parseDouble(data[columnIndex]);
            }
        }
        return 0.0;
    }
}
