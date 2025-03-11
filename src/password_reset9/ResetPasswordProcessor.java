package password_reset9;
import java.io.*;
import javax.swing.JOptionPane;
import motorph_GUI.ITDashboard;

/**
 *
 * @author Shekinah Jabez
 */

public class ResetPasswordProcessor {

    private PasswordResetService passwordResetService;

    public ResetPasswordProcessor(PasswordResetService passwordResetService) {
        this.passwordResetService = passwordResetService;
    }

    public boolean resetPassword(String employeeNumber, String adminName, String adminEmpNum, ITDashboard dashboard) {
        try {
            passwordResetService.resetPassword(employeeNumber, adminName, adminEmpNum);
            JOptionPane.showMessageDialog(null, "✅ Password reset successful.", "Success", JOptionPane.INFORMATION_MESSAGE);
            dashboard.loadPasswordResetRequests();
            return true;
        } catch (PasswordResetException e) {
            JOptionPane.showMessageDialog(null, "❌ " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "❌ An I/O error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    /*private void notifyAdmin(String newPassword) {
        JOptionPane.showMessageDialog(null, "✅ Password has been reset.\nTemporary password: " + newPassword, "Password Reset Successful", JOptionPane.INFORMATION_MESSAGE);
    }

    private void notifyUser(String employeeNumber, String newPassword) {
        String message = "Your password has been reset by IT.\nTemporary Password: " + newPassword + "\nPlease change your password upon login.";
        JOptionPane.showMessageDialog(null, message, "Password Reset Notification", JOptionPane.INFORMATION_MESSAGE);
    }*/
}
     