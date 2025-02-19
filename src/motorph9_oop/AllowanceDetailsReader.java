/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorph9_oop;

/**
 *
 * @author Shekinah Jabez
 */
import java.io.*;

public class AllowanceDetailsReader {
    private static final String FILE_PATH = "src/data9/Allowance.csv";

    public static double getRiceSubsidyAllowance(String employeeId) throws IOException {
        return getAllowance(employeeId, 2); // Assuming rice subsidy is in column 3
    }

    public static double getPhoneAllowance(String employeeId) throws IOException {
        return getAllowance(employeeId, 3); // Assuming phone allowance is in column 4
    }
    
    public static double getClothingAllowance(String employeeId) throws IOException {
        return getAllowance(employeeId, 1); // Assuming clothing allowance is in column 2
    }

    private static double getTotalAllowance(String employeeId, int columnIndex) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            if (data[0].equals(employeeId) && data.length > columnIndex) {
                reader.close();
                return Double.parseDouble(data[columnIndex]);
            }
        }
        reader.close();
        return 0.0;
    }

    private static double getAllowance(String employeeId, int i) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

