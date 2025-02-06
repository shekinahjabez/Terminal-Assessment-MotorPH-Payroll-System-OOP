
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package motorph9;

import javax.swing.JOptionPane;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import static com.opencsv.ICSVWriter.NO_QUOTE_CHARACTER;
import com.opencsv.exceptions.CsvException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.String;
import javax.swing.*;
import motorph9.EmployeeDashboard;
import motorph9.FinanceDashboard;
import motorph9.HRDashboard;
import motorph9.ITDashboard;
import motorph9.User;

    private static class AuthenticationService {

        public AuthenticationService() {
        }
    }

    private static class Dashboard {

        public Dashboard() {
        }
    }
/**
 *
 * @author Shekinah Jabez
 */
public class Login extends javax.swing.JFrame {

    private static final String CSV_FILE = "src/motorph9/Credentials.csv";
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    
    
    
    /**
     * Creates new form Login
     */
    public Login() {
        initComponents();
        
        setLocationRelativeTo(null);
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
        jLblmotorPay = new javax.swing.JLabel();
        jLblUname = new javax.swing.JLabel();
        jUnameField = new javax.swing.JTextField();
        jPwordField = new javax.swing.JPasswordField();
        jBtnLogin = new javax.swing.JButton();
        jLblPword = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPnlBlack.setBackground(new java.awt.Color(0, 0, 0));

        jLblmotorPay.setBackground(new java.awt.Color(0, 0, 0));
        jLblmotorPay.setFont(new java.awt.Font("Times New Roman", 1, 48)); // NOI18N
        jLblmotorPay.setForeground(new java.awt.Color(204, 204, 255));
        jLblmotorPay.setText("MotorPH");

        jLblUname.setBackground(new java.awt.Color(0, 0, 102));
        jLblUname.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLblUname.setForeground(new java.awt.Color(255, 255, 255));
        jLblUname.setText("Username");

        jUnameField.setBackground(new java.awt.Color(0, 0, 102));
        jUnameField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jUnameField.setForeground(new java.awt.Color(255, 255, 255));
        jUnameField.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 255, 255)));
        jUnameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jUnameFieldActionPerformed(evt);
            }
        });

        jPwordField.setBackground(new java.awt.Color(0, 0, 102));
        jPwordField.setForeground(new java.awt.Color(255, 255, 255));
        jPwordField.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 255, 255)));
        jPwordField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPwordFieldActionPerformed(evt);
            }
        });

        jBtnLogin.setText("LOGIN");
        jBtnLogin.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jBtnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnLoginActionPerformed(evt);
            }
        });

        jLblPword.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLblPword.setForeground(new java.awt.Color(255, 255, 255));
        jLblPword.setText("Password");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logos9/MOTORPH_BG.png"))); // NOI18N

        javax.swing.GroupLayout jPnlBlackLayout = new javax.swing.GroupLayout(jPnlBlack);
        jPnlBlack.setLayout(jPnlBlackLayout);
        jPnlBlackLayout.setHorizontalGroup(
            jPnlBlackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlBlackLayout.createSequentialGroup()
                .addGroup(jPnlBlackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPnlBlackLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jPnlBlackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLblUname)
                            .addComponent(jLblPword, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPnlBlackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPwordField, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                            .addComponent(jUnameField))
                        .addGap(57, 57, 57))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPnlBlackLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPnlBlackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPnlBlackLayout.createSequentialGroup()
                                .addComponent(jBtnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(87, 87, 87))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPnlBlackLayout.createSequentialGroup()
                                .addComponent(jLblmotorPay)
                                .addGap(46, 46, 46)))))
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 590, Short.MAX_VALUE))
        );
        jPnlBlackLayout.setVerticalGroup(
            jPnlBlackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlBlackLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLblmotorPay)
                .addGap(78, 78, 78)
                .addGroup(jPnlBlackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLblUname)
                    .addComponent(jUnameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPnlBlackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPnlBlackLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLblPword, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPnlBlackLayout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jPwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jBtnLogin)
                .addContainerGap(242, Short.MAX_VALUE))
            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        jLabel2.getAccessibleContext().setAccessibleName("bg");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPnlBlack, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPnlBlack, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnLoginActionPerformed

    }//GEN-LAST:event_jBtnLoginActionPerformed

    private class LoginActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            
        User user = AuthenticationService.validateLogin(username, password, CSV_FILE);
        if (user == null) {
        JOptionPane.showMessageDialog(Login.this, "Incorrect Credentials.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
        JOptionPane.showMessageDialog(Login.this, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
        openDashboard(user);
        dispose();
            }
        }
    }

    private void openDashboard(User user) {
        Dashboard dashboard;
        switch (user.getRole().toLowerCase()) {
            case "finance":
                dashboard = new FinanceDashboard(user);
                break;
            case "hr":
                dashboard = new HRDashboard(user);
                break;
            case "it":
                dashboard = new ITDashboard(user);
                break;
            default:
                dashboard = new EmployeeDashboard(user);
                break;
        }
        dashboard.showDashboard();
    }
}
    
    private void jPwordFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPwordFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jPwordFieldActionPerformed

    private void jUnameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jUnameFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jUnameFieldActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnLogin;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLblPword;
    private javax.swing.JLabel jLblUname;
    private javax.swing.JLabel jLblmotorPay;
    private javax.swing.JPanel jPnlBlack;
    private javax.swing.JPasswordField jPwordField;
    private javax.swing.JTextField jUnameField;
    // End of variables declaration//GEN-END:variables
}
