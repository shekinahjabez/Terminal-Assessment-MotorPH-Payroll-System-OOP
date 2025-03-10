package motorph_GUI;

import motorph_GUI.ForgotPasswordForm;
import java.awt.Color;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.*;
import data_reader9.EmployeeDetailsReader;
import motorph9.EmployeeUser;
import motorph9.FinanceUser;
import motorph9.HRUser;
import motorph9.ITUser;
import data_reader9.LeaveRequestReader;
import motorph9.EmployeeUser;
import motorph9.FinanceUser;
import motorph9.HRUser;
import motorph9.ITUser;
import motorph9.User;
import motorph9.User;

public class Login extends javax.swing.JFrame {

    private static final String CSV_FILE = "src/data9/Login.csv";
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private EmployeeDetailsReader reader;

    /**
     * Creates new form Login
     */
    public Login() {
        initComponents();
        setMinimumSize(new java.awt.Dimension(957, 490)); 
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        reader = new EmployeeDetailsReader();
        jBtnLogin.addActionListener(new LoginActionListener());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPnlBlack = new javax.swing.JPanel();
        jLblForgotPassword = new javax.swing.JLabel();
        jUnameField = new javax.swing.JTextField();
        jPwordField = new javax.swing.JPasswordField();
        jBtnLogin = new javax.swing.JButton();
        jLblPword = new javax.swing.JLabel();
        BG = new javax.swing.JLabel();
        jLabelMotor = new javax.swing.JLabel();
        jLabelPH = new javax.swing.JLabel();
        LOGO = new javax.swing.JLabel();
        jLblUname = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jPnlBlack.setBackground(new java.awt.Color(0, 0, 0));
        jPnlBlack.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLblForgotPassword.setBackground(new java.awt.Color(0, 0, 102));
        jLblForgotPassword.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLblForgotPassword.setForeground(new java.awt.Color(255, 255, 255));
        jLblForgotPassword.setText("<html><u>Forgot Password</u></html>");
        jLblForgotPassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLblForgotPasswordMouseClicked(evt);
            }
        });
        jPnlBlack.add(jLblForgotPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 330, 100, 40));

        jUnameField.setBackground(new java.awt.Color(255, 255, 255));
        jUnameField.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jUnameField.setForeground(new java.awt.Color(0, 0, 0));
        jUnameField.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jUnameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jUnameFieldActionPerformed(evt);
            }
        });
        jPnlBlack.add(jUnameField, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 190, 200, 30));

        jPwordField.setBackground(new java.awt.Color(255, 255, 255));
        jPwordField.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jPwordField.setForeground(new java.awt.Color(0, 0, 0));
        jPwordField.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPwordField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPwordFieldActionPerformed(evt);
            }
        });
        jPnlBlack.add(jPwordField, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 250, 200, 30));

        jBtnLogin.setBackground(new java.awt.Color(204, 0, 51));
        jBtnLogin.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jBtnLogin.setForeground(new java.awt.Color(255, 255, 255));
        jBtnLogin.setText("LOGIN");
        jBtnLogin.setBorder(null);
        jBtnLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jBtnLoginMousePressed(evt);
            }
        });
        jBtnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnLoginActionPerformed(evt);
            }
        });
        jPnlBlack.add(jBtnLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 300, 130, 30));

        jLblPword.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLblPword.setForeground(new java.awt.Color(255, 255, 255));
        jLblPword.setText("Password");
        jPnlBlack.add(jLblPword, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 210, -1, 50));

        BG.setForeground(new java.awt.Color(102, 204, 255));
        BG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logos9/MOTORPH_BG.png"))); // NOI18N
        jPnlBlack.add(BG, new org.netbeans.lib.awtextra.AbsoluteConstraints(317, 0, 640, 490));
        BG.getAccessibleContext().setAccessibleName("bg");

        jLabelMotor.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        jLabelMotor.setForeground(new java.awt.Color(255, 255, 255));
        jLabelMotor.setText("MOTOR");
        jPnlBlack.add(jLabelMotor, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 80, -1, 60));

        jLabelPH.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        jLabelPH.setForeground(new java.awt.Color(204, 0, 51));
        jLabelPH.setText("PH");
        jPnlBlack.add(jLabelPH, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 60, 70, 100));

        LOGO.setBackground(new java.awt.Color(0, 0, 102));
        LOGO.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        LOGO.setForeground(new java.awt.Color(255, 255, 255));
        LOGO.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logos9/LogoMPH_(small1).png"))); // NOI18N
        LOGO.setText("Username");
        jPnlBlack.add(LOGO, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 100, 100));

        jLblUname.setBackground(new java.awt.Color(0, 0, 102));
        jLblUname.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLblUname.setForeground(new java.awt.Color(255, 255, 255));
        jLblUname.setText("Username");
        jPnlBlack.add(jLblUname, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 160, 80, 30));

        getContentPane().add(jPnlBlack);
        jPnlBlack.setBounds(0, 0, 957, 490);

        pack();
    }// </editor-fold>//GEN-END:initComponents

