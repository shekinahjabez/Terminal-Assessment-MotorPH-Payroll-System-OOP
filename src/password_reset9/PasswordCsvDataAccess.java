package password_reset9;

import data_reader9.CSVReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Shekinah Jabez
 */
public class PasswordCsvDataAccess implements PasswordDataAccess {
    private static final String LOGIN_FILE_PATH = "src/data9/Login.csv";
    private static final String RESET_REQUESTS_FILE_PATH = "src/data9/Password_Reset_Requests.csv";

    @Override
    public List<String[]> readLoginData() throws IOException {
        return CSVReader.readCSV(LOGIN_FILE_PATH);
    }

    /*@Override
    public void writeLoginData(List<String[]> loginData) throws IOException {
        CSVReader.writeCSV(LOGIN_FILE, loginData);
    }*/
    
    @Override
    public void writeLoginData(List<String[]> loginData) throws IOException {
        // Preserve the headers by reading them first
        String headers = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(LOGIN_FILE_PATH))) {
            headers = reader.readLine(); // Read the first line (headers)
        } catch (IOException e) {
            System.err.println("Error reading headers: " + e.getMessage());
            throw e;
        }
        
        // Now write the data with preserved headers
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOGIN_FILE_PATH))) {
            // Write the headers first
            if (headers != null && !headers.isEmpty()) {
                writer.write(headers);
                writer.newLine();
            }
            
            // Then write the data
            for (String[] row : loginData) {
                // Skip the first row if it contains headers (to avoid duplicate headers)
                if (row[0].equalsIgnoreCase("employeeNumber") || 
                    row[0].equalsIgnoreCase("employee_number") ||
                    row[0].contains("Employee")) {
                    continue;
                }
                
                writer.write(String.join(",", row));
                writer.newLine();
            }
        }
    }

    @Override
    public List<String[]> readResetRequestsData() throws IOException {
        return CSVReader.readCSV(RESET_REQUESTS_FILE_PATH);
    }

    /*@Override
    public void writeResetRequestsData(List<String[]> resetRequestsData) throws IOException {
        CSVReader.writeCSV(REQUESTS_FILE, resetRequestsData);
    }*/
    
    @Override
    public void writeResetRequestsData(List<String[]> resetRequestsData) throws IOException {
        // Similar approach for reset requests file
        String headers = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(RESET_REQUESTS_FILE_PATH))) {
            headers = reader.readLine(); // Read the first line (headers)
        } catch (IOException e) {
            System.err.println("Error reading headers: " + e.getMessage());
            throw e;
        }
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RESET_REQUESTS_FILE_PATH))) {
            // Write the headers first
            if (headers != null && !headers.isEmpty()) {
                writer.write(headers);
                writer.newLine();
            }
            
            // Then write the data
            for (String[] row : resetRequestsData) {
                // Skip the header row if it exists in the data
                if (row[0].equalsIgnoreCase("employeeNumber") || 
                    row[0].equalsIgnoreCase("employee_number") ||
                    row[0].contains("Employee")) {
                    continue;
                }
                
                writer.write(String.join(",", row));
                writer.newLine();
            }
        }
    }
}
