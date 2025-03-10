package data_reader9;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import motorph9.PasswordResetRequest;

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
    public static void saveRequest(PasswordResetRequest request) {
        File file = new File(FILE_NAME);
        boolean fileExists = file.exists(); 

        try (FileWriter writer = new FileWriter(FILE_NAME, true);
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
            
            if (!fileExists) {
                bufferedWriter.write("Employee Number,Employee Name,Date of Request,Status\n");
            }

            bufferedWriter.write(request.toCSV() + "\n");
            bufferedWriter.flush();
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

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                if (firstLine) { 
                    firstLine = false; 
                    continue; // ✅ Skip header row
                }

                String[] data = line.split(",", -1);
                if (data.length >= 7) { // ✅ Ensure all 7 fields exist
                    PasswordResetRequest request = new PasswordResetRequest(
                        data[0], // Employee Number
                        data[1], // Employee Name
                        data[2], // Date of Request
                        data[3], // Status
                        data[4], // Admin Name
                        data[5], // Admin Employee No.
                        data[6]  // Date of Reset
                    );
                    requests.add(request);
                }
            }
        } catch (IOException e) {
            System.err.println("❌ Error reading password reset requests: " + e.getMessage());
        }

        return requests;
    }
    /*public List<PasswordResetRequest> getAllRequests() {
        List<PasswordResetRequest> requests = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                if (firstLine) { 
                    firstLine = false; 
                    continue; // ✅ Skip header row
                }

                String[] data = line.split(",", -1);
                if (data.length >= 7) { // ✅ Ensure all fields exist
                    PasswordResetRequest request = new PasswordResetRequest(
                        data[0], // Employee Number
                        data[1], // Employee Name
                        data[2], // Date of Request
                        data[3], // Status
                        data[4], // Admin Name
                        data[5], // Admin Employee No.
                        data[6]  // Date of Reset
                    );
                    requests.add(request);
                }
            }
        } catch (IOException e) {
            System.err.println("❌ Error reading password reset requests: " + e.getMessage());
        }

        return requests;
    }*/

    /**
     * ✅ Updates the status of a password reset request in the CSV file.
     * @param empNum The employee number whose request is being updated.
     * @param newStatus The new status (e.g., "Approved", "Denied").
     * @param adminName The IT admin handling the request.
     * @param adminEmpNum The admin's employee number.
     * @param dateOfReset The date the request was processed.
     * @return true if the update was successful, false otherwise.
     */
    public boolean updateRequestStatus(String empNum, String newStatus, String adminName, String adminEmpNum, String dateOfReset) {
        List<PasswordResetRequest> requests = getAllRequests();
        boolean updated = false;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            bw.write("Employee Number,Employee Name,Date of Request,Status,Admin Name,Admin Employee No.,Date of Reset\n"); // ✅ Rewrite header

            for (PasswordResetRequest request : requests) {
                if (request.getEmployeeNumber().equals(empNum)) {
                    request.setStatus(newStatus);
                    request.setAdminName(adminName);
                    request.setAdminEmployeeNumber(adminEmpNum);
                    request.setDateOfReset(dateOfReset);
                    updated = true;
                }
                bw.write(request.toCSV() + "\n");
            }
        } catch (IOException e) {
            System.err.println("❌ Error updating request: " + e.getMessage());
        }

        return updated;
    }

    /*public boolean updateRequestStatus(String empNum, String newStatus, String adminName, String adminEmpNum, String dateOfReset) {
        List<PasswordResetRequest> requests = getAllRequests();
        boolean updated = false;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            bw.write("Employee Number,Employee Name,Date of Request,Status,Admin Name,Admin Employee No.,Date of Reset\n"); // ✅ Rewrite header

            for (PasswordResetRequest request : requests) {
                if (request.getEmployeeNumber().equals(empNum)) {
                    request.setStatus(newStatus);
                    request.setAdminName(adminName);
                    request.setAdminEmployeeNumber(adminEmpNum);
                    request.setDateOfReset(dateOfReset);
                    updated = true;
                }
                bw.write(request.toCSV() + "\n");
            }
        } catch (IOException e) {
            System.err.println("❌ Error updating request: " + e.getMessage());
        }

        return updated;
    }*/
}
