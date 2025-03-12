package data_reader9;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.util.ArrayList;
import java.util.List;
import password_reset9.PasswordResetRequest;
import java.io.IOException;

/**
 *
 * @author Shekinah Jabez
 */
public class PasswordResetReader { 
 
    private static final String FILE_NAME = "src/data9/Password_Reset_Requests.csv";
    
    /**
     * Saves a new password reset request to the CSV file.
     * @param request The password reset request to be saved.
     */
    
    public void saveRequest(PasswordResetRequest request) throws IOException {
        try {
            List<String[]> existingData = CSVReader.readCSV(FILE_NAME); // Use CSVReader.readCSV
            if (existingData.isEmpty()) {
                existingData.add(new String[]{"Employee Number", "Employee Name", "Date of Request", "Status", "Admin Name", "Admin Employee No.", "Date of Reset"});
            }
            existingData.add(request.toArray());
            CSVReader.writeCSV(FILE_NAME, existingData); // Use CSVReader.writeCSV
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * ✅ Reads all password reset requests from the CSV file.
     * @return A list of password reset requests.
     */
    private List<PasswordResetRequest> getAllRequests() {
        List<PasswordResetRequest> requests = new ArrayList<>();
        try {
            List<String[]> data = CSVReader.readCSV(FILE_NAME);
            data.remove(0);
            for (String[] row : data) {
                if (row.length >= 8) {
                    PasswordResetRequest request = new PasswordResetRequest(
                            row[0], row[1], row[2], row[3], row[4], row[5], row[6], row[7]
                    );
                    requests.add(request);
                }
            }
        } catch (IOException e) {
            System.err.println("❌ Error reading password reset requests: " + e.getMessage());
        }
        return requests;
    }
    
   
    /**
     * ✅ Updates the status of a password reset request in the CSV file.
     * @param empNum The employee number whose request is being updated.
     * @param newStatus The new status ("Approved").
     * @param adminName The IT admin handling the request.
     * @param adminEmpNum The admin's employee number.
     * @param dateOfReset The date the request was processed.
     * @return true if the update was successful, false otherwise.
     */
    public boolean updateRequestStatus(String empNum, String newStatus, String adminName, String adminEmpNum, String dateOfReset) {
        List<PasswordResetRequest> requests = getAllRequests();
        boolean updated = false;
        try {
            List<String[]> data = new ArrayList<>();
            data.add(new String[]{"Employee Number", "Employee Name", "Date of Request", "Status", "Admin Name", "Admin Employee No.", "Date of Reset"});
            for (PasswordResetRequest request : requests) {
                if (request.getEmployeeNumber().equals(empNum)) {
                    request.setStatus(newStatus);
                    request.setAdminName(adminName);
                    request.setAdminEmployeeNumber(adminEmpNum);
                    request.setDateOfReset(dateOfReset);
                    updated = true;
                }
                data.add(request.toArray());
            }
            CSVReader.writeCSV(FILE_NAME, data);
        } catch (IOException e) {
            System.err.println("❌ Error updating request: " + e.getMessage());
        }
        return updated;
    }
}
