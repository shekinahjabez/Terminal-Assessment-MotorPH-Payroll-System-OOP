/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorph9;
import data_reader9.CSVReader;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Shekinah Jabez
 */

public class ResetPasswordProcessor {
    private static final String LOGIN_FILE = "src/data9/Login.csv";
    private static final String REQUESTS_FILE = "src/data9/Password_Reset_Requests.csv";

    /**
     * ✅ Resets the password for a user and updates CSV files.
     * @param employeeNumber The employee number whose password is being reset.
     * @param adminName The IT admin handling the reset.
     * @param adminEmployeeNo The IT admin’s employee number.
     * @return true if the password reset is successful.
     */
    /*public boolean resetPassword(String employeeNumber, String adminName, String adminEmployeeNo) {
        try {
            if (!updateLoginCSV(employeeNumber)) {
                return false;
            }

            if (!updateRequestStatus(employeeNumber, adminName, adminEmployeeNo)) {
                return false;
            }

            return true;
        } catch (IOException e) {
            System.err.println("❌ Error resetting password: " + e.getMessage());
            return false;
        }
    }*/
    
    public boolean resetPassword(String employeeNumber, String adminName, String adminEmpNum) throws IOException {
        List<String[]> loginData = CSVReader.readCSV("src/data9/Login.csv");
        boolean updated = false;
        String newPassword = "Default" + employeeNumber; // ✅ Generate default password

        for (String[] row : loginData) {
            if (row[0].equals(employeeNumber)) { // ✅ Match Employee Number
                row[3] = newPassword; // ✅ Update password
                row[4] = "YES"; // ✅ Set Change Password to YES
                updated = true;
                break;
            }
        }

        if (updated) {
            CSVReader.writeCSV("src/data9/Login.csv", loginData);
            JOptionPane.showMessageDialog(null, 
                "✅ Password has been reset.\n" + 
                "Temporary password: " + newPassword, 
                "Password Reset Successful", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            JOptionPane.showMessageDialog(null, 
                "❌ Employee not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }



    /**
     * ✅ Updates the user's password in Login.csv.
     */
    private boolean updateLoginCSV(String employeeNumber) throws IOException {
        List<String[]> users = CSVReader.readCSV(LOGIN_FILE);
        boolean updated = false;

        for (String[] user : users) {
            if (user.length >= 4 && user[0].equals(employeeNumber)) { 
                user[3] = "123456"; // ✅ Set new default password
                user[4] = "YES"; // ✅ Mark that a password reset was done
                updated = true;
            }
        }

        if (updated) {
            try {
                CSVReader.writeCSV(LOGIN_FILE, users);
                return true;
            } catch (IOException e) {
                System.err.println("❌ Error updating Login.csv: " + e.getMessage());
            }
        }

        return false;
    }

    /**
     * ✅ Updates Password_Reset_Requests.csv to reflect the password reset.
     */
    private boolean updateRequestStatus(String employeeNumber, String adminName, String adminEmployeeNo) throws IOException {
        List<String[]> requests = CSVReader.readCSV(REQUESTS_FILE);
        boolean updated = false;

        String dateOfReset = new SimpleDateFormat("MM/dd/yyyy - hh:mm:ss a").format(new Date());

        for (int i = 0; i < requests.size(); i++) {
            String[] request = requests.get(i);

            if (request.length >= 4 && request[0].equals(employeeNumber)) { 
                if ("Approved".equalsIgnoreCase(request[3])) {
                    System.out.println("❌ Request for " + employeeNumber + " is already approved. Cannot reset again.");
                    return false; // ❌ Prevent re-editing an approved request
                }

                request[3] = "Approved"; // ✅ Change status to Approved

                // ✅ Ensure at least 7 columns exist
                if (request.length < 7) {
                    String[] extendedRequest = new String[7];
                    System.arraycopy(request, 0, extendedRequest, 0, request.length);
                    for (int j = request.length; j < 7; j++) {
                        extendedRequest[j] = ""; // ✅ Fill missing columns with empty values
                    }
                    requests.set(i, extendedRequest); // ✅ Update list with extended request
                    request = extendedRequest;
                }

                request[4] = adminName; // ✅ Set IT Admin Name
                request[5] = adminEmployeeNo; // ✅ Set IT Admin Employee Number
                request[6] = dateOfReset; // ✅ Set Date of Reset
                updated = true;
            }
        }

        if (updated) {
            try {
                CSVReader.writeCSV(REQUESTS_FILE, requests);
                System.out.println("✅ Password reset recorded in CSV.");
                return true;
            } catch (IOException e) {
                System.err.println("❌ Error updating Password_Reset_Requests.csv: " + e.getMessage());
            }
        }

        return false;
    }

    
    /*private boolean updateRequestStatus(String employeeNumber, String adminName, String adminEmployeeNo) throws IOException {
        List<String[]> requests = CSVReader.readCSV(REQUESTS_FILE);
        boolean updated = false;

        String dateOfReset = new SimpleDateFormat("MM/dd/yyyy - hh:mm:ss a").format(new Date());

        for (int i = 0; i < requests.size(); i++) {
            String[] request = requests.get(i);

            if (request.length >= 4 && request[0].equals(employeeNumber)) { 
                request[3] = "Approved"; // ✅ Change status to Approved

                // ✅ Ensure at least 7 columns exist
                if (request.length < 7) {
                    String[] extendedRequest = new String[7];
                    System.arraycopy(request, 0, extendedRequest, 0, request.length);
                    for (int j = request.length; j < 7; j++) {
                        extendedRequest[j] = ""; // ✅ Fill missing columns with empty values
                    }
                    requests.set(i, extendedRequest); // ✅ Update list with extended request
                    request = extendedRequest;
                }

                request[4] = adminName; // ✅ Set IT Admin Name
                request[5] = adminEmployeeNo; // ✅ Set IT Admin Employee Number
                request[6] = dateOfReset; // ✅ Set Date of Reset
                updated = true;
            }
        }

        if (updated) {
            try {
                CSVReader.writeCSV(REQUESTS_FILE, requests);
                System.out.println("✅ Password reset recorded in CSV.");
                return true;
            } catch (IOException e) {
                System.err.println("❌ Error updating Password_Reset_Requests.csv: " + e.getMessage());
            }
        }

        return false;
    }*/

    
    
    
}

