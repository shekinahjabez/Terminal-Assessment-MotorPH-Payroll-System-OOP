/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package motorph9_MS2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;
import java.io.*;
import java.util.regex.*;
import javax.swing.JOptionPane;

/**
 *
 * @author admin
 */
public class UpdateEmployeeForm extends javax.swing.JFrame {

    /**
     * Creates new form UpdateEmployee
     */
    
    private static HRDashboard hrDashboard; 
    
    public UpdateEmployeeForm(HRDashboard hrDashboard) {
        this.hrDashboard = hrDashboard; 
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTextField11 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTextField12 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jTextField13 = new javax.swing.JTextField();
        jButtonSave = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTextField1.setEditable(false);
        jTextField1.setText("jTextField1");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Employee ID:");

        jTextField2.setText("jTextField2");
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jLabel2.setText("Last Name:");

        jTextField3.setText("jTextField3");

        jLabel3.setText("First Name:");

        jTextField4.setText("jTextField4");

        jLabel4.setText("Birthday:");

        jTextField5.setText("jTextField5");

        jLabel5.setText("Address:");

        jTextField6.setText("jTextField6");

        jLabel6.setText("Phone Number:");

        jTextField7.setText("jTextField7");

        jLabel7.setText("SSS Number:");

        jLabel8.setText("PhilHealth Number:");

        jTextField8.setText("jTextField8");

        jLabel9.setText("Tin Number:");

        jTextField9.setText("jTextField9");

        jLabel10.setText("PagIbig Number:");

        jTextField10.setText("jTextField10");

        jLabel11.setText("Status:");

        jTextField11.setText("jTextField11");

        jLabel12.setText("Position:");

        jTextField12.setText("jTextField12");

        jLabel13.setText("Supervisor:");

        jTextField13.setText("jTextField13");

        jButtonSave.setText("Save");
        jButtonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveActionPerformed(evt);
            }
        });

        jButtonCancel.setText("Cancel");
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButtonSave)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel1)
                                .addComponent(jLabel2)
                                .addComponent(jLabel4)
                                .addComponent(jLabel3)
                                .addComponent(jLabel5)
                                .addComponent(jLabel6)
                                .addComponent(jLabel7)
                                .addComponent(jLabel8)
                                .addComponent(jLabel9)
                                .addComponent(jLabel10)
                                .addComponent(jLabel11)
                                .addComponent(jLabel12)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(68, 68, 68)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButtonCancel)))))
                .addContainerGap(258, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonSave)
                    .addComponent(jButtonCancel))
                .addContainerGap(41, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jButtonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveActionPerformed
        saveUpdatedEmployee();
    }//GEN-LAST:event_jButtonSaveActionPerformed

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        int confirm = JOptionPane.showConfirmDialog(
            this, "Are you sure you want to cancel?", "Confirm Exit", JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            dispose(); // Close only this frame
        }
    }//GEN-LAST:event_jButtonCancelActionPerformed

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
            java.util.logging.Logger.getLogger(UpdateEmployeeForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UpdateEmployeeForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UpdateEmployeeForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UpdateEmployeeForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UpdateEmployeeForm(hrDashboard).setVisible(true);
            }
        });
        
    }
    
    void populateTextFields(int selectedRow) {
        if (selectedRow == -1) { // No row selected
            JOptionPane.showMessageDialog(this,
            "Please select an employee to modify!",
            "No Employee Selected",
            JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        
        setVisible(true);
        for (int i = 0; i < 13; i++) {
            try {
                // Use reflection to access jTextField1 to jTextField13 dynamically
                javax.swing.JTextField textField = (javax.swing.JTextField) this.getClass()
                        .getDeclaredField("jTextField" + (i + 1)).get(this);

                // Correct the access to the JTable
                Object value = hrDashboard.getjTableEmployeeRecords().getValueAt(selectedRow, i);
                System.out.println("Value at (" + selectedRow + ", " + i + "): " + value);

                textField.setText(value != null ? String.valueOf(value) : "");
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace(); // Handle potential errors
            }
        }
    }

    void saveUpdatedEmployee() {
       File inputFile = new File("src/data9/Employee.csv");
       File tempFile = new File("src/data9/Employee_temp.csv");

       int selectedRow = hrDashboard.getjTableEmployeeRecords().getSelectedRow();
       if (selectedRow == -1) {
           JOptionPane.showMessageDialog(this, "Please select an employee to modify!", 
                                         "No Employee Selected", JOptionPane.WARNING_MESSAGE);
           return;
       }

       String employeeID = hrDashboard.getjTableEmployeeRecords().getValueAt(selectedRow, 0).toString();
       boolean isModified = false;

       // Validation patterns
       Pattern alphabeticPattern = Pattern.compile("^[A-Za-z ]+$");  // First Name & Last Name
       Pattern numericPattern = Pattern.compile("^\\d+$");  // Employee Number, Phone Number
       Pattern datePattern = Pattern.compile("^\\d{1,2}/\\d{1,2}/\\d{4}$");  // Birthday
       Pattern idPattern = Pattern.compile("^\\d{2}-\\d{7}-\\d$");  // SSS Number
       Pattern philhealthPattern = Pattern.compile("^\\d{12}$");  // PhilHealth Number
       Pattern tinPattern = Pattern.compile("^\\d{3}-\\d{3}-\\d{3}-\\d{3}$");  // TIN Number
       Pattern pagibigPattern = Pattern.compile("^\\d{12}$");  // Pag-IBIG Number

       try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

           String line;
           boolean found = false;
           while ((line = reader.readLine()) != null) {
               String[] data = line.split(",");

               if (data[0].equals(employeeID)) { // Match Employee ID
                   found = true;

                   for (int i = 0; i < data.length && i < 13; i++) {
                       javax.swing.JTextField textField = (javax.swing.JTextField) this.getClass()
                               .getDeclaredField("jTextField" + (i + 1)).get(this);
                       String newValue = textField.getText().trim();

                       // Validate First Name & Last Name
                       if (i == 1 || i == 2) { 
                           if (!alphabeticPattern.matcher(newValue).matches()) {
                               JOptionPane.showMessageDialog(this, "Invalid name format: " + newValue + 
                                                             "\nOnly alphabetic characters and spaces are allowed.",
                                                             "Validation Error", JOptionPane.ERROR_MESSAGE);
                               return;
                           }
                       }
                       // Validate Employee Number & Phone Number (numeric)
                       else if (i == 0 || i == 5) { 
                           if (!numericPattern.matcher(newValue).matches()) {
                               JOptionPane.showMessageDialog(this, "Invalid numeric value: " + newValue, 
                                                             "Validation Error", JOptionPane.ERROR_MESSAGE);
                               return;
                           }
                       }
                       // Validate Birthday
                       else if (i == 3) { 
                           if (!datePattern.matcher(newValue).matches()) {
                               JOptionPane.showMessageDialog(this, "Invalid date format (MM/DD/YYYY): " + newValue, 
                                                             "Validation Error", JOptionPane.ERROR_MESSAGE);
                               return;
                           }
                       }
                       // Validate SSS Number
                       else if (i == 6) { 
                           if (!idPattern.matcher(newValue).matches()) {
                               JOptionPane.showMessageDialog(this, "Invalid SSS number format (XX-XXXXXXX-X): " + newValue, 
                                                             "Validation Error", JOptionPane.ERROR_MESSAGE);
                               return;
                           }
                       }
                       // Validate PhilHealth Number
                       else if (i == 7) { 
                           if (!philhealthPattern.matcher(newValue).matches()) {
                               JOptionPane.showMessageDialog(this, "Invalid PhilHealth number (12 digits): " + newValue, 
                                                             "Validation Error", JOptionPane.ERROR_MESSAGE);
                               return;
                           }
                       }
                       // Validate TIN Number
                       else if (i == 8) { 
                           if (!tinPattern.matcher(newValue).matches()) {
                               JOptionPane.showMessageDialog(this, "Invalid TIN number format (XXX-XXX-XXX-XXX): " + newValue, 
                                                             "Validation Error", JOptionPane.ERROR_MESSAGE);
                               return;
                           }
                       }
                       // Validate Pag-IBIG Number
                       else if (i == 9) { 
                           if (!pagibigPattern.matcher(newValue).matches()) {
                               JOptionPane.showMessageDialog(this, "Invalid Pag-IBIG number (12 digits): " + newValue, 
                                                             "Validation Error", JOptionPane.ERROR_MESSAGE);
                               return;
                           }
                       }

                       if (!data[i].equals(newValue)) { // Check if field changed
                           isModified = true;
                           data[i] = newValue; // Update field
                       }
                   }

                   writer.write(String.join(",", data) + "\n");

               } else {
                   writer.write(line + "\n");
               }
           }

           if (!found) {
               JOptionPane.showMessageDialog(this, "Employee not found in CSV!", 
                                             "Error", JOptionPane.ERROR_MESSAGE);
               return;
           }

       } catch (IOException | NoSuchFieldException | IllegalAccessException e) {
           e.printStackTrace();
           JOptionPane.showMessageDialog(this, "Error saving employee data!", 
                                         "Error", JOptionPane.ERROR_MESSAGE);
           return;
       }

       // Replace original file with updated file
       if (inputFile.delete()) {
           if (tempFile.renameTo(inputFile)) {
               if (isModified) {
                   JOptionPane.showMessageDialog(this, "Employee updated successfully!", 
                                                 "Success", JOptionPane.INFORMATION_MESSAGE);

                   // ✅ **Reload the table with updated data**
//for checking, loading issue                   hrDashboard.loadEmployeeTable();  

                   dispose(); // Close window after successful save
               } else {
                   JOptionPane.showMessageDialog(this, "No changes were made!", 
                                                 "Info", JOptionPane.INFORMATION_MESSAGE);
                   dispose();
               }
           } else {
               JOptionPane.showMessageDialog(this, "Error renaming temp file!", 
                                             "Error", JOptionPane.ERROR_MESSAGE);
           }
       } else {
           JOptionPane.showMessageDialog(this, "Error deleting original file!", 
                                         "Error", JOptionPane.ERROR_MESSAGE);
       }
   }


    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonSave;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables
}
