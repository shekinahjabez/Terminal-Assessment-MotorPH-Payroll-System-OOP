/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package motorph_GUI;

import motorph_GUI.Login;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.Timer;
import motorph9.ITUser;

/**
 *
 * @author Four Lugtu
 */
public class ITDashboard extends javax.swing.JFrame {
    private Timer timer;
    private ITUser itUser;

    /**
     * Creates new form ITDashboards
     */
    
    public ITDashboard(ITUser itUser) {  // ✅ Pass ITUser object directly
        this.itUser = itUser; // ✅ Assign it correctly
        initComponents(); // Call initComponents() to initialize UI
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Define close behavior
        startClock();

        // ✅ Debugging: Print ITUser details
        System.out.println("ITUser Data: ");
        System.out.println("Username: " + itUser.getUsername());
        System.out.println("First Name: " + itUser.getFirstName());
        System.out.println("Last Name: " + itUser.getLastName());

        jLabelGreet.setText("Welcome, " + itUser.getFirstName() + "!");
    }

    private ITDashboard() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
    private void startClock() {
        timer = new Timer(1000, e -> updateTimeAndDate());
        timer.start();
    }
    
    private void updateTimeAndDate() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm:ss a");
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM d, yyyy");
        jLabelTime.setText(timeFormat.format(new Date()));
        jLabelDate.setText(dateFormat.format(new Date()));
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelITMain = new javax.swing.JPanel();
        jLabelPH = new javax.swing.JLabel();
        jLabelMotor = new javax.swing.JLabel();
        Logo = new javax.swing.JLabel();
        jLabelAdminInformation = new javax.swing.JLabel();
        jLabelDate = new javax.swing.JLabel();
        jLabelGMT = new javax.swing.JLabel();
        jLabelTime = new javax.swing.JLabel();
        jPanelPasswordReset = new javax.swing.JPanel();
        jScrollPanePasswordResetTickets = new javax.swing.JScrollPane();
        jTablePasswordResetTickets = new javax.swing.JTable();
        jLabelPasswordResetTickets = new javax.swing.JLabel();
        jLabelGreet = new javax.swing.JLabel();
        jButtonDenyPasswordReset = new javax.swing.JButton();
        jButtonApprovePasswordReset = new javax.swing.JButton();
        jPanelAdminInformation = new javax.swing.JPanel();
        jLabelDateAdmin = new javax.swing.JLabel();
        jLabelName = new javax.swing.JLabel();
        jTextFieldName = new javax.swing.JTextField();
        jLabelEmployeeNumber = new javax.swing.JLabel();
        jTextFieldEmployeeNumber = new javax.swing.JTextField();
        jLabelReasonforAction = new javax.swing.JLabel();
        jTextFieldReasonforAction = new javax.swing.JTextField();
        jDateChooser = new com.toedter.calendar.JDateChooser();
        jButtonLogout = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelITMain.setBackground(new java.awt.Color(0, 0, 0));
        jPanelITMain.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelPH.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        jLabelPH.setForeground(new java.awt.Color(204, 0, 51));
        jLabelPH.setText("PH");
        jPanelITMain.add(jLabelPH, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, -20, 70, 120));

        jLabelMotor.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        jLabelMotor.setForeground(new java.awt.Color(255, 255, 255));
        jLabelMotor.setText("MOTOR");
        jPanelITMain.add(jLabelMotor, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, -10, -1, 100));

        Logo.setBackground(new java.awt.Color(0, 0, 102));
        Logo.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        Logo.setForeground(new java.awt.Color(255, 255, 255));
        Logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logos9/LogoMPH_(small1).png"))); // NOI18N
        Logo.setText("Username");
        jPanelITMain.add(Logo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, 100, 110));

        jLabelAdminInformation.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabelAdminInformation.setForeground(new java.awt.Color(255, 255, 255));
        jLabelAdminInformation.setText("Admin Information");
        jPanelITMain.add(jLabelAdminInformation, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 110, -1, -1));

        jLabelDate.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabelDate.setForeground(new java.awt.Color(255, 255, 255));
        jLabelDate.setText("Wednesday, December 25, 2012");
        jLabelDate.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanelITMain.add(jLabelDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 50, -1, -1));

        jLabelGMT.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabelGMT.setForeground(new java.awt.Color(255, 255, 255));
        jLabelGMT.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelGMT.setText("GMT+8 PH Time");
        jPanelITMain.add(jLabelGMT, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 20, 170, -1));

        jLabelTime.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabelTime.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTime.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTime.setText("12:12:12 AM");
        jPanelITMain.add(jLabelTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 40, 170, 32));

        jPanelPasswordReset.setBackground(new java.awt.Color(102, 0, 0));
        jPanelPasswordReset.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTablePasswordResetTickets.setBackground(new java.awt.Color(255, 255, 255));
        jTablePasswordResetTickets.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Employee Name", "Employee Number", "Date of Request", "Admin Name", "Admin Employee Number", "Admin Action", "Admin Reason"
            }
        ));
        jScrollPanePasswordResetTickets.setViewportView(jTablePasswordResetTickets);

        jPanelPasswordReset.add(jScrollPanePasswordResetTickets, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 50, 820, 470));

        jLabelPasswordResetTickets.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabelPasswordResetTickets.setForeground(new java.awt.Color(255, 255, 255));
        jLabelPasswordResetTickets.setText("PASSWORD RESET TICKETS");
        jPanelPasswordReset.add(jLabelPasswordResetTickets, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, -1, -1));

        jPanelITMain.add(jPanelPasswordReset, new org.netbeans.lib.awtextra.AbsoluteConstraints(-60, 120, 900, 560));

        jLabelGreet.setFont(new java.awt.Font("Century Gothic", 1, 20)); // NOI18N
        jLabelGreet.setForeground(new java.awt.Color(255, 255, 255));
        jLabelGreet.setText("Welcome!");
        jPanelITMain.add(jLabelGreet, new org.netbeans.lib.awtextra.AbsoluteConstraints(788, 20, 240, -1));

        jButtonDenyPasswordReset.setBackground(new java.awt.Color(204, 0, 51));
        jButtonDenyPasswordReset.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        jButtonDenyPasswordReset.setForeground(new java.awt.Color(255, 255, 255));
        jButtonDenyPasswordReset.setText("Deny Password Reset");
        jButtonDenyPasswordReset.setMaximumSize(new java.awt.Dimension(132, 27));
        jButtonDenyPasswordReset.setMinimumSize(new java.awt.Dimension(132, 27));
        jButtonDenyPasswordReset.setPreferredSize(new java.awt.Dimension(90, 23));
        jButtonDenyPasswordReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDenyPasswordResetActionPerformed(evt);
            }
        });
        jPanelITMain.add(jButtonDenyPasswordReset, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 490, 200, 40));

        jButtonApprovePasswordReset.setBackground(new java.awt.Color(204, 0, 51));
        jButtonApprovePasswordReset.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        jButtonApprovePasswordReset.setForeground(new java.awt.Color(255, 255, 255));
        jButtonApprovePasswordReset.setText("Approve Password Reset");
        jButtonApprovePasswordReset.setMaximumSize(new java.awt.Dimension(132, 27));
        jButtonApprovePasswordReset.setMinimumSize(new java.awt.Dimension(132, 27));
        jButtonApprovePasswordReset.setPreferredSize(new java.awt.Dimension(90, 23));
        jButtonApprovePasswordReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonApprovePasswordResetActionPerformed(evt);
            }
        });
        jPanelITMain.add(jButtonApprovePasswordReset, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 440, 200, 40));

        jPanelAdminInformation.setBackground(new java.awt.Color(102, 0, 0));
        jPanelAdminInformation.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelDateAdmin.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelDateAdmin.setForeground(new java.awt.Color(255, 255, 255));
        jLabelDateAdmin.setText("Date:");
        jPanelAdminInformation.add(jLabelDateAdmin, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, -1, -1));

        jLabelName.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelName.setForeground(new java.awt.Color(255, 255, 255));
        jLabelName.setText("Name:");
        jPanelAdminInformation.add(jLabelName, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jTextFieldName.setBackground(new java.awt.Color(255, 255, 255));
        jPanelAdminInformation.add(jTextFieldName, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 180, 20));

        jLabelEmployeeNumber.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelEmployeeNumber.setForeground(new java.awt.Color(255, 255, 255));
        jLabelEmployeeNumber.setText("Employee Number:");
        jPanelAdminInformation.add(jLabelEmployeeNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        jTextFieldEmployeeNumber.setBackground(new java.awt.Color(255, 255, 255));
        jPanelAdminInformation.add(jTextFieldEmployeeNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 180, -1));

        jLabelReasonforAction.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelReasonforAction.setForeground(new java.awt.Color(255, 255, 255));
        jLabelReasonforAction.setText("Reason for Action:");
        jPanelAdminInformation.add(jLabelReasonforAction, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 200, -1));

        jTextFieldReasonforAction.setBackground(new java.awt.Color(255, 255, 255));
        jPanelAdminInformation.add(jTextFieldReasonforAction, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 180, 70));

        jDateChooser.setBackground(new java.awt.Color(255, 255, 255));
        jDateChooser.setForeground(new java.awt.Color(0, 0, 0));
        jPanelAdminInformation.add(jDateChooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 180, -1));

        jPanelITMain.add(jPanelAdminInformation, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 140, 200, 280));

        jButtonLogout.setBackground(new java.awt.Color(255, 255, 255));
        jButtonLogout.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        jButtonLogout.setForeground(new java.awt.Color(0, 0, 0));
        jButtonLogout.setText("Logout");
        jButtonLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLogoutActionPerformed(evt);
            }
        });
        jPanelITMain.add(jButtonLogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 580, 200, 30));

        getContentPane().add(jPanelITMain, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 660));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonDenyPasswordResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDenyPasswordResetActionPerformed
        
    }//GEN-LAST:event_jButtonDenyPasswordResetActionPerformed

    private void jButtonApprovePasswordResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonApprovePasswordResetActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonApprovePasswordResetActionPerformed

    private void jButtonLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogoutActionPerformed
        Login newClassInstance = new Login();
                 newClassInstance.setVisible(true); 
                 
                dispose();
    }//GEN-LAST:event_jButtonLogoutActionPerformed

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
            java.util.logging.Logger.getLogger(ITDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ITDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ITDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ITDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ITDashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Logo;
    private javax.swing.JButton jButtonApprovePasswordReset;
    private javax.swing.JButton jButtonDenyPasswordReset;
    private javax.swing.JButton jButtonLogout;
    private com.toedter.calendar.JDateChooser jDateChooser;
    private javax.swing.JLabel jLabelAdminInformation;
    private javax.swing.JLabel jLabelDate;
    private javax.swing.JLabel jLabelDateAdmin;
    private javax.swing.JLabel jLabelEmployeeNumber;
    private javax.swing.JLabel jLabelGMT;
    private javax.swing.JLabel jLabelGreet;
    private javax.swing.JLabel jLabelMotor;
    private javax.swing.JLabel jLabelName;
    private javax.swing.JLabel jLabelPH;
    private javax.swing.JLabel jLabelPasswordResetTickets;
    private javax.swing.JLabel jLabelReasonforAction;
    private javax.swing.JLabel jLabelTime;
    private javax.swing.JPanel jPanelAdminInformation;
    private javax.swing.JPanel jPanelITMain;
    private javax.swing.JPanel jPanelPasswordReset;
    private javax.swing.JScrollPane jScrollPanePasswordResetTickets;
    private javax.swing.JTable jTablePasswordResetTickets;
    private javax.swing.JTextField jTextFieldEmployeeNumber;
    private javax.swing.JTextField jTextFieldName;
    private javax.swing.JTextField jTextFieldReasonforAction;
    // End of variables declaration//GEN-END:variables
}
