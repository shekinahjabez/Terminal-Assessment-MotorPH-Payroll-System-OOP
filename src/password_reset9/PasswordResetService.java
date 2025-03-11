/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package password_reset9;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Shekinah Jabez
 */
public class PasswordResetService {

    private PasswordDataAccess dataAccess;

    public PasswordResetService(PasswordDataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    public boolean resetPassword(String employeeNumber, String adminName, String adminEmpNum) throws IOException, PasswordResetException {
        String newPassword = "Default" + employeeNumber;

        if (!updateLoginPassword(employeeNumber, newPassword)) {
            throw new PasswordResetException("Employee not found in login data.");
        }

        if (!updateResetRequestStatus(employeeNumber, adminName, adminEmpNum)) {
            throw new PasswordResetException("Password reset request not found or already approved.");
        }

        return true;
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

