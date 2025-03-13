package password_reset9;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Shekinah Jabez
 */
public class PasswordResetService {

    private PasswordDataAccess dataAccess;
    private static final String LOGIN_FILE_PATH = "src/data9/Login.csv";

    public PasswordResetService(PasswordDataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    public boolean resetPassword(String employeeNumber, String adminName, String adminEmpNum) throws IOException, PasswordResetException {
        String specialChars = "!@#$%^&*";
        Random random = new Random();
        int randomIndex = random.nextInt(specialChars.length());
        char randomChar = specialChars.charAt(randomIndex);

        // Ensure two-digit random number (e.g., 07 instead of 7)
        String randomTwoDigit = String.format("%02d", random.nextInt(100));
        
        String newPassword = "Default" + employeeNumber + randomChar + randomTwoDigit;

        if (!updateLoginPassword(employeeNumber, newPassword)) {
            throw new PasswordResetException("Employee not found in login data.");
        }

        if (!updateResetRequestStatus(employeeNumber, adminName, adminEmpNum)) {
            throw new PasswordResetException("Password reset request not found or already approved.");
        }

        return true;
    }
    
    /**
     * Updates the login password while preserving CSV headers
     */
    private boolean updateLoginPasswordWithHeaderPreservation(String employeeNumber, String newPassword) throws IOException {
        List<String> fileContent = new ArrayList<>();
        boolean headerPreserved = false;
        boolean employeeFound = false;
        
        // Read the entire file
        try (BufferedReader reader = new BufferedReader(new FileReader(LOGIN_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!headerPreserved) {
                    // Save the header
                    fileContent.add(line);
                    headerPreserved = true;
                    continue;
                }
                
                String[] data = line.split(",");
                if (data.length > 3 && data[0].equals(employeeNumber)) {
                    // This is the target employee - update password (index 3) and reset flag (index 4)
                    data[3] = newPassword;
                    data[4] = "YES";
                    fileContent.add(String.join(",", data));
                    employeeFound = true;
                } else {
                    // Keep this line unchanged
                    fileContent.add(line);
                }
            }
        }
        
        // If employee was found, write the updated content back
        if (employeeFound) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOGIN_FILE_PATH))) {
                for (String line : fileContent) {
                    writer.write(line);
                    writer.newLine();
                }
            }
            return true;
        }
        
        // Use the original method as fallback if direct file method doesn't find the employee
        if (!employeeFound) {
            return updateLoginPassword(employeeNumber, newPassword);
        }
        
        return false;
    }
    

    private boolean updateLoginPassword(String employeeNumber, String newPassword) throws IOException {
        List<String[]> loginData = dataAccess.readLoginData();
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
            dataAccess.writeLoginData(loginData);
            return true;
        }
        return false;
    }

    private boolean updateResetRequestStatus(String employeeNumber, String adminName, String adminEmpNum) throws IOException, PasswordResetException {
        List<String[]> resetRequests = dataAccess.readResetRequestsData();
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
            dataAccess.writeResetRequestsData(resetRequests);
            return true;
        }
        return false;
    }
}