private class LoginActionListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = jUnameField.getText();
                String password = new String(jPwordField.getPassword());

                User user = validateLogin(username, password);
                if (user == null) {
                    JOptionPane.showMessageDialog(Login.this, "Incorrect Credentials.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(Login.this, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    openDashboard(user); // ✅ Calls method to open the dashboard
                    dispose(); // ✅ Closes login window
                }
            }


        /*private User validateLogin(String username, String password) throws IOException {
            User user = reader.getLoginDetails(username); // Get login details from Login.csv

            if (user != null) {
                System.out.println("Loaded user: " + user.getUsername() + ", Password: " + user.getPassword());
            } else {
                System.out.println("User not found: " + username);
            }

            if (user != null 
                && user.getUsername().trim().equals(username.trim()) 
                && user.getPassword().trim().equals(password.trim())) {
                return user;
            }

            System.out.println("Login failed for user: " + username);
            return null;
        }*/
            
        private User validateLogin(String username, String password) {
            EmployeeDetailsReader reader = new EmployeeDetailsReader();
            User user = reader.getLoginDetails(username); // ✅ Fetch user by username

            if (user != null) {
                System.out.println("Loaded user: " + user.getUsername() + ", Password: " + user.getPassword());

                if (user.getPassword().equals(password)) { // ✅ Check if password matches
                    return user;
                }
            }

            System.out.println("❌ Login failed for user: " + username);
            return null;
        }
    

        /*private void openDashboard(User user) {
            if (user instanceof HRUser) {
                HRDashboard hrDashboard = new HRDashboard((HRUser) user, reader, new LeaveRequestReader());
                hrDashboard.setVisible(true);
            } else if (user instanceof ITUser) {
                ITDashboard itDashboard = new ITDashboard((ITUser) user);
                itDashboard.setVisible(true);
            } else if (user instanceof FinanceUser) {
                FinanceDashboard financeDashboard = new FinanceDashboard((FinanceUser) user);
                financeDashboard.setVisible(true);
            } else if (user instanceof EmployeeUser) {
                EmployeeDashboard employeeDashboard = new EmployeeDashboard((EmployeeUser) user);
                employeeDashboard.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(Login.this, "Unknown user type. Cannot open dashboard.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }*/
        
        private void openDashboard(User user) {
            // ✅ Check if the user needs to reset their password
            if (user.getChangePassword().equalsIgnoreCase("YES")) {
                JOptionPane.showMessageDialog(Login.this, 
                    "⚠️ There is an ongoing password change on your account.\n" +
                    "IT has reset your password. Please change it immediately.", 
                    "Password Reset Required", JOptionPane.WARNING_MESSAGE);
            }

            // ✅ Open the correct dashboard based on user type
            if (user instanceof HRUser) {
                HRDashboard hrDashboard = new HRDashboard((HRUser) user, reader, new LeaveRequestReader());
                hrDashboard.setVisible(true);
            } else if (user instanceof ITUser) {
                ITDashboard itDashboard = new ITDashboard((ITUser) user);
                itDashboard.setVisible(true);
            } else if (user instanceof FinanceUser) {
                FinanceDashboard financeDashboard = new FinanceDashboard((FinanceUser) user);
                financeDashboard.setVisible(true);
            } else if (user instanceof EmployeeUser) {
                EmployeeDashboard employeeDashboard = new EmployeeDashboard((EmployeeUser) user);
                employeeDashboard.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(Login.this, "Unknown user type. Cannot open dashboard.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }
    
  
    private void jBtnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnLoginActionPerformed

    }//GEN-LAST:event_jBtnLoginActionPerformed

    private void jPwordFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPwordFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jPwordFieldActionPerformed

    private void jUnameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jUnameFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jUnameFieldActionPerformed

    private void jBtnLoginMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBtnLoginMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jBtnLoginMousePressed

    private void jLblForgotPasswordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLblForgotPasswordMouseClicked
// Open the Forgot Password Form when clicked
        ForgotPasswordForm form = new ForgotPasswordForm();
        form.setVisible(true);
    }//GEN-LAST:event_jLblForgotPasswordMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        SwingUtilities.invokeLater(() -> {
            Login login = new Login();
            login.setVisible(true);
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BG;
    private javax.swing.JLabel LOGO;
    private javax.swing.JButton jBtnLogin;
    private javax.swing.JLabel jLabelMotor;
    private javax.swing.JLabel jLabelPH;
    private javax.swing.JLabel jLblForgotPassword;
    private javax.swing.JLabel jLblPword;
    private javax.swing.JLabel jLblUname;
    private javax.swing.JPanel jPnlBlack;
    private javax.swing.JPasswordField jPwordField;
    private javax.swing.JTextField jUnameField;
    // End of variables declaration//GEN-END:variables
}
