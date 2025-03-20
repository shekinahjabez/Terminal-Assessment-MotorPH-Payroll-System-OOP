/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package motorph_GUI;

import data_reader9.EmployeeDetailsReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import motorph9.FinanceUser;
import motorph9.User;
import payroll9.Salary;
import java.io.FileReader;
import java.util.Arrays;
import payroll9.Deductions;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;
import payroll9.PayrollData;

/**
 *
 * @author Four Lugtu
 */
public class FinanceDashboard extends javax.swing.JFrame {
    private static final Logger LOGGER = Logger.getLogger(FinanceDashboard.class.getName());
    private Timer timer;
    private FinanceUser financeUser;
    private EmployeeDetailsReader employeeDetailsReader;
    private Map<String, Salary> salaryMap = new HashMap<>();

    /**
     * Creates new form FinanceDashboards
     */
    /*public FinanceDashboard() {
        initComponents();
    }*/
    
    public FinanceDashboard() {
        initComponents();
        employeeDetailsReader = new EmployeeDetailsReader("src/data9/Employee.csv", "src/data9/Login.csv"); // Initialize with file paths
        loadEmployeeData();
    }
    
    public FinanceDashboard(User user) {
    
    }
    
    public FinanceDashboard(FinanceUser financeUser) {  // ✅ Pass FinanceUser object directly
        this.financeUser = financeUser; // ✅ Assign finance correctly
        initComponents(); // Call initComponents() to initialize UI
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Define close behavior
        startClock();

        LOGGER.info("FinanceUser Data: ");
        LOGGER.info("Username: " + financeUser.getUsername());
        LOGGER.info("First Name: " + financeUser.getFirstName());
        LOGGER.info("Last Name: " + financeUser.getLastName());

        jLabelGreet.setText("Welcome, " + financeUser.getFirstName() + "!");

        employeeDetailsReader = new EmployeeDetailsReader("src/data9/Employee.csv", "src/data9/Login.csv"); // Initialize
        loadSalaryData();
        loadEmployeeData();
        
        
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
    
    private void loadSalaryData() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/data9/Salary.csv"))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                Salary salary = new Salary(Double.parseDouble(data[1]), Double.parseDouble(data[2]), 0.0); // Added 0.0
                salaryMap.put(data[0], salary);
                System.out.println("Salary Map Key: " + data[0]); //Add this line
            }
        } catch (IOException e) {
            LOGGER.severe("Error loading Salary data: " + e.getMessage());
        }
    }
        
    /*private void loadEmployeeData() {
        System.out.println("Salary Map Size: " + salaryMap.size());
        List<String[]> employees = employeeDetailsReader.getAllEmployeesStringArrays();
        System.out.println("Employee Data Size: " + employees.size());
        DefaultTableModel model = (DefaultTableModel) jTableEmployeesList.getModel();
        model.setRowCount(0);

        for (String[] employee : employees) {
            System.out.println("Row Data: " + Arrays.toString(employee));
            try {
                if (employee.length >= 10) { // Adjusted check
                    String employeeNumber = employee[0];
                    String fullName = employee[2] + " " + employee[1];
                    String sssNumber = employee[6];
                    String philhealthNumber = employee[7];
                    String tinNumber = employee[8];
                    String pagibigNumber = employee[9];

                    Salary salary = salaryMap.get(employeeNumber);

                    if (salary != null) {
                        double basicSalary = salary.getBasicSalary();

                        double pagibig = Deductions.calculatePagibigDeduction();
                        double philhealth = Deductions.calculatePhilHealthDeduction(basicSalary);
                        double sss = Deductions.calculateSSSDeduction(basicSalary);

                        double totalDeductions = pagibig + philhealth + sss;
                        double totalAllowances = 0.0;
                        double grossSalary = basicSalary + totalAllowances;
                        double netSalary = grossSalary - totalDeductions;

                        model.addRow(new Object[]{
                                employeeNumber,
                                fullName,
                                sssNumber,
                                philhealthNumber,
                                tinNumber,
                                pagibigNumber,
                                totalAllowances,
                                totalDeductions,
                                grossSalary,
                                netSalary
                        });
                    } else {
                        LOGGER.warning("Salary data not found for employee: " + employeeNumber);
                    }
                }
            } catch (NumberFormatException e) {
                LOGGER.severe("Invalid basic salary for employee: " + employee[0] + ". Error: " + e.getMessage());
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        jTableEmployeesList.repaint();
        validate();
        repaint();
        System.out.println("Table Row Count: " + jTableEmployeesList.getRowCount());
        System.out.println("Table Column Count: " + jTableEmployeesList.getColumnCount());
    }*/
    
    private void loadEmployeeData() {
        System.out.println("Salary Map Size: " + salaryMap.size());
        List<String[]> employees = employeeDetailsReader.getAllEmployeesStringArrays();
        System.out.println("Employee Data Size: " + employees.size());
        DefaultTableModel model = (DefaultTableModel) jTableEmployeesList.getModel();
        model.setRowCount(0);
        
        DecimalFormat decimalFormat = new DecimalFormat("#,###.00"); // Format with commas and two decimal places
        
        for (String[] employee : employees) {
            System.out.println("Row Data: " + Arrays.toString(employee));
            try {
                if (employee.length >= 10) {
                    String employeeNumber = employee[0];
                    String fullName = employee[2] + " " + employee[1];
                    String sssNumber = employee[6];
                    String philhealthNumber = employee[7];
                    String tinNumber = employee[8];
                    String pagibigNumber = employee[9];

                    Salary salary = salaryMap.get(employeeNumber);

                    if (salary != null) {
                        double basicSalary = salary.getBasicSalary();

                        double pagibig = Deductions.calculatePagibigDeduction();
                        double philhealth = Deductions.calculatePhilHealthDeduction(basicSalary);
                        double sss = Deductions.calculateSSSDeduction(basicSalary);

                        // Retrieve allowance and withholding tax
                        double totalAllowances = 0.0;
                        double withholdingTax = 0.0;
                        try {
                            payroll9.SalaryDetails salaryDetails = financeUser.getSalaryDetails(employeeNumber);
                            totalAllowances = salaryDetails.totalAllowances();
                            withholdingTax = salaryDetails.withholdingTax();
                        } catch (IOException e) {
                            LOGGER.severe("Error retrieving salary details: " + e.getMessage());
                        }

                        double totalDeductions = pagibig + philhealth + sss + withholdingTax;
                        double grossSalary = basicSalary + totalAllowances;
                        double netSalary = grossSalary - totalDeductions;
                        
                        String formattedAllowances = decimalFormat.format(totalAllowances);
                        String formattedDeductions = decimalFormat.format(totalDeductions);
                        String formattedGrossSalary = decimalFormat.format(grossSalary);
                        String formattedNetSalary = decimalFormat.format(netSalary);

                        model.addRow(new Object[]{
                                employeeNumber,
                                fullName,
                                sssNumber,
                                philhealthNumber,
                                tinNumber,
                                pagibigNumber,
                                formattedAllowances, 
                                formattedDeductions, 
                                formattedGrossSalary, 
                                formattedNetSalary 
                        });
                    } else {
                        LOGGER.warning("Salary data not found for employee: " + employeeNumber);
                    }
                }
            } catch (NumberFormatException e) {
                LOGGER.severe("Invalid basic salary for employee: " + employee[0] + ". Error: " + e.getMessage());
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        jTableEmployeesList.repaint();
        validate();
        repaint();
        System.out.println("Table Row Count: " + jTableEmployeesList.getRowCount());
        System.out.println("Table Column Count: " + jTableEmployeesList.getColumnCount());
    }




    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelMain = new javax.swing.JPanel();
        jPanelHeader = new javax.swing.JPanel();
        jLabelGreet = new javax.swing.JLabel();
        jLabelDate = new javax.swing.JLabel();
        jLblUname1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanelFinanceGenRep1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableEmployeesList = new javax.swing.JTable();
        jLabelGenRep2 = new javax.swing.JLabel();
        jButtonGenRep = new javax.swing.JButton();
        jButtonLogout = new javax.swing.JButton();
        jLabelTime = new javax.swing.JLabel();
        jLabelGMT = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelMain.setBackground(new java.awt.Color(0, 0, 0));
        jPanelMain.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelHeader.setBackground(new java.awt.Color(0, 0, 0));
        jPanelHeader.setForeground(new java.awt.Color(255, 102, 102));
        jPanelHeader.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelGreet.setFont(new java.awt.Font("Century Gothic", 1, 20)); // NOI18N
        jLabelGreet.setForeground(new java.awt.Color(255, 255, 255));
        jLabelGreet.setText("Welcome!");
        jPanelHeader.add(jLabelGreet, new org.netbeans.lib.awtextra.AbsoluteConstraints(1130, 20, -1, -1));

        jLabelDate.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabelDate.setForeground(new java.awt.Color(255, 255, 255));
        jLabelDate.setText("Wednesday, December 25, 2012");
        jLabelDate.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanelHeader.add(jLabelDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(1130, 50, -1, -1));

        jLblUname1.setBackground(new java.awt.Color(0, 0, 102));
        jLblUname1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLblUname1.setForeground(new java.awt.Color(255, 255, 255));
        jLblUname1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logos9/LogoMPH_(small1).png"))); // NOI18N
        jLblUname1.setText("Username");
        jPanelHeader.add(jLblUname1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, -10, 100, 110));

        jLabel3.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("MOTOR");
        jPanelHeader.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, -10, -1, 100));

        jLabel5.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(204, 0, 51));
        jLabel5.setText("PH");
        jPanelHeader.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, -20, 70, 120));

        jPanelMain.add(jPanelHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1340, 90));

        jPanelFinanceGenRep1.setBackground(new java.awt.Color(102, 0, 0));
        jPanelFinanceGenRep1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTableEmployeesList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Employee Number", "Name", "SSS", "PhilHealth", "TIN", "PAGIBIG", "Total Allowances", "Total Deductions", "Gross Salary", "Net Salary"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTableEmployeesList);

        jPanelFinanceGenRep1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 1060, 520));

        jLabelGenRep2.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabelGenRep2.setForeground(new java.awt.Color(255, 255, 255));
        jLabelGenRep2.setText("EMPLOYEES");
        jPanelFinanceGenRep1.add(jLabelGenRep2, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 0, -1, 40));

        jPanelMain.add(jPanelFinanceGenRep1, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 90, 1080, 640));

        jButtonGenRep.setBackground(new java.awt.Color(251, 0, 54));
        jButtonGenRep.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButtonGenRep.setForeground(new java.awt.Color(255, 255, 255));
        jButtonGenRep.setText("Generate Report");
        jButtonGenRep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGenRepActionPerformed(evt);
            }
        });
        jPanelMain.add(jButtonGenRep, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, 200, 40));

        jButtonLogout.setBackground(new java.awt.Color(255, 255, 255));
        jButtonLogout.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        jButtonLogout.setForeground(new java.awt.Color(0, 0, 0));
        jButtonLogout.setText("Logout");
        jButtonLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLogoutActionPerformed(evt);
            }
        });
        jPanelMain.add(jButtonLogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 490, 200, 30));

        jLabelTime.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabelTime.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTime.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTime.setText("12:12:12 AM");
        jPanelMain.add(jLabelTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 580, 210, 32));

        jLabelGMT.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabelGMT.setForeground(new java.awt.Color(255, 255, 255));
        jLabelGMT.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelGMT.setText("GMT+8 PH Time");
        jPanelMain.add(jLabelGMT, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 560, 210, -1));

        getContentPane().add(jPanelMain, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1340, 670));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogoutActionPerformed
        Login newClassInstance = new Login();
        newClassInstance.setVisible(true);

        dispose();
    }//GEN-LAST:event_jButtonLogoutActionPerformed

    private void jButtonGenRepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGenRepActionPerformed
        int selectedRow = jTableEmployeesList.getSelectedRow();
        if (selectedRow >= 0) {
            String employeeNumber = (String) jTableEmployeesList.getValueAt(selectedRow, 0);
            String fullName = (String) jTableEmployeesList.getValueAt(selectedRow, 1);
            String sssNumber = (String) jTableEmployeesList.getValueAt(selectedRow, 2);
            String philhealthNumber = (String) jTableEmployeesList.getValueAt(selectedRow, 3);
            String tinNumber = (String) jTableEmployeesList.getValueAt(selectedRow, 4);
            String pagibigNumber = (String) jTableEmployeesList.getValueAt(selectedRow, 5);

            try {
                // Remove commas and parse to double
                String totalAllowancesStr = (String) jTableEmployeesList.getValueAt(selectedRow, 6);
                double totalAllowances = Double.parseDouble(totalAllowancesStr.replace(",", ""));

                String totalDeductionsStr = (String) jTableEmployeesList.getValueAt(selectedRow, 7);
                double totalDeductions = Double.parseDouble(totalDeductionsStr.replace(",", ""));

                String grossSalaryStr = (String) jTableEmployeesList.getValueAt(selectedRow, 8);
                double grossSalary = Double.parseDouble(grossSalaryStr.replace(",", ""));

                String netSalaryStr = (String) jTableEmployeesList.getValueAt(selectedRow, 9);
                double netSalary = Double.parseDouble(netSalaryStr.replace(",", ""));

                // Create PayrollData object
                PayrollData payrollData = new PayrollData();
                payrollData.setEmployeeNumber(employeeNumber);
                payrollData.setFullName(fullName);
                payrollData.setSssNumber(sssNumber);
                payrollData.setPhilHealthNumber(philhealthNumber);
                payrollData.setTinNumber(tinNumber);
                payrollData.setPagibigNumber(pagibigNumber);
                payrollData.setTotalAllowances(totalAllowances);
                payrollData.setTotalDeductions(totalDeductions);
                payrollData.setGrossSalary(grossSalary);
                payrollData.setNetSalary(netSalary);

                GenerateReports generateReports = new GenerateReports();
                generateReports.setEmployeeDetails(payrollData); // Correct call
                generateReports.setVisible(true);

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid number format in table data.", "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an employee.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonGenRepActionPerformed

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
            java.util.logging.Logger.getLogger(FinanceDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FinanceDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FinanceDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FinanceDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FinanceDashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonGenRep;
    private javax.swing.JButton jButtonLogout;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabelDate;
    private javax.swing.JLabel jLabelGMT;
    private javax.swing.JLabel jLabelGenRep2;
    private javax.swing.JLabel jLabelGreet;
    private javax.swing.JLabel jLabelTime;
    private javax.swing.JLabel jLblUname1;
    private javax.swing.JPanel jPanelFinanceGenRep1;
    private javax.swing.JPanel jPanelHeader;
    private javax.swing.JPanel jPanelMain;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableEmployeesList;
    // End of variables declaration//GEN-END:variables
}
