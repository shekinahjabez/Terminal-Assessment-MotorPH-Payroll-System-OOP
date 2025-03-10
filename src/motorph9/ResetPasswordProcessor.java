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
import motorph_GUI.ITDashboard;

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
    public boolean resetPassword(String employeeNumber, String adminName, String adminEmpNum, ITDashboard dashboard) throws IOException {
        System.out.println("Reset Password Requested for Employee Number: " + employeeNumber); // Debugging line

        String newPassword = "Default" + employeeNumber;
        if (updateLoginPassword(employeeNumber, newPassword) && updateResetRequestStatus(employeeNumber, adminName, adminEmpNum)) {
            notifyAdmin(newPassword);
            notifyUser(employeeNumber, newPassword);
            dashboard.loadPasswordResetRequests();
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "❌ Employee not found or request already approved.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    //Updates the user's password and change password flag in the Login.csv file.
    private boolean updateLoginPassword(String employeeNumber, String newPassword) throws IOException {
        List<String[]> loginData = CSVReader.readCSV("src/data9/Login.csv");
        boolean updated = false;

        for (String[] row : loginData) {
            if (row[0].equals(employeeNumber)) {
                row[3] = newPassword;
                row[4] = "YES";
                updated = true;
                break;
            }
        }

        if (updated) {
            CSVReader.writeCSV("src/data9/Login.csv", loginData);
            return true;
        } else {
            return false;
        }
    }
    
    // Notifies the IT admin that the password has been reset and provides the temporary password.
    private void notifyAdmin(String newPassword) {
        JOptionPane.showMessageDialog(null,
                "✅ Password has been reset.\n" +
                        "Temporary password: " + newPassword,
                "Password Reset Successful", JOptionPane.INFORMATION_MESSAGE);
    }
    
    // Notifies the user that their password has been reset and provides the temporary password.
    private void notifyUser(String employeeNumber, String newPassword) {
        String message = "Your password has been reset by IT.\n" +
                         "Temporary Password: " + newPassword + "\n" +
                         "Please change your password upon login.";

        JOptionPane.showMessageDialog(null, message, "Password Reset Notification", JOptionPane.INFORMATION_MESSAGE);
    }
    /*private void notifyUser(String employeeNumber, String newPassword) {
        JOptionPane.showMessageDialog(null,
                "🔒 Your password has been reset by IT.\n" +
                        "📌 Default Password: " + newPassword + "\n" +
                        "⚠️ Please change your password upon login.",
                "Password Reset Notification", JOptionPane.INFORMATION_MESSAGE);
    }*/
    
    

    // Updates the password reset request status and adds admin details in the Password_Reset_Requests.csv file.
    private boolean updateResetRequestStatus(String employeeNumber, String adminName, String adminEmpNum) throws IOException {
        List<String[]> resetRequests = CSVReader.readCSV("src/data9/Password_Reset_Requests.csv");
        boolean updated = false;
        String currentDate = new SimpleDateFormat("MM/dd/yyyy - hh:mm:ss a").format(new Date());

        for (int i = 0; i < resetRequests.size(); i++) {
            String[] request = resetRequests.get(i);
            if (request[0].equals(employeeNumber) && request[3].equalsIgnoreCase("Pending")) {
                request[3] = "Approved";
                if (request.length < 7) {
                    String[] extendedRequest = new String[7];
                    System.arraycopy(request, 0, extendedRequest, 0, request.length);
                    for (int j = request.length; j < 7; j++) {
                        extendedRequest[j] = "";
                    }
                    resetRequests.set(i, extendedRequest);
                    request = extendedRequest;
                }
                request[4] = adminName;
                request[5] = adminEmpNum;
                request[6] = currentDate;
                updated = true;
                break;
            }
        }

        if (updated) {
            CSVReader.writeCSV("src/data9/Password_Reset_Requests.csv", resetRequests);
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * ✅ Updates the user's password in Login.csv.
     */
    /*private boolean updateLoginCSV(String employeeNumber) throws IOException {
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
    }*/
    

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
}

